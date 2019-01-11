package org.jiira;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jiira.pojo.we.ai.voice.WeYTVoiceSay;
import org.jiira.we.DecriptUtil;
import org.jiira.we.WeGlobal;

public class Launch {
	public static void main(String[] args) {
		String message = "有一天，老师正在讲桌上讲课，我觉得无聊，于是拿出MP3想听歌，又怕被老师逮住，于是我碰了一下同桌，指了指老师，又指了指我耳朵上的耳机。没想到二货同桌突然站起来说，老师你小点声音，我同桌听歌都听不见了。";
		System.out.println(message.length());
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
		System.out.println(message.length());
		WeYTVoiceSay voice = WeGlobal.getInstance().getYTVoiceSay(message);
		if(voice.getRet() == 0) {
			byte[] a = DecriptUtil.decodeBASE64(voice.getVoice());
			
			try {
				FileOutputStream os = new FileOutputStream("d://a.mp3");
				os.write(a);
				System.out.println("成功");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println(voice.getRet() + ":" + voice.getMsg());
		}
	}
}
