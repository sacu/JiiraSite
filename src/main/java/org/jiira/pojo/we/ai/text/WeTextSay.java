package org.jiira.pojo.we.ai.text;

import org.jiira.pojo.we.ai.WeBaseSay;

public class WeTextSay extends WeBaseSay{
	private WeTextSayData data;
	public WeTextSayData getData() {
		return data;
	}
	public String getSession() {
		return data.session;
	}
	public String getAnswer() {
		return data.answer;
	}
}
