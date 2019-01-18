package org.jiira.we;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.jiira.pojo.ad.AdNews;
import org.jiira.pojo.we.ai.image.WeImageSay;
import org.jiira.pojo.we.ai.text.WeTextSay;
import org.jiira.pojo.we.ai.voice.WeVoiceSay;
import org.jiira.pojo.we.ai.voice.WeYTVoiceSay;
import org.jiira.pojo.we.authorization.WeHAT;
import org.jiira.pojo.we.cmenu.CMenu;
import org.jiira.pojo.we.mate.news.MateNews;
import org.jiira.pojo.we.pay.WeJSSDKConfig;
import org.jiira.pojo.we.pay.WePay;
import org.jiira.pojo.we.pay.WePayRequest;
import org.jiira.pojo.we.pay.WePayXML;
import org.jiira.pojo.we.push.MessageByID;
import org.jiira.utils.CommandCollection;
import org.jiira.we.url.SAHTML;
import org.jiira.we.url.SAHttpKVO;
import org.jiira.we.url.SAHttpTable;
import org.jiira.we.url.SAURLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONObject;

public class WeGlobal {
	private static final Logger logger = LoggerFactory.getLogger(WeGlobal.class);
	private ObjectMapper mapper;

	private static WeGlobal instance;

	public static WeGlobal getInstance() {
		if (null == instance) {
			instance = new WeGlobal();
		}
		return instance;
	}

	private WeGlobal() {
		mapper = new ObjectMapper();
	}
	
	public WeJSSDKConfig getWeJSSDKConfig() {
		//获取jsapi_ticket
		checkJSAPITicket();
		WeJSSDKConfig jssdkConfig = new WeJSSDKConfig();
		ArrayList<SAHttpKVO> params = new ArrayList<>();
		jssdkConfig.setDebug(false);
		jssdkConfig.setAppId(CommandCollection.AppID);
		jssdkConfig.setTimestamp(DecriptUtil.create_timestamp());
		jssdkConfig.setNonceStr(DecriptUtil.create_nonce_str());//签名
		jssdkConfig.setJsApiList(new String[] {"chooseWXPay"});//参与
		
		params.add(new SAHttpKVO("noncestr", jssdkConfig.getNonceStr()));
		params.add(new SAHttpKVO("jsapi_ticket", CommandCollection.JSSDKTicket));
		params.add(new SAHttpKVO("url", CommandCollection.WE_REDIRECT + CommandCollection.WE_RECHARGE));//不包含#后边.split('#')[0]
//		params.add(new SAHttpKVO("url", "http://127.0.0.1:8080/JiiraSite/we/ict?redirect=recharge&openid=o3JwK6I-dL64v6LRb_RSLR_xsEiI"));//不包含#后边.split('#')[0]
		jssdkConfig.setSignature(DecriptUtil.ReqSignPayConfig(params));
		return jssdkConfig;
	}
	/**
	 * 微信支付信息
	 * @return
	 */
	public WePayRequest getWePayRequest(String prepay_id) {
		ArrayList<SAHttpKVO> params = new ArrayList<>();
		WePayRequest wepayr = new WePayRequest();
		wepayr.setAppId(CommandCollection.AppID);
		params.add(new SAHttpKVO("appId", wepayr.getAppId()));
		String timeStamp = DecriptUtil.create_timestamp();
		wepayr.setTimeStamp(Integer.valueOf(timeStamp));
		params.add(new SAHttpKVO("timeStamp", timeStamp));
		wepayr.setNonceStr(DecriptUtil.create_nonce_str());
		params.add(new SAHttpKVO("nonceStr", wepayr.getNonceStr()));
		wepayr.setWe_package("prepay_id=" + prepay_id);//prepay_id=test",
		params.add(new SAHttpKVO("package", wepayr.getWe_package()));
		wepayr.setSignType("MD5");
		params.add(new SAHttpKVO("signType", wepayr.getSignType()));
		wepayr.setPaySign(DecriptUtil.ReqSignPay(params, false));
		return wepayr;
	}
	/**
	 * 微信支付
	 */
	public WePay getWePay(String ip, int money, String openid) {
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.WE_PAY);
		ArrayList<SAHttpKVO> params = new ArrayList<>();
		WePayXML wepayxml = new WePayXML();
		wepayxml.setAppid(CommandCollection.AppID);
		params.add(new SAHttpKVO("appid", wepayxml.getAppid()));
		wepayxml.setMch_id(CommandCollection.MCHID);
		params.add(new SAHttpKVO("mch_id", wepayxml.getMch_id()));
		wepayxml.setNonce_str(DecriptUtil.create_nonce_str());
		wepayxml.setOpenid(openid);
		params.add(new SAHttpKVO("openid", wepayxml.getOpenid()));
		params.add(new SAHttpKVO("nonce_str", wepayxml.getNonce_str()));
		wepayxml.setBody("充值代金券:" + money);
		params.add(new SAHttpKVO("body", wepayxml.getBody()));//描述
		wepayxml.setOut_trade_no(DecriptUtil.create_timestamp());
		params.add(new SAHttpKVO("out_trade_no", wepayxml.getOut_trade_no()));//订单号
		wepayxml.setTotal_fee(money);
		params.add(new SAHttpKVO("total_fee", String.valueOf(wepayxml.getTotal_fee())));//金额
		wepayxml.setSpbill_create_ip(ip);
		params.add(new SAHttpKVO("spbill_create_ip", wepayxml.getSpbill_create_ip()));//看看request 能不能获取到了
		wepayxml.setNotify_url(CommandCollection.CALL_PAY);
		params.add(new SAHttpKVO("notify_url", wepayxml.getNotify_url()));
		wepayxml.setTrade_type("JSAPI");
		params.add(new SAHttpKVO("trade_type", wepayxml.getTrade_type()));
		wepayxml.setSign(DecriptUtil.ReqSignPay(params, false));//最后计算
		
