package org.jiira.pojo.ad;

public class WeBookCase {
	private String openid;//varchar(32) not null COMMENT 'openid',
	private int name_id;
	private int news_id;//varchar(32) not null COMMENT 'openid',
	private int read;//varchar(32) not null COMMENT 'openid',
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public int getNameid() {
		return name_id;
	}
	public void setNameid(int nameid) {
		this.name_id = nameid;
	}
	public int getNewsid() {
		return news_id;
	}
	public void setNewsid(int newsid) {
		this.news_id = newsid;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	
}
