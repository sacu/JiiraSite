package org.jiira.controller;

import org.jiira.pojo.Login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理
 * @author time
 *
 */
@Controller
@RequestMapping("/ad")
public class AdminController {
	/**
	 * 去登陆页面
	 */
	@RequestMapping("/login")
	public ModelAndView doLogin(Login login) {
		System.out.println(login.getUserName());
		System.out.println(login.getPassWord());
	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("login");
	    return mv;
	}
}
