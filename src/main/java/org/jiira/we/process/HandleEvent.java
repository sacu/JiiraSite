package org.jiira.we.process;

import java.util.List;

import org.jiira.config.Application;
import org.jiira.pojo.ad.AdIV;
import org.jiira.pojo.ad.AdNews;
import org.jiira.pojo.ad.WeUser;
import org.jiira.service.AdIVService;
import org.jiira.service.AdNewsService;
import org.jiira.service.WeUserService;
import org.jiira.service.impl.AdIVServiceImpl;
import org.jiira.service.impl.AdNewsServiceImpl;
import org.jiira.utils.CommandCollection;
import org.jiira.we.WeGlobal;
import org.jiira.we.message.WeChatMessage;
import org.jiira.we.message.WeChatNewsMessage;
import org.jiira.we.url.SAHttpTable;
import org.jiira.we.url.SAURLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class HandleEvent {
	private static final Logger logger = LoggerFactory.getLogger(HandleEvent.class);
	
	private static HandleEvent instance;
	public static HandleEvent getInstance() {
		if(null == instance) {
			instance = new HandleEvent();
		}
		return instance;
	}
	private HandleEvent() {}
	
	public void process(WeChatMessage msg) {
		switch(msg.getEvent()) {
		/**
		 * 事件推送消息中,事件类型，subscribe(订阅)
		 */
		case CommandCollection.MESSAGE_EVENT_SUBSCRIBE: {//订阅消息
			msg.setMsgType(CommandCollection.MESSAGE_TEXT);
			//这里要注意……调用函数进入到这里时！收发双方的身份已调换，所以现在要取收信方
			String openid = msg.getToUserName();
			String nickName = "";
			if(!CommandCollection.ContainsOpenID(openid)) {//判断缓存是否存在
				WeUserService weUserService = Application.getInstance().getBean(WeUserService.class);
				WeUser weUser = weUserService.selectWeUser(openid);
				if(null == weUser) {
					weUser = getUser(openid);
					if(null != weUser) {
						weUserService.insertWeUser(weUser);
					}
				}
				nickName = "[" + weUser.getNickname() + "]";
				CommandCollection.PutOpenID(openid);
			}
			msg.setContent("/:heart/:heart/:heart/:heart/:heart\n"
					+ nickName + " 您好，很高兴为您服务！\n无聊了可以找我聊天~\n我可是随时待命呦~\n"
					+ "/:heart/:heart/:heart/:heart/:heart");
			break;
		}
		/**
		 * 事件推送消息中,事件类型，unsubscribe(取消订阅)
		 */
		case CommandCollection.MESSAGE_EVENT_UNSUBSCRIBE: {//取消订阅
			msg.setMsgType(CommandCollection.MESSAGE_TEXT);
			msg.setContent("期待您的下次关注！");
			String openid = msg.getToUserName();
			if(CommandCollection.ContainsOpenID(openid)) {//判断缓存是否存在
				CommandCollection.RemoveOpenID(openid);
				WeUserService weUserService = Application.getInstance().getBean(WeUserService.class);
				weUserService.deleteWeUser(openid);
			}
			break;
		}
		/**
		 * 事件推送消息中,上报地理位置事件
		 */
		case CommandCollection.MESSAGE_EVENT_LOCATION_UP: {//发送地理位置
			break;
		}
		/**
		 * 事件推送消息中,自定义菜单事件,点击菜单拉取消息时的事件推送
		 */
		case CommandCollection.MESSAGE_EVENT_CLICK: {//拉取消息
			String key = msg.getEventKey();
			switch(key) {
			case CommandCollection.MENU_RECENT:{//拉取图文
				AdIVService adIVService = Application.getInstance().getBean(AdIVServiceImpl.class);
				AdNewsService adNewsService = Application.getInstance().getBean(AdNewsServiceImpl.class);
				//拉取最低级别的消息
				List<AdNews> adNews = adNewsService.selectOderByLevelAndDesc(CommandCollection.LEVEL_LOWER, 8);
				int len = adNews.size();
				msg.setMsgType(CommandCollection.MESSAGE_NEWS);
				msg.setArticleCount(len);
				WeChatNewsMessage[] articles = new WeChatNewsMessage[len];
				WeChatNewsMessage article;
				AdNews adNew;
				AdIV adiv;
				for(int i = 0; i < len; ++i) {
					article = new WeChatNewsMessage();
					adNew = adNews.get(i);
					article.setTitle(adNew.getTitle());
					article.setDescription(adNew.getDigest());
					adiv = adIVService.selectIVByMediaId(adNew.getThumb_media_id());
					article.setPicUrl(adiv.getUrl());
					article.setUrl(CommandCollection.GetNewsURL(adNew.getId()));
					articles[i] = article;
				}
				msg.setArticles(articles);
				break;
			}
			case CommandCollection.MENU_DIVINATION://每日一签
				msg.setMsgType(CommandCollection.MESSAGE_TEXT);
				//这里要注意……调用函数进入到这里时！收发双方的身份已调换，所以现在要取收信方
				String openid = msg.getToUserName();
				WeUserService weUserService = Application.getInstance().getBean(WeUserService.class);
				WeUser weUser = weUserService.selectWeUser(openid);
				String birthday = "";
				if(null == weUser) {
					weUser = getUser(openid);
					if(null != weUser) {
						weUserService.insertWeUser(weUser);
					}
				} else {
					birthday = weUser.getBirthday();
				}
				if(birthday.length() == CommandCollection.Birthday_Len) {
					int month = Integer.valueOf(birthday.substring(4, 6));
					int day = Integer.valueOf(birthday.substring(6, 8));
					SAHttpTable table = new SAHttpTable();
					table.setURL("https://www.meiguoshenpo.com/yunshi/" + CommandCollection.getConstellation(month, day));
					table.setMethod("GET");
					String info = SAURLConnection.getInstance().GetRequest(table).getBody();
					try {
						int begin = info.indexOf("\"zt\">");
						begin = info.indexOf("</span>", begin);
						info = info.substring(begin + 7, info.indexOf("</p>", begin));
						msg.setContent("[每日一签]\t" + info.trim() + "\r\n[纯属娱乐,切勿较真儿呦~]");
					} catch(Exception ex) {
						msg.setContent("抱歉,服务器出了点小问题,请您稍后再试~");
					}
				} else {
					msg.setContent("我还不知道您的生日呢！" + CommandCollection.Birthday_Info);
				}
				break;
			default:
				msg.setMsgType(CommandCollection.MESSAGE_TEXT);
				msg.setContent("诶？这是什么操作");
			}
			break;
		}
		/**
		 * 事件推送消息中,自定义菜单事件,点击菜单跳转链接时的事件推送
		 */
		case CommandCollection.MESSAGE_EVENT_VIEW: {//点击菜单跳转
			break;
		}
		default:{//交给机器人处理
			msg.setUseTextSay(true);
		}
		}
	}
	public String format(WeChatMessage msg, String xml) {
		switch(msg.getEvent()) {
		case CommandCollection.MESSAGE_EVENT_CLICK:{
			switch(msg.getEventKey()) {
			case CommandCollection.MENU_RECENT:{
				return xml.replaceAll("org.jiira.we.message.WeChatNewsMessage", "item");
			}
			}
			break;
		}
		}
		return xml;
	}
	
	private WeUser getUser(String openid) {
		JSONObject json = WeGlobal.getInstance().getUserInfoByOpenID(openid);
		WeUser weUser = null;
		if(json.getInt("subscribe") != 0) {//获取成功,
			weUser = new WeUser();
			weUser.setOpenid(json.getString("openid"));
			weUser.setNickname(json.getString("nickname"));
			weUser.setSex(json.getInt("sex"));
			weUser.setVouchers(0);
			weUser.setLanguage(json.getString("language"));
			weUser.setCountry(json.getString("country"));
			weUser.setProvince(json.getString("province"));
			weUser.setCity(json.getString("city"));
			weUser.setHeadimgurl(json.getString("headimgurl"));
		}
		return weUser;
	}
}
