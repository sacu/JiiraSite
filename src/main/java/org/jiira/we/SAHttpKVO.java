package org.jiira.we;
/**
*
* @author sacu
*
* @time 2018年1月29日 下午9:23:31
*
* @mail saculer@hotmail.com
*
* instructions:
*/
public class SAHttpKVO {
	private String key;
	private String value;
	
	public SAHttpKVO(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static SAHttpKVO NKVO(String key, String value) {
		return new SAHttpKVO(key, value);
	}
}
