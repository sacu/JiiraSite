package org.jiira.pojo.ad;

public class WeConsume {
	private String openid;//varchar(32) not null COMMENT 'openid',
	private int vouchers;//int not null COMMENT '代金券',
	private String out_trade_no;//订单号
	private int rows;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getVouchers() {
		return vouchers;
	}
	public void setVouchers(int vouchers) {
		this.vouchers = vouchers;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	
}
