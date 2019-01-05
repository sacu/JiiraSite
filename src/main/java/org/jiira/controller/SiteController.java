package org.jiira.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jiira.pojo.Login;
import org.jiira.pojo.ad.AdIV;
import org.jiira.pojo.ad.AdNews;
import org.jiira.service.AdMateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sf.json.JSONObject;

/**
 * 主要是网站的
 * @author time
 *
 */
@Controller
@RequestMapping("/we")
public class SiteController {

	@Autowired
	private AdMateService<AdNews> adNewsService = null;

	@Autowired
	private AdMateService<AdIV> adIVService = null;
	
	@RequestMapping(value="/news")
	public ModelAndView news(int id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		AdNews adNews = adNewsService.selectById(id);
		request.setAttribute("page", "news");
		request.setAttribute("adNews", JSONObject.fromObject(adNews).toString());
		AdIV adIV = adIVService.selectIVByMediaId(adNews.getThumb_media_id());
		request.setAttribute("thumb", adIV.getIV());
	    mv.setViewName("we/index");
	    return mv;
	}
	
	/**
	 * 去登陆页面
	 */
	@RequestMapping("/login")
	public ModelAndView doLogin(Login login, HttpSession session, RedirectAttributes redirectAttributes, ModelAndView mv) {
	    //登陆
		return mv;
	}
}
