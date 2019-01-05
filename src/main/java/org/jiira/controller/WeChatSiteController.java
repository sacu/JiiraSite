package org.jiira.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiira.pojo.we.WeToken;
import org.jiira.pojo.we.authorization.WeHAT;
import org.jiira.pojo.we.authorization.WeUserInfo;
import org.jiira.service.WeChatService;
import org.jiira.utils.CommandCollection;
import org.jiira.we.DecriptUtil;
import org.jiira.we.SAHTML;
import org.jiira.we.SAHttpTable;
import org.jiira.we.SAURLConnection;
import org.jiira.we.StrComparator;
import org.jiira.we.WeGlobal;
import org.jiira.we.message.WeChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.sf.json.JSONObject;

/**
 * 微信的
 * @author time
 *
 */
@Controller
@RequestMapping("/we")
public class WeChatSiteController {
	// 日志记录
	private static final Logger logger = LoggerFactory.getLogger(WeChatSiteController.class);

	@Autowired
	private WeChatService weChatService = null;

	/**
	 * 微信token验证
	 * 
	 * @param roleName
	 * @param note
	 * @return
	 */
	@RequestMapping(value = "as", method = RequestMethod.GET)
	public void weChat(PrintWriter out, WeToken weToken) {
		logger.error(weToken.getSignature() + "|" + weToken.getTimestamp() + "|" + weToken.getNonce() + "|"
				+ weToken.getEchostr());
		/**
		 * 组装 token、timestamp、nonce，进行字典序排序，中后两个值是微信给的 然后使用排序后的值进行sha1加密
		 * 使用sha1的加密值和signature最比较，如果比较成功，则证明是微信的请求，并且表示“接入生效”
		 */
		ArrayList<String> sort = new ArrayList<>(3);
		sort.add(CommandCollection.Token);
		sort.add(weToken.getTimestamp());
		sort.add(weToken.getNonce());
		Collections.sort(sort, new StrComparator());
		String _token = "";
		for (int i = 0; i < 3; ++i) {
			_token += sort.get(i);
		}
		logger.warn("##############开始微信接入##############");
		logger.warn("token:" + CommandCollection.Token);
		logger.warn("appID:" + CommandCollection.AppID);
		logger.warn("appsecret:" + CommandCollection.Appsecret);
		logger.warn("signature:" + weToken.getSignature());
		logger.warn("timestamp:" + weToken.getTimestamp());
		logger.warn("nonce:" + weToken.getNonce());
		logger.warn("echostr:" + weToken.getEchostr());
		logger.warn("字典序:" + _token);
		// 组装
		String digest = DecriptUtil.SHA1(_token);
		logger.warn("sha1加密:" + digest);
		if (digest.equals(weToken.getSignature())) {
			logger.warn("接入成功，返回echostr");
			out.print(weToken.getEchostr());
		} else {
			logger.warn("接入失败Error");
			out.print("Error");
		}
		out.flush();
		out.close();
	}

	@RequestMapping(value = "as", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void weChat(HttpServletResponse response, HttpServletRequest request) {
		// 解析用户发来的数据
		logger.warn("收到消息#################");
		WeChatMessage msg = weChatService.getMessage(request);
		msg.print(logger);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			msg.identityConvert();// 转换发送者和接收者身份
			String strMsg = weChatService.handler(msg);// 执行修改msg内容
			out = response.getWriter();
			out.print(strMsg);
			logger.warn("回复消息 : " + strMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 获取用户信息
	 */

	@RequestMapping(value = "/userInfo")
	public void getUserInfo(HttpServletResponse response, @RequestParam(value = "", required = false) String code) {
		WeHAT hat = CommandCollection.HAT;
		if (null == hat) {// 没有密钥
			if (null != code) {// 获取密钥
				CommandCollection.HCODE = code;
				hat = WeGlobal.getInstance().getHAT(code);
			} else {// 没有代码
//				code = CommandCollection.HCODE;
				WeGlobal.getInstance().getCode("we/userInfo");
			}
			return;
		}
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.USER_INFO);
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
