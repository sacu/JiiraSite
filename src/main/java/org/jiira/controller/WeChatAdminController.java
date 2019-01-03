package org.jiira.controller;

import java.util.ArrayList;
import java.util.List;

import org.jiira.pojo.ad.AdIV;
import org.jiira.pojo.ad.AdNewsImage;
import org.jiira.pojo.ad.AdVideo;
import org.jiira.pojo.ad.AdVoice;
import org.jiira.pojo.ad.ResultIVV;
import org.jiira.service.AdIVService;
import org.jiira.service.AdNewsImageService;
import org.jiira.service.AdVideoService;
import org.jiira.service.AdVoiceService;
import org.jiira.utils.CommandCollection;
import org.jiira.we.SAHTML;
import org.jiira.we.WeGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/ad")
public class WeChatAdminController {

	@Autowired
	private AdNewsImageService adNewsImageService = null;
	@Autowired
	private AdIVService adIVService = null;
	@Autowired
	private AdVoiceService adVoiceService = null;
	@Autowired
	private AdVideoService adVideoService = null;

	/**
	 * 创建菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createMenu")
	public ModelAndView createMenu() {
		ModelAndView mv = new ModelAndView();
		SAHTML html = WeGlobal.getInstance().createMenu();
		mv.addObject(html.getBody());
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 获取Access Token
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createAccessToken")
	public ModelAndView createAccessToken() {
		ModelAndView mv = new ModelAndView();
		SAHTML html = WeGlobal.getInstance().createAccessToken();
		mv.addObject(html.getBody());
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/clearQuota")
	public ModelAndView clearQuota() {
		ModelAndView mv = new ModelAndView();
		JSONObject json = WeGlobal.getInstance().clearQuota();
		mv.addObject(json);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	/**
	 * 上传图文内图片到服务器
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/addNewsImage")
	public ModelAndView addNewsImage(MultipartFile[] files) {
		ModelAndView mv = new ModelAndView();
		// 获取所有图片名称+后缀作为newsimage(数据库字段)
		List<String> newsImages = new ArrayList<String>();
		List<MultipartFile> newsImagesFiles = new ArrayList<>();
		MultipartFile file;
		for (int i = 0; i < files.length; ++i) {
			file = files[i];
			if (file.getSize() > 0) {
				newsImages.add(file.getOriginalFilename());
				newsImagesFiles.add(file);
			}
		}
		if (newsImages.size() > 0) {
			// 检测数据库
			List<AdNewsImage> adNewsImages = adNewsImageService.checkNewsImage(newsImages);
			if (adNewsImages.size() == 0) {// 可以上传
				// 保存图片
				if (WeGlobal.getInstance().upload(CommandCollection.NEWS_IMAGE_PATH, newsImagesFiles)) {
					// 写入数据库
					int rows = adNewsImageService.ignoreNewsImage(newsImages);
					mv.addObject("上传成功 : " + rows + "条数据");
				} else {
					mv.addObject("上传失败");
				}
			} else {
				mv.addObject(adNewsImages);
			}
		} else {
			mv.addObject("没有检测到可上传的图片");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 获取图文内图片列表
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/getNewsImageList")
	public ModelAndView getNewsImageList() {
		List<AdNewsImage> adNewsImages = adNewsImageService.selectNewsImages();
		ModelAndView mv = new ModelAndView();
		mv.addObject(adNewsImages);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 上传图文内图片到服务器
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/getNewsImageToWe")
	public ModelAndView getNewsImageToWe(@RequestParam(name = "newsImages[]") String[] newsImages) {
		// 提交图片到公众号
		JSONObject json;
		String newsImage;
		String url;
		int row;
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		List<String> weFault = new ArrayList<String>();// 微信提交失败列表
		ModelAndView mv = new ModelAndView();
		for (int i = 0; i < newsImages.length; ++i) {
			newsImage = newsImages[i];
			json = WeGlobal.getInstance().addNewsImage(CommandCollection.NEWS_IMAGE_PATH + newsImage);
			if (null != json) {
				url = json.getString("url");
				if (null != url) {// 上传成功
					url = url.replaceAll("\\\\", "");
					row = adNewsImageService.updateNewsImage(newsImage, url);
					if (row > 0) {// 更新成功
						success.add(newsImage);
					} else {
						dataFault.add(newsImage);
					}
				} else {// 失败
					weFault.add(newsImage);
				}
			} else {
				mv.addObject("msg", "上传到公众号失败，可能是图片不存在");
			}
		}
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.addObject("weFault", weFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 清除图文内图片到服务器
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/clearNewsImageToWe")
	public ModelAndView clearNewsImageToWe(@RequestParam(name = "newsImages[]") String[] newsImages) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		String newsImage;
		int row;
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		for (int i = 0; i < newsImages.length; ++i) {
			newsImage = newsImages[i];
			row = adNewsImageService.updateNewsImage(newsImage, "");
			if (row > 0) {// 更新成功
				success.add(newsImage);
			} else {
				dataFault.add(newsImage);
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 删除服务器上的图文图片
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/deleteNewsImageToWe")
	public ModelAndView deleteNewsImageToWe(@RequestParam(name = "newsImages[]") String[] newsImages) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		String newsImage;
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		for (int i = 0; i < newsImages.length; ++i) {
			newsImage = newsImages[i];
			if (WeGlobal.getInstance().unload(CommandCollection.NEWS_IMAGE_PATH, newsImage)
					&& adNewsImageService.deleteNewsImage(newsImage) > 0) {
				success.add(newsImage);// 更新成功
			} else {
				dataFault.add(newsImage);
			}
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 上传资源到服务器
	 */
	@RequestMapping(value = "/addIVV")
	public ModelAndView addIVV(MultipartFile[] files, @RequestParam(name = "type") String type,
			@RequestParam(required = false) String title, @RequestParam(required = false) String introduction) {
		ModelAndView mv = new ModelAndView();
		// 获取所有图片名称+后缀作为iv(数据库字段)
		List<String> ivvs = new ArrayList<String>();
		List<MultipartFile> ivsFiles = new ArrayList<>();
		MultipartFile file;
		for (int i = 0; i < files.length; ++i) {
			file = files[i];
			if (file.getSize() > 0) {
				ivvs.add(file.getOriginalFilename());
				ivsFiles.add(file);
			}
		}
		String path = CommandCollection.GetLocalPath(type);
		boolean isVideo = type.equals(CommandCollection.MESSAGE_VIDEO);
		boolean isVoice = type.equals(CommandCollection.MESSAGE_VOICE);
		if (ivvs.size() > 0) {
			// 检测数据库
			boolean cu = false;
			if (isVideo) {
				cu = adVideoService.checkVideo(ivvs.get(0)).size() == 0;
			} else if(isVoice) {
				cu = adVoiceService.checkVoice(ivvs).size() == 0;
			} else {
				cu = adIVService.checkIV(ivvs).size() == 0;
			}
			if (cu) {// 可以上传
				// 保存
				int rows = 0;
				if (isVideo) {
					if (WeGlobal.getInstance().upload(path, ivsFiles.get(0))) {
						rows = adVideoService.ignoreVideo(ivvs.get(0), title, introduction);
					}
				} else {
					if (WeGlobal.getInstance().upload(path, ivsFiles)) {
						if(isVoice) {
							rows = adVoiceService.ignoreVoice(ivvs);
						} else {
							rows = adIVService.ignoreIV(ivvs, type);
						}
					}
				}
				if (rows > 0) {
					mv.addObject("上传成功 : " + rows + "条数据");
				} else {
					mv.addObject("上传失败");
				}
			} else {
				mv.addObject("有重复");
			}
		} else {
			mv.addObject("没有检测到可上传的图片");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 获取IVV列表
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/getIVVList")
	public ModelAndView getIVVList(@RequestParam(name = "type") String type) {

		ModelAndView mv = new ModelAndView();
		if (type.equals(CommandCollection.MESSAGE_VIDEO)) {
			List<AdVideo> adVideos = adVideoService.selectVideos();
			mv.addObject(adVideos);
		} else if(type.equals(CommandCollection.MESSAGE_VOICE)){
			List<AdVoice> adVoices = adVoiceService.selectVoices();
			mv.addObject(adVoices);
		} else {
			List<AdIV> adIVs = adIVService.selectIVs(type);
			mv.addObject(adIVs);
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 上传到微信服务器
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/getIVVToWe")
	public ModelAndView getIVVToWe(@RequestParam(name = "ivvs[]") String[] ivvs,
			@RequestParam(name = "type") String type) {
		// 提交图片到公众号
		JSONObject json;
		String ivv;
		String url;
		String media_id;
		int row;
		List<ResultIVV> success = new ArrayList<ResultIVV>();// 成功列表
		List<ResultIVV> dataFault = new ArrayList<ResultIVV>();// 数据库提交失败列表
		List<String> weFault = new ArrayList<String>();// 微信提交失败列表
		ModelAndView mv = new ModelAndView();
		String path = CommandCollection.GetLocalPath(type);
		boolean isVideo = type.equals(CommandCollection.MESSAGE_VIDEO);
		boolean isVoice = type.equals(CommandCollection.MESSAGE_VOICE);
		for (int i = 0; i < ivvs.length; ++i) {
			ivv = ivvs[i];
			json = WeGlobal.getInstance().addIV(path + ivv, type);
			if (null != json) {
				media_id = json.getString("media_id");
				if (null != media_id) {// 上传成功
					ResultIVV rivv = new ResultIVV();
					rivv.setIvv(ivv);
					rivv.setMedia_id(media_id);
					if (isVideo) {
						row = adVideoService.updateVideo(ivv, media_id);
					} else if(isVoice){
						row = adVoiceService.updateVoice(ivv, media_id);
					} else {
						url = json.getString("url");
						url = url.replaceAll("\\\\", "");
						rivv.setUrl(url);
						row = adIVService.updateIV(ivv, media_id, url);
					}
					if (row > 0) {// 更新成功
						success.add(rivv);// 利用lastIndexof("|")
					} else {
						dataFault.add(rivv);// 利用lastIndexof("|")
					}
				} else {// 失败
					weFault.add(ivv);
				}
			} else {
				mv.addObject("msg", "上传到公众号失败，可能是图片不存在");
			}
		}
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.addObject("weFault", weFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 清除图片并删除微信服务器
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/clearIVVToWe")
	public ModelAndView clearImageToWe(@RequestParam(name = "ivvs[]") String[] ivvs,
			@RequestParam(name = "type") String type) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		boolean isVideo = type.equals(CommandCollection.MESSAGE_VIDEO);
		boolean isVoice = type.equals(CommandCollection.MESSAGE_VOICE);
		for (int i = 0; i < ivvs.length; ++i) {
			deleteControl(ivvs[i], isVideo, isVoice, false, success, dataFault, null);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 删除服务器上的图文图片
	 * 
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/deleteIVVToWe")
	public ModelAndView deleteIVVToWe(@RequestParam(name = "ivvs[]") String[] ivvs,
			@RequestParam(name = "type") String type) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		boolean isVideo = type.equals(CommandCollection.MESSAGE_VIDEO);
		boolean isVoice = type.equals(CommandCollection.MESSAGE_VOICE);
		String path = CommandCollection.GetLocalPath(type);
		for (int i = 0; i < ivvs.length; ++i) {
			deleteControl(ivvs[i], isVideo, isVoice, true, success, dataFault, path);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	private void deleteControl(String ivv, boolean isVideo, boolean isVoice, boolean isDelete, List<String> success,
			List<String> dataFault, String path) {
		int row = 0;
		int dot = ivv.indexOf('|');
		String media_id = ivv.substring(dot + 1);
		ivv = ivv.substring(0, dot);
		// 删除服务器
		boolean isOK = true;
		if(media_id.length() > 0 && !media_id.equals("null")) {
			JSONObject json = WeGlobal.getInstance().deleteNIVV(media_id);
			isOK = json.getInt("errcode") == 0;
		}
		if (isOK) {
			if (isVideo) {
				if (isDelete) {
					WeGlobal.getInstance().unload(path, ivv);
					row = adVideoService.deleteVideo(ivv);
				} else {
					row = adVideoService.updateVideo(ivv, "");// 清空
				}
			} else if(isVoice) {
				if (isDelete) {
					WeGlobal.getInstance().unload(path, ivv);
					row = adVoiceService.deleteVoice(ivv);
				} else {
					row = adVoiceService.updateVoice(ivv, "");// 清空
				}
			} else {
				if (isDelete) {
					WeGlobal.getInstance().unload(path, ivv);
					row = adIVService.deleteIV(ivv);
				} else {
					row = adIVService.updateIV(ivv, "", "");// 清空
				}
			}
			if (row > 0) {// 更新成功
				success.add(ivv);
			} else {
				dataFault.add(ivv);
			}
		} else {
			dataFault.add(ivv);
		}
	}
}
