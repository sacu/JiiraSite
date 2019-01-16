package org.jiira.pojo.we.pay;

public class WeJSSDKConfig {
	 private boolean debug; // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	 private String appId;//: '<%=CommandCollection.AppID%>', // 必填，公众号的唯一标识
	 private String timestamp;//: '<%=DecriptUtil.create_nonce_str()%>', // 必填，生成签名的时间戳
	 private String nonceStr;//: '', // 必填，生成签名的随机串
	 private String signature;//: '',// 必填，签名
	 private String[] jsApiList;//: [] // 必填，需要使用的JS接口列表
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String[] getJsApiList() {
		return jsApiList;
	}
	public void setJsApiList(String[] jsApiList) {
		this.jsApiList = jsApiList;
	}
}
