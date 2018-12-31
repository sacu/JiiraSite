package org.jiira.controller;

import org.jiira.pojo.Login;
import org.jiira.pojo.Register;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/command")
public class CommandController {
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping("/register")
	public ModelAndView doRegister(Register register) {
	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("login");
	    return mv;
	}
	/**
	 * 先写个登陆
	 */
	@RequestMapping("/login")
	public ModelAndView doLogin(Login login) {
		System.out.println(login.getUserName());
		System.out.println(login.getPassWord());
	    ModelAndView mv = new ModelAndView();
	    mv.setViewName("index");
	    return mv;
	}
}
