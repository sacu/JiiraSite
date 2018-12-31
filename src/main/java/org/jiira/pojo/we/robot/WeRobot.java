package org.jiira.pojo.we.robot;

public class WeRobot {
	private int ret;
	private String msg;
	private WeRobotData data;
	public int getRet() {
		return ret;
	}
	public String getMsg() {
		return msg;
	}
	public WeRobotData getData() {
		return data;
	}
	public String getSession() {
		return data.session;
	}
	public String getAnswer() {
		return data.answer;
	}
}
