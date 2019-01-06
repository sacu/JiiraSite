package org.jiira.we.process;

import java.util.Date;

import org.jiira.utils.CommandCollection;
import org.jiira.we.message.WeChatMessage;
import org.jiira.we.message.WeChatMusicMessage;

public class HandleText {
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
			msg.setContent("测试音乐");
			msg.setMsgType(CommandCollection.MESSAGE_MUSIC);//改变回复消息类型
			WeChatMusicMessage music = new WeChatMusicMessage();
			music.setTitle("喵贼克~");
			music.setDescription("动次大次~动次大次~~切克闹");
			music.setMusicUrl("http://188.131.228.192/manager/dream.mp3");
			music.setHQMusicUrl("http://188.131.228.192/manager/dream.mp3");
			music.setThumbMediaId("IkKppKVsdtT8T0V05gBYvtSEt0ZbjhcC0dW1Ix7DUhY");
			msg.setMusic(music);
			break;
		}
		case "音乐2":{
			msg.setContent("听吧听吧");
			msg.setMsgType(CommandCollection.MESSAGE_MUSIC);//改变回复消息类型
			WeChatMusicMessage music = new WeChatMusicMessage();
			music.setTitle("喵贼克~");
			music.setDescription("动次大次~动次大次~~切克闹");
			music.setMusicUrl("");
			music.setHQMusicUrl("");
			music.setThumbMediaId("IkKppKVsdtT8T0V05gBYvtSEt0ZbjhcC0dW1Ix7DUhY");
			msg.setMusic(music);
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
