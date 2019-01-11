package org.jiira.pojo.ad;

public class WeUser {
	private String openid;//varchar(32) not null COMMENT 'openid',
	private String nickname;//varchar(32) not null COMMENT '昵称',
	private int sex;//TINYINT(1) COMMENT '0女1男2保密',
	private int vouchers;//int not null COMMENT '代金券',
	private String language;
	private String country;//varchar(32) not null COMMENT '国家',
	private String province;//varchar(32) not null COMMENT '省',
	private String city;//varchar(32) not null COMMENT '市',
	private String headimgurl;//varchar(300) not null COMMENT '头像地址',
	private String privilege;//varchar(300) not null COMMENT '特权(用,隔开)',
	private String birthday;//出生日期
	private String jointime;//timestamp default CURRENT_TIMESTAMP COMMENT '加入时间'
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getVouchers() {
		return vouchers;
	}
	public void setVouchers(int vouchers) {
		this.vouchers = vouchers;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getJointime() {
		return jointime;
	}
	public void setJointime(String jointime) {
		this.jointime = jointime;
	}
}
