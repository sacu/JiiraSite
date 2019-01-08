package org.jiira.pojo.ad;

public class AdNewsType {
	private int id;//'主键',
	private String name;//类型名称
	private int protect;//删除保护（1不可删除 0可删除）
	private int level;//拉取级别（0-3）
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProtect() {
		return protect;
	}
	public void setProtect(int protect) {
		this.protect = protect;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
