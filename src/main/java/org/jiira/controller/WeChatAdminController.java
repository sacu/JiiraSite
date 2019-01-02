package org.jiira.controller;

import java.util.ArrayList;
import java.util.List;

import org.jiira.pojo.ad.AdNewsImage;
import org.jiira.service.AdNewsImageService;
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
				if (WeGlobal.getInstance().upload(newsImagesFiles)) {
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
		for (int i = 0; i < newsImages.length; ++i) {
			newsImage = newsImages[i];
			json = WeGlobal.getInstance().addNewsImage(CommandCollection.NEWS_IMAGE_PATH + newsImage);
			url = json.getString("url");
			if (null != url) {// 上传成功
				url = url.replaceAll("\\\\", "");
				row = adNewsImageService.updateNewsImage(newsImage, url);
				if (row > 0) {// 更新成功
					success.add(url + "|" + newsImage);// 利用lastIndexof("|")
				} else {
					dataFault.add(url + "|" + newsImage);// 利用lastIndexof("|")
				}
			} else {// 失败
				weFault.add(newsImage);
			}
		}
		ModelAndView mv = new ModelAndView();
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
	 * 删除服务器上的图片
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/deleteNewsImage")
	public ModelAndView deleteNewsImageToWe(@RequestParam(name = "newsImages[]") String[] newsImages) {
		// 图文内图片不用清除操作,只删除本地服务器即可
		String newsImage;
		List<String> success = new ArrayList<String>();// 成功列表
		List<String> dataFault = new ArrayList<String>();// 数据库提交失败列表
		for (int i = 0; i < newsImages.length; ++i) {
			newsImage = newsImages[i];
			if(WeGlobal.getInstance().unload(newsImage) && adNewsImageService.deleteNewsImage(newsImage) > 0) {
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
}
