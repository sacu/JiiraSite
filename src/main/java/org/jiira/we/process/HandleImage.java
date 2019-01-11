package org.jiira.we.process;

import org.jiira.pojo.we.ai.image.WeImageSay;
import org.jiira.utils.CommandCollection;
import org.jiira.we.DecriptUtil;
import org.jiira.we.WeGlobal;
import org.jiira.we.message.WeChatMessage;
import org.jiira.we.url.SAURLStream;

public class HandleImage {
	private static HandleImage instance;
	public static HandleImage getInstance() {
		if(null == instance) {
			instance = new HandleImage();
		}
		return instance;
	}
	private HandleImage() {}

	public void process(WeChatMessage msg){
		String url = msg.getPicUrl();
		msg.setMsgType(CommandCollection.MESSAGE_TEXT);
		byte[] data = SAURLStream.getInstance().download(url);
		if(null != data) {
			String image = DecriptUtil.encodeBASE64(data);
			WeImageSay r = WeGlobal.getInstance().getImageSay(image, msg.getMsgId());
			if(r.getRet() == 0) {
				msg.setContent(r.getText());
			} else {
				msg.setContent(r.getRet() + ":" + r.getMsg());
			}
		} else {
			msg.setContent("出错了:" + url);
		}
	}
}
