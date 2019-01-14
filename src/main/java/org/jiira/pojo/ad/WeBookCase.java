package org.jiira.pojo.ad;

public class WeBookCase {
	private String openid;//varchar(32) not null COMMENT 'openid',
	private int newsid;//varchar(32) not null COMMENT 'openid',
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getNewsid() {
		return newsid;
	}
	public void setNewsid(int newsid) {
		this.newsid = newsid;
	}
}
