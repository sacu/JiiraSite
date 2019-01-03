package org.jiira.utils;

import org.jiira.pojo.we.WeClearQuota;
import org.jiira.pojo.we.authorization.WeHAT;
import org.jiira.pojo.we.cmenu.CMenu;
import org.jiira.pojo.we.cmenu.CMenuButton;
import org.jiira.pojo.we.cmenu.CMenuClickButton;
import org.jiira.pojo.we.cmenu.CMenuViewButton;
import org.jiira.pojo.we.mate.MateDelete;
import org.jiira.pojo.we.mate.ivv.MateVideo;
import org.jiira.pojo.we.mate.news.MateNews;
import org.jiira.pojo.we.mate.news.MateNewsElement;
import org.jiira.we.SAHttpTable;

public class CommandCollection {
	public static final String Token = "sacu";
	//正式
	 public static final String AppID = "wxbe33419389062baf";
	 public static final String Appsecret = "9825b2ee097c860161004a6f12bc38a7";
	// 测试
//	public static final String AppID = "wxeb5a9c34e7d8e1d4";
//	public static final String Appsecret = "3916d9c38526662c982212051a73de9d";

	public static final String AI_AppID = "2110982891";
	public static final String AI_AppKey = "cWIPxtEqyc8mMQSW";

	public static String AccessToken;

	public static String HCODE;// 网页获取密钥的代码
	public static WeHAT HAT;// 网页密钥
///###################### 本地或服务器本地路径 ############################################
	//测试路径
//	public static final String ACCESS_TOKEN_PATH = "d:\\accesstoken.sa";// ACCESS TOKEN路径
//	public static final String NEWS_PATH = "F:\\\\upload\\\\";//图文路径，可能用不到
//	public static final String NEWS_IMAGE_PATH = "F:\\upload\\";//图文内图形路径
//	public static final String IMAGE_PATH = "F:\\upload\\";//图文内图形路径
//	public static final String VOICE_PATH = "F:\\upload\\";//图文内图形路径
//	public static final String VIDEO_PATH = "F:\\upload\\";//图文内图形路径
//	public static final String THUMB_PATH = "F:\\upload\\";//图文内图形路径
	//服务器路径
	public static final String ACCESS_TOKEN_PATH = "/home/resource/accesstoken.sa";// ACCESS TOKEN路径
	public static final String NEWS_PATH = "/home/resource/news/";//图文路径，可能用不到
	public static final String NEWS_IMAGE_PATH = "/home/resource/news_image/";//图文内图形路径
	public static final String IMAGE_PATH = "/home/resource/image/";//图形路径
	public static final String VOICE_PATH = "/home/resource/voice/";//语音路径
	public static final String VIDEO_PATH = "/home/resource/video/";//视频路径
	public static final String THUMB_PATH = "/home/resource/thumb/";//缩略图路径
	
	
	
///##################################################################
	// 各种消息类型,除了扫带二维码事件
	public static final String MESSAGE_TEXT = "text";// 文本消息
	public static final String MESSAGE_IMAGE = "image";// 图片消息
	public static final String MESSAGE_NEWS = "news";// 图文消息
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
	// menu
	public static final String MENU_SEARCH = "search";
	public static final String MENU_DIVINATION = "divination";

	//
	public static final String HOST_NAME = "188.131.228.192";// 以后换成域名
	// 各种请求地址
	public static final String CLEAR_QUOTA = "https://api.weixin.qq.com/cgi-bin/clear_quota?access_token=";//清除访问次数限制，一个月只能用10次
	public static final String CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";// 创建菜单
	public static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";// 生成 ACCESS TOKEN
	
	public static final String AUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize";// 获取授权码
	public static final String HTML_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";// 获取HTML ACCESS
																										// TOKEN
	public static final String REF_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";// 更新授权码
	public static final String MATE_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";// 上传图文消息
	public static final String MATE_NEWS_IMAGE = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";//上传图文消息内部图形资源
	public static final String MATE_IVV = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=";//上传IVV资源
	public static final String MATE_DELETE = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=";//删除NIVV
	
	// 页面
	public static final String WELCOME = "welcome";
	public static final String WELCOME_J = "welcome.jsp";
	public static final String SETTING = "setting";
	public static final String SETTING_J = "setting.jsp";
	public static final String ABOUT = "about";
	public static final String ABOUT_J = "about.jsp";

	public static CMenu GetMenu() {
		CMenu menu = new CMenu();
		// 倒叙创建
		// 创建二级菜单
		CMenuViewButton m221 = new CMenuViewButton();
		m221.setName("最新动态");
		m221.setType("view");
		m221.setUrl("http://www.soso.com");
		CMenuViewButton m222 = new CMenuViewButton();
		m222.setName("往期回顾");
		m222.setType("view");
		m222.setUrl("http://www.qq.com");
		CMenuClickButton m223 = new CMenuClickButton();
		m223.setName("搜索...");
		m223.setType("click");
		m223.setKey(MENU_SEARCH);
		// 创建一级
		CMenuClickButton m11 = new CMenuClickButton();
		m11.setName("每日一签");
		m11.setType("click");
		m11.setKey(MENU_DIVINATION);
		CMenuButton m12 = new CMenuButton();
		m12.setName("大事件");
		m12.setSub_button(new CMenuButton[] { m221, m222, m223 });
		CMenuViewButton m13 = new CMenuViewButton();
		m13.setName("我");
		m13.setType("view");
		m13.setUrl("http://" + HOST_NAME + "/we/userInfo");
		menu.setButton(new CMenuButton[] { m11, m12, m13 });
		return menu;
	}

	public static MateNews GetMateNews(String title, String thumb_media_id, String author, String digest,
			int show_cover_pic, String content, String content_source_url, int need_open_comment,
			int only_fans_can_comment) {
		MateNewsElement element = new MateNewsElement();
		element.setTitle(title);
		element.setThumb_media_id(thumb_media_id);
		element.setAuthor(author);
		element.setDigest(digest);
		element.setShow_cover_pic(show_cover_pic);
		element.setContent(content);
		element.setContent_source_url(content_source_url);
		element.setNeed_open_comment(need_open_comment);
		element.setOnly_fans_can_comment(only_fans_can_comment);
		MateNews news = new MateNews();
		news.setArticles(new MateNewsElement[] {element});
		return news;
	}
	public static MateVideo GetMateVideo(String title, String introduction) {
		MateVideo video = new MateVideo();//因为比较少
		video.setTitle(title);
		video.setIntroduction(introduction);
		return video;
	}
	public static MateDelete GetMateDelete(String media_id) {
		MateDelete delete = new MateDelete();//因为比较少
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
		table.setURL("https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat");
		table.addPropertys("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		table.addPropertys("Content-Type", "application/x-www-form-urlencoded");
		return table;
	}
	
	public static String GetLocalPath(String type) {
		if (type.equals(CommandCollection.MESSAGE_IMAGE)) {
			return CommandCollection.IMAGE_PATH;
		} else if (type.equals(CommandCollection.MESSAGE_VOICE)) {
			return CommandCollection.VOICE_PATH;
		} else if (type.equals(CommandCollection.MESSAGE_THUMB)) {
			return CommandCollection.THUMB_PATH;
		} else if(type.equals(CommandCollection.MESSAGE_VIDEO)){
			return CommandCollection.VIDEO_PATH;
		} else {
			return "";
		}
	}
}
