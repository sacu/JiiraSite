package org.jiira.pojo.we.cmenu;

public class CMenuButton {
	private String type;
	private String name;
	private CMenuButton[] sub_button;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CMenuButton[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(CMenuButton[] sub_button) {
		this.sub_button = sub_button;
	}
	
	
}
