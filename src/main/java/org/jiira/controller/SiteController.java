package org.jiira.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jiira.pojo.ad.AdIV;
import org.jiira.pojo.ad.AdNews;
import org.jiira.service.AdMateService;
import org.jiira.utils.CommandCollection;
import org.jiira.we.WeGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONObject;

/**
 * 主要是网站的
 * @author time
 *
 */
@Controller
@RequestMapping("/we")
public class SiteController {
	// 日志记录
	private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

	@Autowired
	private AdMateService<AdNews> adNewsService = null;

	@Autowired
	private AdMateService<AdIV> adIVService = null;
	
	@RequestMapping(value="/getNews")
	public ModelAndView getNews(int id, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		AdNews adNews = adNewsService.selectById(id);
		request.setAttribute("page", "news");
		request.setAttribute("adNews", JSONObject.fromObject(adNews).toString());
		AdIV adIV = adIVService.selectIVByMediaId(adNews.getThumb_media_id());
		request.setAttribute("thumb", adIV.getIV());
	    
	    return mv;
	}
	
	/**
	 * 获取到微信用户信息
	 */
	@RequestMapping("/c")
	public ModelAndView c(HttpServletRequest request, @RequestParam(required = false, name="redirect") String redirect, 
			@RequestParam(name="code")String code, @RequestParam(name="state")String state) {
		ModelAndView mv = new ModelAndView();
		//获取用户信息
		JSONObject json = WeGlobal.getInstance().getUserInfo(code);
		request.setAttribute("page", redirect);//写入index执行页面规则
		request.setAttribute("json", json);
		mv.setViewName("we/c");//去微信页
		return mv;
	}
	/**
	 * 通过微信授权跳转到地址
	 * @param url 要跳转的地址
	 * @param code
	 */
	@RequestMapping(value = "/redirect")
	public String redirect(@RequestParam(name="redirect") String redirect) {
		String redirect_uri = "";
		try {
			redirect_uri = URLEncoder.encode(CommandCollection.WEB_NAME +"we/c?redirect=" + redirect, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("redirect_uri encode error");
		}
		String url = CommandCollection.AUTH_CODE + "?" + "appid=" + CommandCollection.AppID
				+"&redirect_uri=" + redirect_uri + "&response_type=code"
				+"&scope=snsapi_userinfo" + "&state=test#wechat_redirect";
		return "redirect:" + url;//使用重定向到用户验证
	}
}
