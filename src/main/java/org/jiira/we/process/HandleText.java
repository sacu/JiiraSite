package org.jiira.we.process;

import java.util.Date;

import org.jiira.pojo.we.article.WeArticle;
import org.jiira.pojo.we.article.WeArticleItem;
import org.jiira.pojo.we.ivv.WeIVV;
import org.jiira.pojo.we.ivv.WeIVVItem;
import org.jiira.utils.CommandCollection;
import org.jiira.we.WeChatMessage;
import org.jiira.we.WeGlobal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandleText {
	private static final Logger logger = LoggerFactory.getLogger(HandleText.class);
	private static HandleText instance;
	public static HandleText getInstance() {
		if(null == instance) {
			instance = new HandleText();
		}
		return instance;
	}
	private HandleText() {}
	
	public void process(WeChatMessage msg){
		switch(msg.getContent()) {
		case "我有七颗龙珠":{
			msg.setContent("恭喜你！触发了隐藏剧情！今天是 : " + new Date().toGMTString());
			break;
		}
		case "薛恒杰":{
			msg.setContent("是这个世界上最伟大的人~！~大家要爱护他、多给他钱花、还要给他车、给他房子，把存着、支付宝、微信的钱全转给他");
			break;
		}
		case "音乐":{
			msg.setMsgType(CommandCollection.MESSAGE_TEXT);//改变回复消息类型
			msg.setContent("暂时没有音乐哦");
			//这里等做了后台管理再优化吧……需要把公众号的列表存到服务器
//			WeIVV music = WeGlobal.getInstance().getWeIVV(CommandCollection.MESSAGE_VOICE);
//			logger.error("music : " + music.getItem_count());
//			logger.error("music : " + music.getItem().length);
//			if(music.getItem_count() > 0) {
//				WeIVVItem item = music.getItem()[(int) Math.floor(Math.random() * music.getItem_count())];//随机一个
//				msg.setMsgType(CommandCollection.MESSAGE_MUSIC);//改变回复消息类型
//				msg.setTitle("喵贼克~");
//				msg.setDescription("动次大次~动次大次~~切克闹");
//				msg.setThumbMediaId(item.media_id);
//			} else {
//				msg.setMsgType(CommandCollection.MESSAGE_TEXT);//改变回复消息类型
//				msg.setContent("诶呀~我没有音乐可播……");
//			}
			
			break;
		}
		case "荆轲刺秦王":{
			msg.setContent("两条毛腿肩上扛");
			break;
		}
		default:{//如果没有回答，则保留原有对话，出去后给机器人回答
			msg.setUseRobot(true);
		}
		}
	}
}
