package org.jiira.pojo;

/**
 * 登陆对象
 * @author time
 *
 */
public class Login {
	private String userName;
	private String passWord;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public void clear() {
		userName = null;
		passWord = null;
	}
}
