package org.jiira.controller;

import java.util.ArrayList;
import java.util.List;

import org.jiira.pojo.ad.AdNewsImage;
import org.jiira.service.AdNewsImageService;
import org.jiira.we.SAHTML;
import org.jiira.we.WeGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
@RequestMapping("/ad")
public class WeChatAdminController {

	@Autowired
	private AdNewsImageService adNewsImageService = null;
	
	/**
	 * 创建菜单
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
	 * 上传图文内图片
	 * @param files
	 * @return
	 */
	@RequestMapping(value = "/addNewsImage")
	public ModelAndView addNewsImage(MultipartFile[] files) {
		List<String> newsImages = new ArrayList<String>();
		MultipartFile file;
		String orig;
		int dot;
		for(int i = 0; i < files.length; ++i) {
			orig = files[i].getOriginalFilename();
			dot = orig.lastIndexOf('.');
			newsImages.add(orig.substring(0, dot));
		}
		//检测数据库
		List<AdNewsImage> adNewsImages = adNewsImageService.checkNewsImage(newsImages);
		//保存图片
//		WeGlobal.getInstance().upload(files);
		//提交图片到公众号
//		SAHTML html = WeGlobal.getInstance().addNewsImage("F:\\icon.jpg");
		ModelAndView mv = new ModelAndView();
		mv.setView(new MappingJackson2JsonView());
		mv.addObject(adNewsImages);
		return mv;
	}
}
