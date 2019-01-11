package org.jiira.we.process;

import org.jiira.pojo.we.ai.text.WeTextSay;
import org.jiira.pojo.we.ai.voice.WeVoiceSay;
import org.jiira.pojo.we.ai.voice.WeYTVoiceSay;
import org.jiira.utils.CommandCollection;
import org.jiira.we.DecriptUtil;
import org.jiira.we.WeGlobal;
import org.jiira.we.message.WeChatMessage;
import org.jiira.we.message.WeChatVoiceMessage;
import org.jiira.we.url.SAURLStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class HandleVoice {
	private static final Logger logger = LoggerFactory.getLogger(HandleVoice.class);
	private static HandleVoice instance;
	public static HandleVoice getInstance() {
		if(null == instance) {
			instance = new HandleVoice();
		}
		return instance;
	}
	private HandleVoice() {}

	public void process(WeChatMessage msg){
		WeTextSay r = WeGlobal.getInstance().getTextSay(msg.getRecognition());
		msg.setMsgType(CommandCollection.MESSAGE_TEXT);
		if(r.getRet() == 0) {
			String message = r.getAnswer();
			logger.error("[原始]message:" + message);
			int ret = -1;
			String speech = null;
			if(message.length() > 85) {//超过最大限制
				message = message.replaceAll("“", "");
				message = message.replaceAll("”", "");
				message = message.replaceAll(":", "");
				message = message.replaceAll("。", "");
				message = message.replaceAll("？", "");
				message = message.replaceAll("！", "");
				message = message.replaceAll("，", "");
				message = message.replaceAll(" ", "");
				message = message.replaceAll("\\r", "");
				message = message.replaceAll("\\n", "");
			}
			if(message.length() < 50) {//使用腾讯 腾讯最大支持50
				WeVoiceSay r2 = WeGlobal.getInstance().getVoiceSay(message);
				if((ret = r2.getRet()) == 0) {
					speech = r2.getSpeech();
				}
			} else if(message.length() < 85) {//优图最大支持89
				logger.error("[实际]message:" + message);
				WeYTVoiceSay r2 = WeGlobal.getInstance().getYTVoiceSay(message);
				if((ret = r2.getRet()) == 0) {
					speech = r2.getVoice();
				}
			} else {//只能直接文字输出了
				ret = -1;
			}
			//转化文字为语音
			if(ret == 0) {//写入到本地
				String type = CommandCollection.MESSAGE_VOICE;
				String wtype = CommandCollection.TEMP;
				String name = "temp.mp3";
				//转码
				byte[] data = DecriptUtil.decodeBASE64(speech);
				//写入到本地
				SAURLStream.getInstance().save(wtype, name, data);
				String path = CommandCollection.GetLocalPath(wtype);
				//获取mediaid
				JSONObject json = WeGlobal.getInstance().addTemp(path + name, type);
				WeGlobal.getInstance().unload(path, name);
				if(null != json && !json.has("errcode")) {
					String media_id = json.getString("media_id");
					msg.setMsgType(type);
					WeChatVoiceMessage voice = new WeChatVoiceMessage();
					voice.setMediaId(media_id);
					msg.setVoice(voice);
				} else {
					msg.setContent("[3." + json.getString("errcode") + "]" + r.getAnswer());
				}
			} else if(ret == -1) {
				msg.setContent("[太长了！你自己看吧！]" + r.getAnswer());
			} else {
				msg.setContent("[2." + ret + "]" + r.getAnswer());
			}
		} else {
			msg.setContent("[1." + r.getRet() + "]我没有听清你说的什么……");
		}
	}
}
