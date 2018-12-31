package org.jiira.pojo.we.article;

public class WeArticle {
	private int total_count;
	private int item_count;
	private WeArticleItem[] item;
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getItem_count() {
		return item_count;
	}
	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}
	public WeArticleItem[] getItem() {
		return item;
	}
	public void setItem(WeArticleItem[] item) {
		this.item = item;
	}
	
}
