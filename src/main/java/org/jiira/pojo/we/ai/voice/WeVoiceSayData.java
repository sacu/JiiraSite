package org.jiira.pojo.we.ai.voice;

public class WeVoiceSayData {
	public int format;//API请求中的格式编码
	public String speech;//合成语音的base64编码数据
	public String md5sum;//合成语音的md5摘要（base64编码之前）
}

