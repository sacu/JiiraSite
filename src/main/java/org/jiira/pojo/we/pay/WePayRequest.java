package org.jiira.pojo.we.pay;

public class WePayRequest {
	private String appId;     
	private int timeStamp;     
	private String nonceStr;
	private String we_package;//prepay_id=test",
	private String signType;     
	private String paySign;
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getWe_package() {
		return we_package;
	}
	public void setWe_package(String we_package) {
		this.we_package = we_package;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	} 
}
