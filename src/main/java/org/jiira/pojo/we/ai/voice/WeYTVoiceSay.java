package org.jiira.pojo.we.ai.voice;

import org.jiira.pojo.we.ai.WeBaseSay;

public class WeYTVoiceSay extends WeBaseSay{
	private WeYTVoiceSayData data;

	public WeYTVoiceSayData getData() {
		return data;
	}
	public String getVoice() {
		return data.voice;
	}
}
