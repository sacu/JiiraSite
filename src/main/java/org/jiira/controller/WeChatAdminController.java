package org.jiira.controller;

import java.io.PrintWriter;

import org.jiira.we.SAHTML;
import org.jiira.we.WeGlobal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class WeChatAdminController {
	@RequestMapping(value = "createMenu")
	public void createMenu(PrintWriter out) {
		SAHTML html = WeGlobal.getInstance().createMenu();
		out.println("create menu" + html.getBody());
		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "createAccessToken", method = RequestMethod.GET)
	public void createAccessToken(PrintWriter out) {
		SAHTML html = WeGlobal.getInstance().createAccessToken();
		out.println("create access token : " + html.getBody());
		out.flush();
		out.close();
	}
}
