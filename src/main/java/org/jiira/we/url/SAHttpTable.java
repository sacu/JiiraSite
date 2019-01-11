package org.jiira.we.url;
/**
*
* @author sacu
*
* @time 2018年1月29日 下午9:00:52
*
* @mail saculer@hotmail.com
*
* instructions:
*/

import java.util.ArrayList;


public class SAHttpTable {
	private String URL;
	private String method;
	private ArrayList<SAHttpKVO> propertys;
	private ArrayList<SAHttpKVO> params;
	private SAHTML html;
	private boolean useJson;
	private String json;
	public SAHttpTable() {
		propertys = new ArrayList<>();
		params = new ArrayList<>();
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String URL) {
		this.URL = URL;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public SAHTML getHtml() {
		return html;
	}
	public void setHtml(SAHTML html) {
		this.html = html;
	}
	public ArrayList<SAHttpKVO> getPropertys() {
		return propertys;
	}
	public ArrayList<SAHttpKVO> getParams() {
		return params;
	}

	public void addPropertys(SAHttpKVO property) {
		propertys.add(property);
	}
	public void addPropertys(String key, String value) {
		propertys.add(SAHttpKVO.NKVO(key, value));
	}
	public void addParams(SAHttpKVO param) {
		params.add(param);
	}
	public void addParams(String key, String value) {
		params.add(SAHttpKVO.NKVO(key, value));
	}
	public boolean isUseJson() {
		return useJson;
	}
	public void setUseJson(boolean useJson) {
		this.useJson = useJson;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
}

