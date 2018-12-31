package org.jiira.we.process;

import org.jiira.utils.CommandCollection;
import org.jiira.we.WeChatMessage;

public class HandleEvent {
	private static HandleEvent instance;
	public static HandleEvent getInstance() {
		if(null == instance) {
			instance = new HandleEvent();
		}
		return instance;
	}
	private HandleEvent() {}
	
	public void process(WeChatMessage msg){
		switch(msg.getEvent()) {
		/**
		 * 事件推送消息中,事件类型，subscribe(订阅)
		 */
		case CommandCollection.MESSAGE_EVENT_SUBSCRIBE: {//订阅消息
			msg.setContent("/:heart/:heart/:heart/:heart/:heart\n"
					+ "您好，很高兴为您服务！\n无聊了可以找我聊天~我可是随时待命呦~\n"
					+ "/:heart/:heart/:heart/:heart/:heart");
			break;
		}
		/**
		 * 事件推送消息中,事件类型，unsubscribe(取消订阅)
		 */
		case CommandCollection.MESSAGE_EVENT_UNSUBSCRIBE: {//取消订阅
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
			case CommandCollection.MENU_SEARCH://搜索
				msg.setContent("暂时还没有什么信息可搜取呦……");
				break;
			case CommandCollection.MENU_DIVINATION://每日一签
				msg.setContent("心情也算不错了吧!\r\n虽然有时会遇到失败,\r\n但切勿放弃啊!");
				break;
				default:
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
}
