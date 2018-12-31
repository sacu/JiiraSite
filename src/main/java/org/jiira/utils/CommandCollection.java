package org.jiira.utils;

import org.jiira.pojo.we.authorization.WeHAT;
import org.jiira.pojo.we.cmenu.CMenu;
import org.jiira.pojo.we.cmenu.CMenuButton;
import org.jiira.pojo.we.cmenu.CMenuClickButton;
import org.jiira.pojo.we.cmenu.CMenuViewButton;
import org.jiira.we.SAHttpTable;

public class CommandCollection {
	public static final String Token = "sacu";
//	public static final String AppID = "wxbe33419389062baf";
//	public static final String Appsecret = "0e0096f240db6cabe012295de3ba1dbc";
	//测试
	public static final String AppID = "wxeb5a9c34e7d8e1d4";
	public static final String Appsecret = "3916d9c38526662c982212051a73de9d";

	public static final String AI_AppID = "2110982891";
	public static final String AI_AppKey = "cWIPxtEqyc8mMQSW";
	
	
	public static String AccessToken;

	public static String HCODE;//网页获取密钥的代码
	public static WeHAT HAT;//网页密钥
	// 各种消息类型,除了扫带二维码事件
	public static final String MESSAGE_TEXT = "text";// 文本消息
	public static final String MESSAtGE_IMAGE = "image";// 图片消息
	public static final String MESSAGE_NEWS = "news";// 图文消息
	public static final String MESSAGE_VOICE = "voice";// 语音
	public static final String MESSAGE_MUSIC = "music";// 音乐
	public static final String MESSAGE_VIDEO = "video";// 视频消息
	public static final String MESSAGE_SHORTVIDEO = "shortvideo";// 小视频消息
	public static final String MESSAGE_LOCATION = "location";// 地理位置消息
	public static final String MESSAGE_LINK = "link";// 链接消息
	public static final String MESSAGE_EVENT = "event";// 事件推送消息
	public static final String MESSAGE_EVENT_SUBSCRIBE = "subscribe";// 事件推送消息中,事件类型，subscribe(订阅)
	public static final String MESSAGE_EVENT_UNSUBSCRIBE = "unsubscribe";// 事件推送消息中,事件类型，unsubscribe(取消订阅)
	public static final String MESSAGE_EVENT_LOCATION_UP = "LOCATION";// 事件推送消息中,上报地理位置事件
	public static final String MESSAGE_EVENT_CLICK = "CLICK";// 事件推送消息中,自定义菜单事件,点击菜单拉取消息时的事件推送
	public static final String MESSAGE_EVENT_VIEW = "VIEW";// 事件推送消息中,自定义菜单事件,点击菜单跳转链接时的事件推送

	//menu
	public static final String MENU_SEARCH = "search";
	public static final String MENU_DIVINATION = "divination";
	
	//
	public static final String HOST_NAME = "188.131.228.192";//以后换成域名
	
	public static CMenu GetMenu() {
		CMenu menu = new CMenu();
		//倒叙创建
		//创建二级菜单
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
		//创建一级
		CMenuClickButton m11 = new CMenuClickButton();
		m11.setName("每日一签");
		m11.setType("click");
		m11.setKey(MENU_DIVINATION);
		CMenuButton m12 = new CMenuButton();
		m12.setName("大事件");
		m12.setSub_button(new CMenuButton[] {m221, m222, m223});
		CMenuViewButton m13 = new CMenuViewButton();
		m13.setName("我");
		m13.setType("view");
		m13.setUrl("http://" + HOST_NAME + "/we/userInfo");
		menu.setButton(new CMenuButton[] {m11, m12, m13});
		return menu;
	}
	
	public static SAHttpTable GetHttpTable(String method) {
		SAHttpTable table = new SAHttpTable();
		table.setMethod(method);
		table.setURL("https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat");
		table.addPropertys("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		table.addPropertys("Content-Type", "application/x-www-form-urlencoded");
		return table;
	}
}
