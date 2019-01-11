package org.jiira.utils;

public class HandleTextBox {
	private String text;
	private String key;
	private String value;
	public HandleTextBox(String text) {
		this.text = text;
		String[] sol = text.split("\\|");
		if(sol.length == 2) {
			key = sol[0];
			value = sol[1];
		} else {
			key = "none";
		}
	}
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	public String getText() {
		return text;
	}
}
