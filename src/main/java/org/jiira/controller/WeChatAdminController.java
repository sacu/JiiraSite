package org.jiira.controller;

import java.util.ArrayList;
import java.util.List;

import org.jiira.pojo.ad.AdIV;
import org.jiira.pojo.ad.AdNews;
import org.jiira.pojo.ad.AdNewsImage;
import org.jiira.pojo.ad.AdNewsName;
import org.jiira.pojo.ad.AdNewsType;
import org.jiira.pojo.ad.AdVideo;
import org.jiira.pojo.ad.AdVoice;
import org.jiira.pojo.ad.ResultIVV;
import org.jiira.service.AdMateService;
import org.jiira.service.AdNewsNameService;
import org.jiira.service.AdNewsTypeService;
import org.jiira.utils.CommandCollection;
import org.jiira.we.SAHTML;
import org.jiira.we.WeGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import net.sf.json.JSONObject;

/**
 * 微信admin后台管理,通过 AdminController登入后调用
 * @author time
 *
 */
@Controller
@RequestMapping("/ad")
public class WeChatAdminController {

	@Autowired
	private AdMateService<AdNewsImage> adNewsImageService = null;
	@Autowired
	private AdMateService<AdIV> adIVService = null;
	@Autowired
	private AdMateService<AdVoice> adVoiceService = null;
	@Autowired
	private AdMateService<AdVideo> adVideoService = null;
	@Autowired
	private AdMateService<AdNews> adNewsService = null;

