package org.jiira.pojo.we.ai.image;

import org.jiira.pojo.we.ai.WeBaseSay;

public class WeImageSay extends WeBaseSay {
	private WeImageSayData data;

	public String getText() {
		return data.text;
	}
	public WeImageSayData getData() {
		return data;
	}
}