		XStream xstream = new XStream();
		xstream.alias("xml", wepayxml.getClass());
		String xml = xstream.toXML(wepayxml).replaceAll("__", "_");
		table.setJson(xml);
		table.setUseJson(true);
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		xstream.alias("xml", WePay.class);
		WePay wePay = (WePay)xstream.fromXML(html.getBody());
		return wePay;
	}
	/**
	 * 获取机器人对话
	 * 
	 * @param message
	 * @return
	 */
	public WeTextSay getTextSay(String text) {
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.AI_Text_Say);
		table.addParams("app_id", CommandCollection.AI_AppID);
		table.addParams("nonce_str", DecriptUtil.create_nonce_str());
		table.addParams("question", text);
		table.addParams("session", "123456");
		table.addParams("time_stamp", DecriptUtil.create_timestamp());
		ArrayList<SAHttpKVO> params = table.getParams();
		table.addParams("sign", DecriptUtil.ReqSignAI(params));
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		return getClass(html.getBody(), WeTextSay.class);
	}
	/**
	 * 获取图片转对话
	 * @return
	 */
	public WeImageSay getImageSay(String image, String session_id) {
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.AI_Image_Say);
		table.addParams("app_id", CommandCollection.AI_AppID);
		table.addParams("image", image);
		table.addParams("session_id", session_id);
		table.addParams("time_stamp", DecriptUtil.create_timestamp());
		table.addParams("nonce_str", DecriptUtil.create_nonce_str());
		ArrayList<SAHttpKVO> params = table.getParams();
		table.addParams("sign", DecriptUtil.ReqSignAI(params));
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		return getClass(html.getBody(), WeImageSay.class);
	}
	/**
	 * 获取语音转对话
	 * @return
	 */
	public WeVoiceSay getVoiceSay(String text) {
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.AI_Voice_Say);
		table.addParams("app_id", CommandCollection.AI_AppID);
		table.addParams("time_stamp", DecriptUtil.create_timestamp());
		table.addParams("nonce_str", DecriptUtil.create_nonce_str());
		table.addParams("speaker", "7");//声音类型 1普通话男声 5静琪女声 6欢馨女声 7碧萱女声
		table.addParams("format", "3");//声音格式 1PCM 2WAV 3MP3
		table.addParams("volume", "10");//音量-10,10
		table.addParams("speed", "100");//语速,默认100 50,200
		table.addParams("text", text);//要合成的文本
		table.addParams("aht", "0");//高音设定,默认0 -24,24
		table.addParams("apc", "58");//改变语调,默认58 0,100
		ArrayList<SAHttpKVO> params = table.getParams();
		table.addParams("sign", DecriptUtil.ReqSignAI(params));
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		return getClass(html.getBody(), WeVoiceSay.class);
	}

	public WeYTVoiceSay getYTVoiceSay(String text) {
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.AI_YTVoice_Say);
		table.addParams("app_id", CommandCollection.AI_AppID);
		table.addParams("time_stamp", DecriptUtil.create_timestamp());
		table.addParams("nonce_str", DecriptUtil.create_nonce_str());
		table.addParams("speed", "0");//语速,默认0 -2,2
		table.addParams("text", text);//要合成的文本
		table.addParams("model_type", "0");//高音设定,默认0 0,2
		ArrayList<SAHttpKVO> params = table.getParams();
		table.addParams("sign", DecriptUtil.ReqSignAI(params));
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		return getClass(html.getBody(), WeYTVoiceSay.class);
	}
	public JSONObject createMenu() {
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.CREATE_MENU + CommandCollection.AccessToken);
		CMenu menu = CommandCollection.GetMenu();
		String menuStr = JSONObject.fromObject(menu).toString();
		logger.error(menuStr);
		table.setUseJson(true);
		table.setJson(menuStr);
		
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		logger.error(json.toString());
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return createMenu();
			}
		}
		return json;
	}

	public <T> T getClass(String body, Class<T> cls) {
		if (null == mapper) {
			mapper = new ObjectMapper();
		}
		try {
			return mapper.readValue(body, cls);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return null;
	}

	public JSONObject clearQuota() {
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.CLEAR_QUOTA + CommandCollection.AccessToken);
		String quotaStr = JSONObject.fromObject(CommandCollection.GetWeClearQuota()).toString();
		table.setJson(quotaStr);
		table.setUseJson(true);
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return clearQuota();
			}
		}
		return json;
	}

	/**
	 * access token
	 * 
	 * @return
	 */
	public SAHTML createAccessToken() {
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.ACCESS_TOKEN);
		table.addParams("grant_type", "client_credential");
		table.addParams("appid", CommandCollection.AppID);
		table.addParams("secret", CommandCollection.Appsecret);
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		if (json.containsKey("errcode")) {// 失败了
			logger.error("create access token errcode ：" + json.getString("errcode"));
			logger.error("create access errmsg ：" + json.getString("errmsg"));
		} else {
			CommandCollection.AccessToken = json.getString("access_token");
			logger.error("access token : " + CommandCollection.AccessToken);
			// 写入本地
			File file = null;
			FileOutputStream out = null;
			try {
				file = new File(CommandCollection.ACCESS_TOKEN_PATH);
				if (file.exists()) {
					file.delete();
					file.createNewFile();
				}
				out = new FileOutputStream(file, true);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				out.write(CommandCollection.AccessToken.getBytes("utf-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("创建access token 文件错误 :" + e.getMessage());
			} finally {
				try {
					if (null != out) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("关闭粗偶？ :" + e.getMessage());
				}
			}
			logger.error(CommandCollection.AccessToken);
		}
		return html;
	}

	private void checkAccessToken() {
		if (null != CommandCollection.AccessToken) {// 如果有~则直接返回
			return;
		}
		File file = new File(CommandCollection.ACCESS_TOKEN_PATH);
		if (!file.exists() || file.isDirectory()) {// 如果不存在，则创建并返回（从创建中获取）
			createAccessToken();// 读取accesstoken
		}
		// 如果第一次访问AccessToken且服务器已经生成过AccessToken，则获取
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			CommandCollection.AccessToken = br.readLine();
		} catch (IOException e) {
			logger.error(e.getMessage());
			//createAccessToken();// 读取accesstoken
		} finally {
			if (null != br) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * jsapi_ticket
	 * @param openid
	 * @return
	 */
	public SAHTML createJSAPITicket() {
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.JSSDK_TICKET + CommandCollection.AccessToken + "&type=jsapi");
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			CommandCollection.JSSDKTicket = json.getString("ticket");
			logger.error("JSSDKTicket : " + CommandCollection.JSSDKTicket);
			// 写入本地
			File file = null;
			FileOutputStream out = null;
			try {
				file = new File(CommandCollection.JSSDK_TICKET_PATH);
				if (file.exists()) {
					file.delete();
					file.createNewFile();
				}
				out = new FileOutputStream(file, true);
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				out.write(CommandCollection.JSSDKTicket.getBytes("utf-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("创建JSSDKTicket 文件错误 :" + e.getMessage());
			} finally {
				try {
					if (null != out) {
						out.flush();
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.error("关闭粗偶？ :" + e.getMessage());
				}
			}
			logger.error(CommandCollection.JSSDKTicket);
		} else {
			if (code == 42001 || code == 40001) {
				logger.error("JSSDKTicket access 过期 = " + code);
				createAccessToken();
				return createJSAPITicket();//重新获取
			} else {
				logger.error("create JSSDKTicket errcode ：" + json.getString("errcode"));
				logger.error("create JSSDKTicket errmsg ：" + json.getString("errmsg"));
			}
		}
		return html;
	}
	private void checkJSAPITicket() {
		if (null != CommandCollection.JSSDKTicket) {// 如果有~则直接返回
			return;
		}
		File file = null;
		try {
			file = new File(CommandCollection.JSSDK_TICKET_PATH);
			if (!file.exists() || file.isDirectory()) {// 如果不存在，则创建并返回（从创建中获取）
				createJSAPITicket();// 创建
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		// 如果第一次访问JSAPITicket且服务器已经生成过AccessToken，则获取
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			CommandCollection.JSSDKTicket = br.readLine();
		} catch (IOException e) {
			logger.error(e.getMessage());
			//createJSAPITicket();// 读取JSAPITicket
		} finally {
			if (null != br) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//需要openid
	public JSONObject getUserInfoByOpenID(String openid) {
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.USER_INFO_OID);
		table.addParams("access_token", CommandCollection.AccessToken);
		table.addParams("openid", openid);
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return getUserInfoByOpenID(openid);//重新获取
			}
		}
		return json;
	}
	//需要 code
	public JSONObject getUserInfo(String authCode, HttpSession session) {
		WeHAT hat = (WeHAT) session.getAttribute("hat");
		if(null == hat) {//需要获取HTML ACCESS TOKEN
			hat = getHAT(authCode);
			if(null == hat) {
				return null;
			}
			session.setAttribute("hat", hat);
		}
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.USER_INFO);
		table.addParams("access_token", hat.getAccess_token());
		table.addParams("openid", hat.getOpenid());
		table.addParams("lang", "zh_CN");
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				hat = refHAT(hat.getRefresh_token());//刷新HAT
				if(null == hat) {
					hat = getHAT(authCode);
				}
				session.setAttribute("hat", hat);
				return getUserInfo(authCode, session);//重新获取
			}
		}
		return json;
	}

	public WeHAT getHAT(String authCode) {// 获取html 的 access token
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.HTML_ACCESS_TOKEN);
		table.addParams("appid", CommandCollection.AppID);
		table.addParams("secret", CommandCollection.Appsecret);
		table.addParams("code", authCode);
		table.addParams("grant_type", "authorization_code");
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return getClass(json.toString(), WeHAT.class);
		} else {
			logger.error("HAT error : " + json.getString("errcode"));
			return null;
		}
	}

	public WeHAT refHAT(String refresh_token) {// 刷新授权码
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.REF_ACCESS_TOKEN);
		table.addParams("appid", CommandCollection.AppID);
		table.addParams("grant_type", "refresh_token");
		table.addParams("refresh_token", refresh_token);
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return getClass(json.toString(), WeHAT.class);
		} else {
			logger.error("ref HAT error : " + json.getString("errcode"));
			return null;
		}
	}
	/**
	 * 
	 */
	public JSONObject pushMessageByOpenID(String media_id) {
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.SEND_ALL_OID + CommandCollection.AccessToken);
		MessageByID mbid = CommandCollection.GetMessageByID(media_id);
		String mbidStr = JSONObject.fromObject(mbid).toString();
		logger.error(mbidStr);
		table.setJson(mbidStr);
		table.setUseJson(true);
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return pushMessageByOpenID(media_id);
			}
		}
		return json;
	}
	/**
	 * 以下为素材
	 */
	public boolean upload(String path, List<MultipartFile> files) {// 上传文件
		int len = files.size();
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				upload(path, files.get(i));
			}
			return true;
		} else {
			logger.error("没有可上传的数据");
			return false;
		}
	}

	public boolean upload(String path, MultipartFile file) {
		try {
			String filePath = path + file.getOriginalFilename();
			// 转存文件
			file.transferTo(new File(filePath));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean unload(String path, String newsImage) {// 删除文件
		File file = new File(path + newsImage);
		if (file.exists() && file.isFile()) {
			return file.delete();
		}
		return false;
	}

	public JSONObject addNews(AdNews adNew) {
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.MATE_NEWS + CommandCollection.AccessToken);
		MateNews news = CommandCollection.GetMateNews(adNew);
		String newStr = JSONObject.fromObject(news).toString();
		table.setUseJson(true);
		table.setJson(newStr);
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return addNews(adNew);
			}
		}
		return json;
	}

	public JSONObject addVideo(String filePath, String type, String title, String introduction) {// 视频
		checkAccessToken();
		String vedioStr = JSONObject.fromObject(CommandCollection.GetMateVideo(title, introduction)).toString();
		JSONObject json = addIVV(filePath, type, vedioStr);
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return addVideo(filePath, type, title, introduction);
			}
		}
		return json;
	}
	
	public JSONObject addTemp(String filePath, String type) {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			logger.error("上传的文件不存在:" + filePath);
			return null;
		}
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.MATE_TEMP + CommandCollection.AccessToken + "&type=" + type);
		SAHTML html = SAURLConnection.getInstance().PostRequest(table, file, false);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return addIV(filePath, type);
			}
		}
		return json;
	}

	public JSONObject addIV(String filePath, String type) {// 图形和语音
		checkAccessToken();
		JSONObject json = addIVV(filePath, type, null);
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return addIV(filePath, type);
			}
		}
		return json;
	}

	private JSONObject addIVV(String filePath, String type, String videoStr) {// IVV
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.MATE_IVV + CommandCollection.AccessToken + "&type=" + type);
		if (null != videoStr) {
			table.setJson(videoStr);
			table.setUseJson(true);
		}
		return addNIVV(filePath, table, type);
	}

	public JSONObject addNewsImage(String filePath) {// 添加图文内部图片资源
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.MATE_NEWS_IMAGE + CommandCollection.AccessToken);
		JSONObject json = addNIVV(filePath, table, CommandCollection.MESSAGE_NEWS_IMAGE);
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return addNIVV(filePath, table, CommandCollection.MESSAGE_NEWS_IMAGE);
			}
		}
		return json;
	}

	private JSONObject addNIVV(String filePath, SAHttpTable table, String type) {// 添加图文内部图片资源
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			logger.error("上传的文件不存在:" + filePath);
			return null;
		}
		checkAccessToken();
		boolean isVideo = type.equals(CommandCollection.MESSAGE_VIDEO);
		SAHTML html = SAURLConnection.getInstance().PostRequest(table, file, isVideo);
		JSONObject json = JSONObject.fromObject(html.getBody());
		return json;
	}

	public JSONObject deleteNIVV(String media_id) {// 添加图文内部图片资源
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.MATE_DELETE + CommandCollection.AccessToken);
		String deleteStr = JSONObject.fromObject(CommandCollection.GetMateDelete(media_id)).toString();
		table.setJson(deleteStr);
		table.setUseJson(true);
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
				return deleteNIVV(media_id);
			}
		}
		return json;
	}
}
