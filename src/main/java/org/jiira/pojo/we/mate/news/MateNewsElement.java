package org.jiira.pojo.we.mate.news;
/**
 * 返回 "media_id":MEDIA_ID
 * @author time
 *
 */
public class MateNewsElement {
	private String title;//标题
	private String thumb_media_id;//封面，素材id
	private String author;//作者
	private String digest;//简介
	private int show_cover_pic;//是否显示封面0不显示 1显示
	private String content;//内容2W字符以内，1M以内，支持HTML，不支持JS，不支持外部图片，只支持《图文消息内部图片》详情看文档
	private String content_source_url;//原文地址
	private int need_open_comment;//是否打开评论，0打开 1不打开
	private int only_fans_can_comment;//评论限制，0所有人 1粉丝
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getContent_source_url() {
		return content_source_url;
	}
	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
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
