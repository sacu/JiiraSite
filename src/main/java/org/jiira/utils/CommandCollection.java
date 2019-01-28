package org.jiira.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jiira.pojo.ad.AdNews;
import org.jiira.pojo.ad.AdNewsType;
import org.jiira.pojo.we.WeClearQuota;
import org.jiira.pojo.we.cmenu.CMenu;
import org.jiira.pojo.we.cmenu.CMenuButton;
import org.jiira.pojo.we.cmenu.CMenuClickButton;
import org.jiira.pojo.we.cmenu.CMenuViewButton;
import org.jiira.pojo.we.mate.MateDelete;
import org.jiira.pojo.we.mate.ivv.MateVideo;
import org.jiira.pojo.we.mate.news.MateNews;
import org.jiira.pojo.we.mate.news.MateNewsElement;
import org.jiira.pojo.we.push.MessageByID;
import org.jiira.pojo.we.push.MessageMediaID;
import org.jiira.we.url.SAHttpTable;

import net.sf.json.JSONArray;

public class CommandCollection {
	public static final String Token = "sacu";
	//商户号
	public static final String MCHID = "1523670331";
	// 正式
	public static final String AppID = "wxbe33419389062baf";
	public static final String Appsecret = "9825b2ee097c860161004a6f12bc38a7";
	// 测试（主要测试授权和消息推送）
	// public static final String AppID = "wxeb5a9c34e7d8e1d4";
	// public static final String Appsecret = "3916d9c38526662c982212051a73de9d";

	public static final String AI_AppID = "2110982891";
	public static final String AI_AppKey = "cWIPxtEqyc8mMQSW";

	public static boolean JSSDK_INIT = false;
	public static final String WePayKey = "tengxunshabilajiwangzhancaonima2";
	public static String AccessToken;
	public static String JSSDKTicket;

	public static int ALL_TYPE = 1;
	public static int BOOK_TYPE = 2;
	public static int STRATEGY_TYPE = 3;//攻略
	
	public static int FAULT_INT = -1;//失效的
	public static int ENABLE = 1;// 可用
	public static int DISABLE = 0;// 禁用
	public static int LEVEL_NONE = 0;// 无限制
	public static int LEVEL_LOWER = 1;// 较低的
	public static int LEVEL_MEDIUM = 2;// 中等
	public static int LEVEL_HIGHER = 3;// 较高的
	private static List<AdNewsType> NEWS_TYPE_LIST;
	private static JSONArray NEWS_TYPE_JSON;

	public static int AUTO_PAY = 1;//自动支付
	// 运势
	public static final String EveryDay = "https://www.meiguoshenpo.com/yunshi/";
	public static final String ED_Aries = "baiyang/";// 白羊
	public static final String ED_Taurus = "jinniu/";// 金牛
	public static final String ED_Gemini = "shuangzi/";// 双子
	public static final String ED_Cancer = "juxie/";// 巨蟹
	public static final String ED_Leo = "shizi/";// 狮子
	public static final String ED_Virgo = "chunv/";// 处女
	public static final String ED_Libra = "tiancheng/";// 天秤
	public static final String ED_Scorpio = "tianxie/";// 天蝎
	public static final String ED_Sagittarius = "sheshou/";// 射手
	public static final String ED_Capricorn = "mojie/";// 摩羯
	public static final String ED_Aquarius = "shuiping/";// 水瓶
	public static final String ED_Pisces = "shuangyu/";// 双鱼
	public static int Birthday_Len = 8;
	public static String Birthday_Info = "\r\n请输入[sr|19990102]设置您的生日,\r\n不需要输入[],只需要输入[]内的:字母+竖线+年月日即可。\r\n其中1999为您的生日年份,01为您生日月份,02为您生日当时月的天数。";
	//
	public static final String HOST_NAME = "wx.jiira.com";// 以后换成域名 188.131.228.192
	public static final String WEB_NAME = "http://" + HOST_NAME + "/";// 以后换成域名
	public static final String RES_NAME = WEB_NAME + "manager/resource/";
	/// ##################################################################
	// 各种消息类型,除了扫带二维码事件
	public static final String MESSAGE_TEXT = "text";// 文本消息
	public static final String MESSAGE_IMAGE = "image";// 图片消息
	public static final String MESSAGE_NEWS = "news";// 图文消息
	public static final String MESSAGE_NEWS_IMAGE = "news_image";// 图文内图片
	public static final String MESSAGE_VOICE = "voice";// 语音
	public static final String MESSAGE_MUSIC = "music";// 音乐
	public static final String MESSAGE_VIDEO = "video";// 视频消息
	public static final String MESSAGE_THUMB = "thumb";// 缩略图
	public static final String MESSAGE_SHORTVIDEO = "shortvideo";// 小视频消息
	public static final String MESSAGE_LOCATION = "location";// 地理位置消息
	public static final String MESSAGE_LINK = "link";// 链接消息
	public static final String MESSAGE_EVENT = "event";// 事件推送消息
	public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";// 事件推送消息中,事件类型，subscribe(订阅)
	public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";// 事件推送消息中,事件类型，unsubscribe(取消订阅)
	public static final String MESSAGE_EVENT_LOCATION_UP = "LOCATION";// 事件推送消息中,上报地理位置事件
	public static final String MESSAGE_EVENT_CLICK = "CLICK";// 事件推送消息中,自定义菜单事件,点击菜单拉取消息时的事件推送
	public static final String MESSAGE_EVENT_VIEW = "VIEW";// 事件推送消息中,自定义菜单事件,点击菜单跳转链接时的事件推送
	
