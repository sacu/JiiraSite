package org.jiira.service;

import javax.servlet.http.HttpServletRequest;

import org.jiira.we.message.WeChatMessage;

public interface WeChatService {
	public WeChatMessage getMessage(HttpServletRequest request);
	public String formatMessage(WeChatMessage msg);
	public String handler(WeChatMessage msg);
}
