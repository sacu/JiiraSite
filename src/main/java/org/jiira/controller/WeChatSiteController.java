package org.jiira.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.jiira.pojo.we.authorization.WeHAT;
import org.jiira.pojo.we.authorization.WeUserInfo;
import org.jiira.utils.CommandCollection;
import org.jiira.we.SAHTML;
import org.jiira.we.SAHttpTable;
import org.jiira.we.SAURLConnection;
import org.jiira.we.WeGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/we")
public class WeChatSiteController {
	/**
	 * 获取用户信息
	 */
	private static final Logger logger = LoggerFactory.getLogger(WeChatSiteController.class);
	@RequestMapping(value = "/userInfo")
	public void getUserInfo(HttpServletResponse response, @RequestParam(value = "", required = false) String code) {
		WeHAT hat = CommandCollection.HAT;
		if (null == hat) {// 没有密钥
			if (null != code) {// 获取密钥
				CommandCollection.HCODE = code;
				hat = WeGlobal.getInstance().getHAT(code);
			} else {// 没有代码
				code = CommandCollection.HCODE;
				WeGlobal.getInstance().getCode("we/userInfo");
			}
			return;
		}
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL("https://api.weixin.qq.com/sns/userinfo");
		table.addParams("access_token", hat.getAccess_token());
		table.addParams("openid", hat.getOpenid());
		table.addParams("lang", "zh_CN");

		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		if (!json.has("errcode")) {
			WeUserInfo info = WeGlobal.getInstance().getClass(html.getBody(), WeUserInfo.class);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.println("openid : " + info.getOpenid());
				out.println("nickname : " + info.getNickname());
				out.println("sex : " + info.getSex());
				out.println("province : " + info.getProvince());
				out.println("city : " + info.getCity());
				out.println("country : " + info.getCountry());
				out.println("headimgurl : " + info.getHeadimgurl());
				String[] privilege = info.getPrivilege();
				for (int i = 0; i < privilege.length; ++i) {
					out.println("privilege " + i + " : " + privilege[i]);
				}
				out.println("unionid : " + info.getUnionid());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (null != out) {
					out.flush();
					out.close();
				}
			}
		} else {
			logger.error(json.getString("errcode"));
		}
	}
}
