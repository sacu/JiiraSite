package org.jiira.service;

import javax.servlet.http.HttpServletRequest;

import org.jiira.we.WeChatMessage;

public interface WeChatService {
	public WeChatMessage getMessage(HttpServletRequest request);
	public String formatMessage(WeChatMessage msg);
	public void handler(WeChatMessage msg);
}
