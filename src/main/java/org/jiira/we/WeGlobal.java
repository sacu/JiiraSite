package org.jiira.we;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jiira.pojo.ad.AdNews;
import org.jiira.pojo.we.authorization.WeHAT;
import org.jiira.pojo.we.cmenu.CMenu;
import org.jiira.pojo.we.mate.news.MateNews;
import org.jiira.pojo.we.robot.WeRobot;
import org.jiira.utils.CommandCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	/**
	 * 获取机器人对话
	 * 
	 * @param message
	 * @return
	 */
	public WeRobot getRobot(String message) {
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.addParams("app_id", CommandCollection.AI_AppID);
		table.addParams("nonce_str", String.valueOf(Math.round(Math.random() * 32768)));
		table.addParams("question", message);
		table.addParams("session", "123456");
		table.addParams("time_stamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
		ArrayList<SAHttpKVO> params = table.getParams();
		table.addParams("sign", DecriptUtil.ReqSign(params));
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		if (json.getInt("ret") == 0) {
			return getClass(html.getBody(), WeRobot.class);
		}
		return null;
	}

	public JSONObject createMenu() {
		checkAccessToken();
		SAHttpTable table = CommandCollection.GetHttpTable("POST");
		table.setURL(CommandCollection.CREATE_MENU + CommandCollection.AccessToken);
		CMenu menu = CommandCollection.GetMenu();
		String menuStr = JSONObject.fromObject(menu).toString();
		table.setUseJson(true);
		table.setJson(menuStr);
		SAHTML html = SAURLConnection.getInstance().PostRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		int code = WeCode.getInstance().check(json);
		if(code == 0) {
			return json;
		} else {
			if (code == 42001 || code == 40001) {
				createAccessToken();
			}
			return createMenu();
		}
	}

	public <T> T getClass(String body, Class<T> cls) {
		logger.error("body : " + body);
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
			}
			return clearQuota();
		}
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
			createAccessToken();// 读取accesstoken
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
	 * authorization
	 */
	public SAHTML getCode(String redirect) {
		String redirect_uri = "http://" + CommandCollection.HOST_NAME + "/" + redirect;
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.AUTH_CODE);
		table.addParams("appid", CommandCollection.AI_AppID);
		table.addParams("redirect_uri", redirect_uri);
		table.addParams("response_type", "code");
		table.addParams("scope", "snsapi_base");
		table.addParams("state", redirect + "#wechat_redirect");
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		return html;
	}

	public WeHAT getHAT(String code) {// 获取html 的 access token
		SAHttpTable table = CommandCollection.GetHttpTable("GET");
		table.setURL(CommandCollection.HTML_ACCESS_TOKEN);
		table.addParams("appid", CommandCollection.AppID);
		table.addParams("secret", CommandCollection.Appsecret);
		table.addParams("code", code);
		table.addParams("grant_type", "authorization_code");
		SAHTML html = SAURLConnection.getInstance().GetRequest(table);
		JSONObject json = JSONObject.fromObject(html.getBody());
		if (!json.has("errcode")) {
			CommandCollection.HAT = WeGlobal.getInstance().getClass(html.getBody(), WeHAT.class);
			return CommandCollection.HAT;
		} else {
			logger.error("HAT error : " + json.has("errcode"));
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
		if (!json.has("errcode")) {
			CommandCollection.HAT = WeGlobal.getInstance().getClass(html.getBody(), WeHAT.class);
			return CommandCollection.HAT;
		} else {
			logger.error("ref HAT error : " + json.has("errcode"));
			return null;
		}
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
			}
			return addNews(adNew);
		}
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
			}
			return addVideo(filePath, type, title, introduction);
		}
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
			}
			return addIV(filePath, type);
		}
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
			}
			return addNIVV(filePath, table, CommandCollection.MESSAGE_NEWS_IMAGE);
		}
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
			}
			return deleteNIVV(media_id);
		}
	}
}
