package org.jiira.controller;

import org.jiira.pojo.ad.AdUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理
 * @author time
 *
 */
@Controller
@RequestMapping("/")
@SessionAttributes(value="adUser", types= {AdUser.class})
public class RootController {
	@RequestMapping("ad")
	public ModelAndView ad() {
		ModelAndView mv = new ModelAndView();
	    mv.setViewName("redirect:/ad/index");
	    return mv;
	}
	@RequestMapping("we")
	public ModelAndView we() {
		ModelAndView mv = new ModelAndView();
	    mv.setViewName("redirect:/we/error");
	    return mv;
	}
}
