package org.jiira.pojo.ad;

public class AdNews {
	private int id;//'主键',
	private String media_id;//
	private String title;// '图文标题',
	private int type;// '图文类型',
	private int consume;// '阅读花费',
	private int name_id;// '书名ID',

	private String thumb_media_id;//'图文消息的封面图片素材id',
	private String author;//'图文作者',
	private String digest;//'图文消息的摘要，不填写会自动抓取',
	private int show_cover_pic;// '是否显示封面，1显示 0不显示',
	private String content;// '文章内容，支持HTML',
	private int need_open_comment;// '是否打开评论 1打开 0关闭',
	private int only_fans_can_comment;// '是否限制评论 1限制 0不限制'
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getConsume() {
		return consume;
	}
	public void setConsume(int consume) {
		this.consume = consume;
	}
	public int getName_id() {
		return name_id;
	}
	public void setName_id(int name_id) {
		this.name_id = name_id;
	}
	public String getThumb_media_id() {
		return thumb_media_id;
	}
	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public int getShow_cover_pic() {
		return show_cover_pic;
	}
	public void setShow_cover_pic(int show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getNeed_open_comment() {
		return need_open_comment;
	}
	public void setNeed_open_comment(int need_open_comment) {
		this.need_open_comment = need_open_comment;
	}
	public int getOnly_fans_can_comment() {
		return only_fans_can_comment;
	}
	public void setOnly_fans_can_comment(int only_fans_can_comment) {
		this.only_fans_can_comment = only_fans_can_comment;
	}
}
