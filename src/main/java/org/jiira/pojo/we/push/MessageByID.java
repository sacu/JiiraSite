package org.jiira.pojo.we.push;

import java.util.List;

public class MessageByID {
	private List<String> touser;
	private MessageMediaID mpnews;
	private String msgtype;
	private int send_ignore_reprint;
	public List<String> getTouser() {
		return touser;
	}
	public void setTouser(List<String> touser) {
		this.touser = touser;
	}
	public MessageMediaID getMpnews() {
		return mpnews;
	}
	public void setMpnews(MessageMediaID mpnews) {
		this.mpnews = mpnews;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public int getSend_ignore_reprint() {
		return send_ignore_reprint;
	}
	public void setSend_ignore_reprint(int send_ignore_reprint) {
		this.send_ignore_reprint = send_ignore_reprint;
	}
	
	
}
