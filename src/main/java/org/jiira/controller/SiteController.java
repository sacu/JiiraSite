package org.jiira.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jiira.pojo.ad.WeUser;
import org.jiira.service.WeUserService;
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
	private WeUserService weUserService = null;
	/**
	 * 获取到微信用户信息
	 */
	@RequestMapping("/c")
	public ModelAndView c(HttpServletRequest request, @RequestParam(required = false, name="redirect") String redirect, 
			@RequestParam(name="code")String code, @RequestParam(name="state")String state) {
		//获取用户信息
		JSONObject json = WeGlobal.getInstance().getUserInfo(code);
		json.remove("privilege");//老报错 不要了
		logger.error("调试code：" + code);
		//从前TX又有个BUG，就是URL只能传一个参数，所以只能把所有参数压缩到第一个里边
		WeUser weUser = WeGlobal.getInstance().getClass(json.toString(), WeUser.class);
		WeUser _weUser = weUserService.selectWeUser(weUser.getOpenid());
		if(null == _weUser) {//用户不存在
			logger.error(JSONObject.fromObject(weUser).toString());
			int rows = weUserService.insertWeUser(weUser);
			if(rows != 0) {
				weUser = weUserService.selectWeUser(weUser.getOpenid());
			}
		}
		request.getSession().setAttribute("weUser", weUser);
		request.getSession().setAttribute("code", code);//测试用
		return jump(request, redirect);
	}
	@RequestMapping("/ic")
	public ModelAndView ic(HttpServletRequest request, String redirect) {
		return jump(request, redirect);
	}
	private ModelAndView jump(HttpServletRequest request, String redirect) {
		ModelAndView mv = new ModelAndView();
		String[] args = redirect.split("\\*");
		String[] group;
		request.setAttribute("page", args[0]);//写入index执行页面规则
		for(int i = 1; i < args.length; ++i) {//写入其他参数(包含news_id)
			group = args[i].split("=");
			request.setAttribute(group[0], group[1]);
		}
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
