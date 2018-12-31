package org.jiira.we;

import org.slf4j.Logger;

public class WeChatMessage {
	/**
	 * 通用类型
	 */
	private String ToUserName;//开发者微信号
	private String FromUserName;//发送方帐号（一个OpenID）
	private long CreateTime;//消息创建时间 （整型）
	private String MsgType;//消息类型
	private String MsgId;//消息id，64位整型
	
	//普通消息类通用
	private String MediaId;//图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String Title;//各种的标题
	//文字消息
	private String Content;//内容
	//图像
	private String PicUrl;//图片链接（由系统生成
	//语音消息
	private String Format;//语音格式amr,speex
	//音乐
	private String MusicURL;//音乐链接
	private String HQMusicUrl;//高质量音乐链接 WIFI会优先选择
	
	//视频消息和小视频消息
	private String ThumbMediaId;//视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	
	//地理位置消息
	private String Location_X;//double 地理位置维度
	private String Location_Y;//double 地理位置经度
	private String Scale;//int 地图缩放大小
	private String Label;//地理位置信息
	
	//链接消息
	private String Description;//消息描述
	private String Url;//消息链接
	
	
	private String Event;//事件类型返回的内容，subscribe(订阅)、unsubscribe(取消订阅)
	private String EventKey;//事件的key(订阅信息：qrscene_为前缀，后面为二维码的参数值！！ 或用于菜单事件)
	
	private String Ticket;//二维码的ticket
	private String Latitude;//地理位置纬度
	private String Longitude;//地理位置经度
	private String Precision;//地理位置精度
	private boolean useRobot;
	
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = Long.valueOf(createTime);
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public boolean getUseRobot() {
		return useRobot;
	}

	public void setUseRobot(boolean useRobot) {
		this.useRobot = useRobot;
	}
	
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getMusicURL() {
		return MusicURL;
	}

	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public String getScale() {
		return Scale;
	}

	public void setScale(String scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		Precision = precision;
	}

	/**
	 * 切换发送者和接收者关系
	 */
	public void identityConvert() {
		String temp = ToUserName;
		ToUserName = FromUserName;
		FromUserName = temp;
	}
	/**
	 * 测试用
	 */
	public void print(Logger logger) {
		logger.error("##############################################");
		logger.error("ToUserName : " + ToUserName);
		logger.error("FromUserName : " + FromUserName);
		logger.error("CreateTime : " + CreateTime);
		logger.error("MsgType : " + MsgType);
		logger.error("MsgId : " + MsgId);
		logger.error("##############################################");
		logger.error("Content : " + Content);//文字消息
		logger.error("##############################################");
		logger.error("MediaId : " + MediaId);//通用MediaId
		logger.error("MusicURL" + MusicURL);//音乐链接
		logger.error("HQMusicUrl" + HQMusicUrl);//高质量音乐链接 WIFI会优先选择
		logger.error("PicUrl : " + PicUrl);//图像
		logger.error("Format : " + Format);//语音消息
		logger.error("ThumbMediaId : " + ThumbMediaId);//视频消息和小视频消息
		logger.error("##############################################");
		//地理位置消息
		logger.error("Location_X : " + Location_X);//double 地理位置维度
		logger.error("Location_Y : " + Location_Y);//double 地理位置经度
		logger.error("Scale : " + Scale);//int 地图缩放大小
		logger.error("Label : " + Label);//地理位置信息
		logger.error("##############################################");
		//链接消息
		logger.error("Title : " + Title);//消息标题
		logger.error("Description : " + Description);//消息描述
		logger.error("Url : " + Url);//消息链接
		logger.error("##############################################");
		logger.error("Event : " + Event);//事件类型返回的内容，subscribe(订阅)、unsubscribe(取消订阅)
		logger.error("##############################################");
		logger.error("EventKey : " + EventKey);//事件的key，qrscene_为前缀，后面为二维码的参数值
		logger.error("Ticket : " + Ticket);//二维码的ticket
		logger.error("##############################################");
		logger.error("Latitude : ", Latitude);//地理位置纬度
		logger.error("Longitude : ", Longitude);//地理位置经度
		logger.error("Precision : ", Precision);
	}
}
