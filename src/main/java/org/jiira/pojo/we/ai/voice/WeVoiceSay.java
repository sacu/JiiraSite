package org.jiira.pojo.we.ai.voice;

import org.jiira.pojo.we.ai.WeBaseSay;

public class WeVoiceSay extends WeBaseSay{
	private WeVoiceSayData data;

	public WeVoiceSayData getData() {
		return data;
	}
	public int getFormat() {
		return data.format;
	}
	public String getSpeech() {
		return data.speech;
	}
	public String getMD5sum() {
		return data.md5sum;
	}
}
