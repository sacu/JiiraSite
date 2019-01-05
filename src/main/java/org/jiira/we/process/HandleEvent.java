package org.jiira.we.process;

import java.util.List;

import org.jiira.config.Application;
import org.jiira.pojo.ad.AdIV;
import org.jiira.pojo.ad.AdNews;
import org.jiira.service.AdMateService;
import org.jiira.service.impl.AdIVServiceImpl;
import org.jiira.service.impl.AdNewsServiceImpl;
import org.jiira.utils.CommandCollection;
import org.jiira.we.message.WeChatMessage;
import org.jiira.we.message.WeChatNewsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
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
			msg.setContent("/:heart/:heart/:heart/:heart/:heart\n"
					+ "您好，很高兴为您服务！\n无聊了可以找我聊天~我可是随时待命呦~\n"
					+ "/:heart/:heart/:heart/:heart/:heart");
			break;
		}
		/**
		 * 事件推送消息中,事件类型，unsubscribe(取消订阅)
		 */
		case CommandCollection.MESSAGE_EVENT_UNSUBSCRIBE: {//取消订阅
			msg.setMsgType(CommandCollection.MESSAGE_TEXT);
			msg.setContent("期待您的下次关注！");
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
				AdMateService<AdIV> adIVService = Application.getInstance().getBean(AdIVServiceImpl.class);
				AdMateService<AdNews> adNewsService = Application.getInstance().getBean(AdNewsServiceImpl.class);
				List<AdNews> adNews = adNewsService.selectOderByDesc(3);
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
					article.setUrl(adNew.getContent_source_url());
					logger.error(adNew.getContent_source_url());
					articles[i] = article;
				}
				msg.setArticles(articles);
				break;
			}
			case CommandCollection.MENU_SEARCH://搜索
				msg.setMsgType(CommandCollection.MESSAGE_TEXT);
				msg.setContent("暂时还没有什么信息可搜取呦……");
				break;
			case CommandCollection.MENU_DIVINATION://每日一签
				msg.setMsgType(CommandCollection.MESSAGE_TEXT);
				msg.setContent("心情也算不错了吧!\r\n虽然有时会遇到失败,\r\n但切勿放弃啊!");
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
			msg.setUseRobot(true);
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
}
