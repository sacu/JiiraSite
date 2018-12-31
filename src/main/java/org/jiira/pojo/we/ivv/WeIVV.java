/**
 * 图片\语音\视频
 */
package org.jiira.pojo.we.ivv;

public class WeIVV {
	private int total_count;
	private int item_count;
	private WeIVVItem[] item;
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
	public WeIVVItem[] getItem() {
		return item;
	}
	
	
}