	public static final String TEMP = "temp";//临时
	/// ###################### 本地或服务器本地路径
	/// ############################################
	// 测试路径
//	 public static final String ACCESS_TOKEN_PATH = "d:\\accesstoken.sa";// ACCESS
//	 public static final String JSSDK_TICKET_PATH = "d:\\jsapiticket.sa";// ACCESS
	/// TOKEN路径
	// public static final String NEWS_PATH = "F:\\\\upload\\\\";//图文路径，可能用不到
	// public static final String NEWS_IMAGE_PATH = "F:\\upload\\";//图文内图形路径
	// public static final String IMAGE_PATH = "F:\\upload\\";//图形路径
	// public static final String VOICE_PATH = "F:\\upload\\";//语音路径
	// public static final String VIDEO_PATH = "F:\\upload\\";//视频路径
	// public static final String THUMB_PATH = "F:\\upload\\";//缩略图路径
	// 服务器路径
	public static final String BASE_PATH = "/usr/share/tomcat/webapps/manager/resource/";
	public static final String ACCESS_TOKEN_PATH = BASE_PATH + "accesstoken.sa";// ACCESS TOKEN路径
	public static final String JSSDK_TICKET_PATH = BASE_PATH + "jsapiticket.sa";// jsapi ticket路径
	public static final String NEWS_PATH = BASE_PATH + MESSAGE_NEWS + "/";// 图文路径，可能用不到
	public static final String NEWS_IMAGE_PATH = BASE_PATH + MESSAGE_NEWS_IMAGE + "/";// 图文内图形路径
	public static final String IMAGE_PATH = BASE_PATH + MESSAGE_IMAGE + "/";// 图形路径
	public static final String VOICE_PATH = BASE_PATH + MESSAGE_VOICE + "/";// 语音路径
	public static final String VIDEO_PATH = BASE_PATH + MESSAGE_VIDEO + "/";// 视频路径
	public static final String THUMB_PATH = BASE_PATH + MESSAGE_THUMB + "/";// 缩略图路径
	public static final String TEMP_PATH = BASE_PATH + TEMP + "/";// 临时目录
	

	// menu
	public static final String MENU_RECENT = "recent";
	public static final String MENU_DIVINATION = "divination";

	// 各种请求地址
	public static final String CLEAR_QUOTA = "https://api.weixin.qq.com/cgi-bin/clear_quota?access_token=";// 清除访问次数限制，一个月只能用10次
	public static final String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";// 创建菜单
	public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";// 生成 ACCESS TOKEN
	public static final String JSSDK_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";

	public static final String AUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize";// 获取授权码 code
	public static final String HTML_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";// 获取HTML ACCESS
																										// TOKEN
	public static final String REF_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";// 更新授权码
	public static final String MATE_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";// 上传图文消息
	public static final String MATE_NEWS_IMAGE = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";// 上传图文消息内部图形资源
	public static final String MATE_IVV = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=";// 上传IVV资源
	public static final String MATE_DELETE = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=";// 删除NIVV

	public static final String MATE_TEMP = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=";//临时素材
	
	public static final String USER_INFO = "https://api.weixin.qq.com/sns/userinfo";// 获取用户信息
	public static final String USER_INFO_OID = "https://api.weixin.qq.com/cgi-bin/user/info";// open id 获取用户信息
	public static final String SEND_ALL_OID = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";// openid群发