	@Autowired
	private AdNewsTypeService adNewsTypeService = null;
	@Autowired
	private AdNewsNameService adNewsNameService = null;
	/**
	 * 创建菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createMenu")
	public ModelAndView createMenu() {
		ModelAndView mv = new ModelAndView();
		JSONObject json = WeGlobal.getInstance().createMenu();
		mv.addObject(json);
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
	 * 获取图文类型
	 */
	@RequestMapping(value = "/getNewsTypeList")
	public ModelAndView getNewsTypeList() {
		ModelAndView mv = new ModelAndView();
		List<AdNewsType> adNewsTypeList = adNewsTypeService.selectNewsType();
		mv.addObject(adNewsTypeList);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/addNewsType")
	public ModelAndView addNewsType(String name) {
		ModelAndView mv = new ModelAndView();
		int rows = adNewsTypeService.insertNewsType(name);
		if(rows > 0) {
			mv.addObject("添加成功");
		} else {
			mv.addObject("添加失败");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/delNewsType")
	public ModelAndView delNewsType(int id) {
		ModelAndView mv = new ModelAndView();
		int rows = adNewsTypeService.deleteNewsType(id);
		if(rows > 0) {
			mv.addObject("删除成功");
		} else {
			mv.addObject("删除失败");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/reNewsType")
	public ModelAndView reNewsType(int id, String name) {
		ModelAndView mv = new ModelAndView();
		int rows = adNewsTypeService.updateNewsType(id, name);
		if(rows > 0) {
			mv.addObject("修改成功");
		} else {
			mv.addObject("修改失败");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	/**
	 * 获取书籍列表
	 */
	@RequestMapping(value = "/getNewsNameList")
	public ModelAndView getNewsNameList() {
		ModelAndView mv = new ModelAndView();
		List<AdNewsName> adNewsNameList = adNewsNameService.selectNewsName();
		mv.addObject(adNewsNameList);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/addNewsName")
	public ModelAndView addNewsName(AdNewsName adNewsName) {
		ModelAndView mv = new ModelAndView();
		int rows = adNewsNameService.insertNewsName(adNewsName);
		if(rows > 0) {
			mv.addObject("添加成功");
		} else {
			mv.addObject("添加失败");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/delNewsName")
	public ModelAndView delNewsName(int id) {
		ModelAndView mv = new ModelAndView();
		int rows = adNewsNameService.deleteNewsName(id);
		if(rows > 0) {
			mv.addObject("删除成功");
		} else {
			mv.addObject("删除失败");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/reNewsName")
	public ModelAndView reNewsName(AdNewsName adNewsName) {
		ModelAndView mv = new ModelAndView();
		int rows = adNewsNameService.updateNewsName(adNewsName);
		if(rows > 0) {
			mv.addObject("修改成功");
		} else {
			mv.addObject("修改失败");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	/**
	 * 上传图文到服务器
	 */
	@RequestMapping(value = "/addNews")
	public ModelAndView addNews(AdNews adNews) {
		ModelAndView mv = new ModelAndView();
		int rows = adNewsService.insert(adNews);
		if(rows > 0) {
			mv.addObject("插入成功");
		} else {
			mv.addObject("插入失败");
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/getNewsList")
	public ModelAndView getNewsList() {
		ModelAndView mv = new ModelAndView();
		List<AdNews> adNewsList = adNewsService.select();
		mv.addObject(adNewsList);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}
	@RequestMapping(value = "/getNewsToWe")
	public ModelAndView getNewsToWe(@RequestBody AdNews[] adNews) {
		// 提交图片到公众号
		JSONObject json;
		AdNews adNew;
		String media_id;
		ModelAndView mv = new ModelAndView();
		int row;
		List<Integer> success = new ArrayList<>();// 成功列表
		List<Integer> dataFault = new ArrayList<>();// 数据库提交失败列表
		List<Integer> weFault = new ArrayList<>();// 微信提交失败列表
		for (int i = 0; i < adNews.length; ++i) {
			adNew = adNews[i];
			json = WeGlobal.getInstance().addNews(adNew);
			if (null != json) {
				media_id = json.getString("media_id");
				if (null != media_id) {// 上传成功
					row = adNewsService.update(adNew.getId(), media_id);
					if (row > 0) {// 更新成功
						success.add(adNew.getId());
					} else {
						dataFault.add(adNew.getId());
					}
				} else {// 失败
					weFault.add(adNew.getId());
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
	@RequestMapping(value = "/clearNewsToWe")
	public ModelAndView clearNewsToWe(@RequestBody AdNews[] adNews) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		int id;
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		for (int i = 0; i < adNews.length; ++i) {
			id = adNews[i].getId();
			deleteControl(id, CommandCollection.MESSAGE_NEWS, false, success, dataFault);
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
	@RequestMapping(value = "/deleteNewsToWe", method=RequestMethod.POST)
	public ModelAndView deleteNewsToWe(@RequestBody AdNews[] adNews) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		AdNews adNew;
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		for (int i = 0; i < adNews.length; ++i) {
			adNew = adNews[i];
			deleteControl(adNew.getId(), adNew.getMedia_id(), CommandCollection.MESSAGE_NEWS, true, success, dataFault);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 推送图文
	 * @param adNews
	 * @return
	 */
	@RequestMapping(value = "/pushNewsToWe")
	public ModelAndView pushNewsToWe(@RequestBody String media_id) {
		ModelAndView mv = new ModelAndView();
		if(null == media_id || media_id.length() == 0) {
			mv.addObject("MediaID 不能为空");
		} else {
			JSONObject json = WeGlobal.getInstance().pushMessageByOpenID(media_id);
			mv.addObject(json);
		}
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
			List<AdNewsImage> adNewsImages = adNewsImageService.check(newsImages);
			if (adNewsImages.size() == 0) {// 可以上传
				// 保存图片
				if (WeGlobal.getInstance().upload(CommandCollection.NEWS_IMAGE_PATH, newsImagesFiles)) {
					// 写入数据库
					int rows = adNewsImageService.ignore(newsImages);
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
		List<AdNewsImage> adNewsImages = adNewsImageService.select();
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
					row = adNewsImageService.update(newsImage, url);
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
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		for (int i = 0; i < newsImages.length; ++i) {
			newsImage = newsImages[i];
			deleteControl(newsImage, CommandCollection.MESSAGE_NEWS_IMAGE, false, success, dataFault, CommandCollection.NEWS_IMAGE_PATH);
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
			deleteControl(newsImage, CommandCollection.MESSAGE_NEWS_IMAGE, true, success, dataFault, CommandCollection.NEWS_IMAGE_PATH);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 上传资源到服务器
	 * 视频只能单独上传
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
				cu = adVideoService.check(ivvs.get(0)).size() == 0;
			} else if(isVoice) {
				cu = adVoiceService.check(ivvs).size() == 0;
			} else {
				cu = adIVService.check(ivvs).size() == 0;
			}
			if (cu) {// 可以上传
				// 保存
				int rows = 0;
				if (isVideo) {
					if (WeGlobal.getInstance().upload(path, ivsFiles.get(0))) {
						rows = adVideoService.ignore(ivvs.get(0), title, introduction);
					}
				} else {
					if (WeGlobal.getInstance().upload(path, ivsFiles)) {
						if(isVoice) {
							rows = adVoiceService.ignore(ivvs);
						} else {
							rows = adIVService.ignore(ivvs, type);
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
			List<AdVideo> adVideos = adVideoService.select();
			mv.addObject(adVideos);
		} else if(type.equals(CommandCollection.MESSAGE_VOICE)){
			List<AdVoice> adVoices = adVoiceService.select();
			mv.addObject(adVoices);
		} else {
			List<AdIV> adIVs = adIVService.selectByType(type);
			mv.addObject(adIVs);
		}
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 上传到微信服务器
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/getIVVToWe")
	public ModelAndView getIVVToWe(@RequestParam(name = "ivvs[]") String[] ivvs, @RequestParam(name = "type") String type,
			@RequestParam(name = "titles[]", required = false) String[] titles, @RequestParam(name = "introductions[]", required = false) String[] introductions) {
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
			if(isVideo) {
				json = WeGlobal.getInstance().addVideo(path + ivv, type, titles[i], introductions[i]);
			} else {
				json = WeGlobal.getInstance().addIV(path + ivv, type);
			}
			if (null != json) {
				media_id = json.getString("media_id");
				if (null != media_id) {// 上传成功
					ResultIVV rivv = new ResultIVV();
					rivv.setIvv(ivv);
					rivv.setMedia_id(media_id);
					if (isVideo) {
						row = adVideoService.update(ivv, media_id);
					} else if(isVoice){
						row = adVoiceService.update(ivv, media_id);
					} else {
						url = json.getString("url");
						url = url.replaceAll("\\\\", "");
						rivv.setUrl(url);
						row = adIVService.update(ivv, media_id, url);
					}
					if (row > 0) {// 更新成功
						success.add(rivv);
					} else {
						dataFault.add(rivv);
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
			@RequestParam(name = "type") String type, @RequestParam(name = "media_ids[]") String[] media_ids) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		for (int i = 0; i < ivvs.length; ++i) {
			deleteControl(ivvs[i], media_ids[i], type, false, success, dataFault, null);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	/**
	 * 删除服务器上的图文图片
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/deleteIVVToWe")
	public ModelAndView deleteIVVToWe(@RequestParam(name = "ivvs[]") String[] ivvs,
			@RequestParam(name = "type") String type, @RequestParam(name = "media_ids[]") String[] media_ids) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		String path = CommandCollection.GetLocalPath(type);
		for (int i = 0; i < ivvs.length; ++i) {
			deleteControl(ivvs[i], media_ids[i], type, true, success, dataFault, path);
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("success", success);
		mv.addObject("dataFault", dataFault);
		mv.setView(new MappingJackson2JsonView());
		return mv;
	}

	private void deleteControl(int id, String type, boolean isDelete, List<String> success,
			List<String> dataFault) {
		deleteControl(String.valueOf(id), null, type, isDelete, success, dataFault, null);
	}
	private void deleteControl(int id, String media_id, String type, boolean isDelete, List<String> success,
			List<String> dataFault) {
		deleteControl(String.valueOf(id), media_id, type, isDelete, success, dataFault, null);
	}
	private void deleteControl(String nivv, String type, boolean isDelete, List<String> success,
			List<String> dataFault, String path) {
		deleteControl(nivv, null, type, isDelete, success, dataFault, path);
	}
	private void deleteControl(String nivv, String media_id, String type, boolean isDelete, List<String> success,
			List<String> dataFault, String path) {
		int row = 0;
		// 删除服务器
		boolean isOK = true;
		if(media_id != null && media_id.length() > 0 && !media_id.equals("null")) {//删除公众号空间
			JSONObject json = WeGlobal.getInstance().deleteNIVV(media_id);
			isOK = json.getInt("errcode") == 0;
		}
		if (isOK) {
			AdMateService<?> as;
			switch(type) {
				case CommandCollection.MESSAGE_VIDEO:as = adVideoService;break;//视频
				case CommandCollection.MESSAGE_VOICE:as = adVoiceService;break;//语音
				case CommandCollection.MESSAGE_NEWS:as = adNewsService;break;//新闻
				case CommandCollection.MESSAGE_NEWS_IMAGE:as = adNewsImageService;break;//新闻内图片
				default:as = adIVService;//普通图片和缩略图
			}
			if (isDelete) {
				if(type.equals(CommandCollection.MESSAGE_NEWS)) {
					row = as.delete(Integer.valueOf(nivv));
				} else {
					if(!type.equals(CommandCollection.MESSAGE_NEWS_IMAGE)) {
						WeGlobal.getInstance().unload(path, nivv);
					}
					row = as.delete(nivv);
				}
			} else if((row = as.update(nivv, "")) == 0) {// 清空
				if(type.equals(CommandCollection.MESSAGE_NEWS)) {
					row = as.update(Integer.valueOf(nivv), "");
				} else {
					row = as.update(nivv, "", "");//执行IVV清空
				}
			}
			if (row > 0) {// 更新成功
				success.add(nivv);
			} else {
				dataFault.add(nivv);
			}
		} else {
			dataFault.add(nivv);
		}
	}
}
