package org.jiira.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jiira.pojo.Login;
import org.jiira.pojo.ad.AdUser;
import org.jiira.service.AdUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 后台管理
 * @author time
 *
 */
@Controller
@RequestMapping("/ad")
@SessionAttributes(value="adUser", types= {AdUser.class})
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdUserService adUserService = null;
	/**
	 * ad index
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView();
	    mv.setViewName("ad/index");
	    return mv;
	}
	/**
	 * 去登陆页面
	 */
	@RequestMapping("/login")
	public ModelAndView doLogin(Login login, HttpSession session, RedirectAttributes redirectAttributes, ModelAndView mv) {
	    //登陆
		if(login != null && login.getUserName() != null) {
			logger.error(login.getUserName());
		}
	    AdUser adUser = adUserService.getAdUser(login);
	    if(null != adUser) {
	    	session.setAttribute("adUser", adUser);
	    	session.setAttribute("page", "#welcome.jsp");
	    	redirectAttributes.addFlashAttribute("msg", "登陆成功");
	    } else {
	    	redirectAttributes.addFlashAttribute("msg", "用户不存在");
	    }
//	    redirect//request失效
//	    forward//路径会改变……js css丢失
		mv.setViewName("redirect:/ad");
		return mv;
	}
	/**
	 * 切换页面 首页、设置、关于
	 */
	@RequestMapping("/flip")
	public ModelAndView doFlip(String page, ModelAndView mv) {
	    mv.setViewName("/ad/" + page);
	    return mv;
	}
	/**
	 * 
	 * @param page
	 * @param mv
	 * @return
	 */
	@RequestMapping("/setting_flip")
	public ModelAndView doSettingFlip(String page, ModelAndView mv) {
	    mv.setViewName("/ad/setting_" + page);
	    return mv;
	}
	/**
	 * 用来测试的~随后可以删掉
	 * @param page
	 * @param mv
	 * @return
	 */
	@RequestMapping("/setting_news_image")
	public ModelAndView dotest(String page, ModelAndView mv) {
	    mv.setViewName("/ad/setting_news_image");
	    return mv;
	}
	/**
	 * 退出
	 * @param mv
	 * @return
	 */
	@RequestMapping("/exit")
	public ModelAndView doExit(Map<String,Object> map, ModelAndView mv, HttpSession session) {
		map.remove("adUser");//map 需要删除 不然session里还有
		session.removeAttribute("adUser");//session也要删除
		map.remove("page");//map 需要删除 不然session里还有
		session.removeAttribute("page");//session也要删除
	    mv.setViewName("redirect:/ad");
	    return mv;
	}
}