	//ai
	public static final String AI_Text_Say = "https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";//文字回答文字
	public static final String AI_Image_Say = "https://api.ai.qq.com/fcgi-bin/vision/vision_imgtotext";//图片回答文字
	public static final String AI_Voice_Say = "https://api.ai.qq.com/fcgi-bin/aai/aai_tts";//文字转语音
	public static final String AI_YTVoice_Say = "https://api.ai.qq.com/fcgi-bin/aai/aai_tta";//优图文字转语音
	//支付
	public static final String WE_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder";//支付
	public static final String CALL_PAY = "http://wx.jiira.com/we/callpay";//支付回调地址
	public static final String SUCCESS = "SUCCESS";
	
	////////////// 真TM坑到家了！！！！触发类型！！！必须小写！！！！！不然他竟会报个{"errcode":65318,"errmsg":"must
	////////////// use utf-8 charset hint: [bKu2cA00243125]"}
	////////////// 这TM跟UTF8有关系吗？？？？
	public static final String WE_CLICK = "click";//
	public static final String WE_VIEW = "view";//
	// 微信授权跳转规范
	public static final String WE_REDIRECT = WEB_NAME + "we/redirect?redirect=";// 重定向地址前缀
	public static final String WE_USER = "user";// 用户信息
	public static final String WE_RECHARGE = "recharge";// 充值
	public static final String WE_NEWS = "news";// 浏览图文 从拉取信息 或 推送信息内 进入
	public static final String WE_INDEX = "index";// 首页
	public static final String WE_LIST = "list";// 列表(往期回顾)
	public static final String WE_SEARCH = "search";// 搜索

	public static CMenu GetMenu() {
		CMenu menu = new CMenu();
		// 倒叙创建
		// 创建二级菜单2
		CMenuClickButton m221 = new CMenuClickButton();// 点击拉取消息 会使用到 WE_NEWS
		m221.setName("最新动态");
		m221.setType(WE_CLICK);
		m221.setKey(MENU_RECENT);
		CMenuViewButton m222 = new CMenuViewButton();// 打开网页
		m222.setName("往期回顾");
		m222.setType(WE_VIEW);
		m222.setUrl(WE_REDIRECT + WE_LIST);
		CMenuViewButton m223 = new CMenuViewButton();
		m223.setName("搜索...");
		m223.setType(WE_VIEW);
		m223.setUrl(WE_REDIRECT + WE_SEARCH);
		// 创建二级菜单3
		CMenuClickButton m231 = new CMenuClickButton();// 点击消息返回
		m231.setName("每日一签");
		m231.setType(WE_CLICK);
		m231.setKey(MENU_DIVINATION);
		CMenuViewButton m232 = new CMenuViewButton();// 跳转到账户网页
		m232.setName("账户");
		m232.setType(WE_VIEW);
		m232.setUrl(WE_REDIRECT + WE_USER);
		CMenuViewButton m233 = new CMenuViewButton();// 跳转到充值网页
		m233.setName("充值");
		m233.setType(WE_VIEW);
		m233.setUrl(WE_REDIRECT + WE_RECHARGE);
		// 创建一级
		CMenuViewButton m11 = new CMenuViewButton();// 跳转到首页
		m11.setName("首页");
		m11.setType(WE_VIEW);
		m11.setUrl(WE_REDIRECT + WE_INDEX);
		CMenuButton m12 = new CMenuButton();
		m12.setName("大事件");
		m12.setSub_button(new CMenuButton[] { m221, m222, m223 });
		CMenuButton m13 = new CMenuButton();
		m13.setName("我的");
		m13.setSub_button(new CMenuButton[] { m231, m232, m233 });

		menu.setButton(new CMenuButton[] { m11, m12, m13 });
		return menu;
	}

	public static MateNews GetMateNews(AdNews adNew) {
		MateNewsElement element = new MateNewsElement();
		element.setTitle(adNew.getTitle());
		element.setThumb_media_id(adNew.getThumb_media_id());
		element.setAuthor(adNew.getAuthor());
		element.setDigest(adNew.getDigest());
		element.setShow_cover_pic(adNew.getShow_cover_pic());
		element.setContent(adNew.getContent());
		element.setContent_source_url(GetNewsURL(adNew.getId()));
		element.setNeed_open_comment(adNew.getNeed_open_comment());
		element.setOnly_fans_can_comment(adNew.getOnly_fans_can_comment());
		MateNews news = new MateNews();
		news.setArticles(new MateNewsElement[] { element });
		return news;
	}

	public static String GetNewsURL(int id) {
		return WE_REDIRECT + WE_NEWS + "*news_id=" + id;
	}

	public static MessageByID GetMessageByID(String media_id) {
		MessageByID mbid = new MessageByID();
		List<String> touser = new ArrayList<>();
		for (String key : OPENID.keySet()) {
			touser.add(key);
		}
		mbid.setTouser(touser);
		mbid.setMpnews(new MessageMediaID(media_id));
		mbid.setMsgtype("mpnews");
		mbid.setSend_ignore_reprint(1);// 遇到转发文章继续发送 0为停止发送
		return mbid;
	}

	public static MateVideo GetMateVideo(String title, String introduction) {
		MateVideo video = new MateVideo();// 因为比较少
		video.setTitle(title);
		video.setIntroduction(introduction);
		return video;
	}

	public static MateDelete GetMateDelete(String media_id) {
		MateDelete delete = new MateDelete();// 因为比较少
		delete.setMedia_id(media_id);
		return delete;
	}

	public static WeClearQuota GetWeClearQuota() {
		WeClearQuota quota = new WeClearQuota();
		quota.setAppid(AppID);
		return quota;
	}

	public static SAHttpTable GetHttpTable(String method) {
		SAHttpTable table = new SAHttpTable();
		table.setMethod(method);
		table.addPropertys("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		table.addPropertys("Content-Type", "application/x-www-form-urlencoded");
		return table;
	}

	public static String GetLocalPath(String type) {
		if (type.equals(MESSAGE_IMAGE)) {
			return IMAGE_PATH;
		} else if (type.equals(MESSAGE_VOICE)) {
			return VOICE_PATH;
		} else if (type.equals(MESSAGE_THUMB)) {
			return THUMB_PATH;
		} else if (type.equals(MESSAGE_VIDEO)) {
			return VIDEO_PATH;
		} else if(type.equals(TEMP)) {
			return TEMP_PATH;
		} else {
			return "";
		}
	}

	/**
	 * 图文类型表
	 */
	public static void SetNewsTypeList(List<AdNewsType> list) {
		NEWS_TYPE_LIST = list;
		NEWS_TYPE_JSON = JSONArray.fromObject(NEWS_TYPE_LIST);
	}

	public static List<AdNewsType> GetNewsTypeList() {
		return NEWS_TYPE_LIST;
	}

	public static JSONArray GetNewsTypeJson() {
		return NEWS_TYPE_JSON;
	}

	/**
	 * openid缓存 主要用于推送
	 */
	private static Map<String, Integer> OPENID = new HashMap<>();

	public static boolean ContainsOpenID(String openid) {
		return OPENID.containsKey(openid);
	}

	public static void PutOpenID(String openid) {
		if (!OPENID.containsKey(openid)) {// spring会加载两次，所以加个判断！
			OPENID.put(openid, 0);
		}
	}

	public static void RemoveOpenID(String openid) {
		OPENID.remove(openid);
	}

	public static String getConstellation(int month, int day) {
		if ((month == 1 && day >= 20) || (month == 2 && day <= 18)) {
			return ED_Aquarius;
		} else if ((month == 2 && day >= 19) || (month == 3 && day <= 20)) {
			return ED_Pisces;
		} else if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
			return ED_Aries;
		} else if ((month == 4 && day >= 20) || (month == 5 && day <= 20)) {
			return ED_Taurus;
		} else if ((month == 5 && day >= 21) || (month == 6 && day <= 21)) {
			return ED_Gemini;
		} else if ((month == 6 && day >= 22) || (month == 7 && day <= 22)) {
			return ED_Cancer;
		} else if ((month == 7 && day >= 23) || (month == 8 && day <= 22)) {
			return ED_Leo;
		} else if ((month == 8 && day >= 23) || (month == 9 && day <= 22)) {
			return ED_Virgo;
		} else if ((month == 9 && day >= 23) || (month == 10 && day <= 23)) {
			return ED_Libra;
		} else if ((month == 10 && day >= 24) || (month == 11 && day <= 22)) {
			return ED_Scorpio;
		} else if ((month == 11 && day >= 23) || (month == 12 && day <= 21)) {
			return ED_Sagittarius;
		} else {// if ((month == 12 && day >= 22) || (month == 1 && day <= 19)) {
			return ED_Capricorn;
		}
	}
	
	public static String getIPByRequest(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_CLIENT_IP");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
		}  
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getRemoteAddr();  
		}
		return ip;
	}
}
