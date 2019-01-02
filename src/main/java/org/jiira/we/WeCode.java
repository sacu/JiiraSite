package org.jiira.we;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class WeCode {
	private static final Logger logger = LoggerFactory.getLogger(WeCode.class);
	private static WeCode instance;

	public static WeCode getInstance() {
		if (null == instance) {
			instance = new WeCode();
		}
		return instance;
	}

	private WeCode() {
	}

	public int check(JSONObject json) {
		if (!json.containsKey("errcode")) {
			return 0;
		}
		int code = json.getInt("errcode");
		switch (code) {
		case -1: {
			logger.error("系统繁忙，此时请开发者稍候再试");
			return code;
		}
		case 0: {
			logger.error("请求成功");
			return code;
		}
		case 40001: {
			logger.error(
					"获取 access_token 时 AppSecret 错误，或者 access_token 无效。请开发者认真比对 AppSecret 的正确性，或查看是否正在为恰当的公众号调用接口");
			return code;
		}
		case 40002: {
			logger.error("不合法的凭证类型");
			return code;
		}
		case 40003: {
			logger.error("不合法的 OpenID ，请开发者确认 OpenID （该用户）是否已关注公众号，或是否是其他公众号的 OpenID");
			return code;
		}
		case 40004: {
			logger.error("不合法的媒体文件类型");
			return code;
		}
		case 40005: {
			logger.error("不合法的文件类型");
			return code;
		}
		case 40006: {
			logger.error("不合法的文件大小");
			return code;
		}
		case 40007: {
			logger.error("不合法的媒体文件 id");
			return code;
		}
		case 40008: {
			logger.error("不合法的消息类型");
			return code;
		}
		case 40009: {
			logger.error("不合法的图片文件大小");
			return code;
		}
		case 40010: {
			logger.error("不合法的语音文件大小");
			return code;
		}
		case 40011: {
			logger.error("不合法的视频文件大小");
			return code;
		}
		case 40012: {
			logger.error("不合法的缩略图文件大小");
			return code;
		}
		case 40013: {
			logger.error("不合法的 AppID ，请开发者检查 AppID 的正确性，避免异常字符，注意大小写");
			return code;
		}
		case 40014: {
			logger.error("不合法的 access_token ，请开发者认真比对 access_token 的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口");
			return code;
		}
		case 40015: {
			logger.error("不合法的菜单类型");
			return code;
		}
		case 40016: {
			logger.error("不合法的按钮个数");
			return code;
		}
		case 40017: {
			logger.error("不合法的按钮个数");
			return code;
		}
		case 40018: {
			logger.error("不合法的按钮名字长度");
			return code;
		}
		case 40019: {
			logger.error("不合法的按钮 KEY 长度");
			return code;
		}
		case 40020: {
			logger.error("不合法的按钮 URL 长度");
			return code;
		}
		case 40021: {
			logger.error("不合法的菜单版本号");
			return code;
		}
		case 40022: {
			logger.error("不合法的子菜单级数");
			return code;
		}
		case 40023: {
			logger.error("不合法的子菜单按钮个数");
			return code;
		}
		case 40024: {
			logger.error("不合法的子菜单按钮类型");
			return code;
		}
		case 40025: {
			logger.error("不合法的子菜单按钮名字长度");
			return code;
		}
		case 40026: {
			logger.error("不合法的子菜单按钮 KEY 长度");
			return code;
		}
		case 40027: {
			logger.error("不合法的子菜单按钮 URL 长度");
			return code;
		}
		case 40028: {
			logger.error("不合法的自定义菜单使用用户");
			return code;
		}
		case 40029: {
			logger.error("不合法的 oauth_code");
			return code;
		}
		case 40030: {
			logger.error("不合法的 refresh_token");
			return code;
		}
		case 40031: {
			logger.error("不合法的 openid 列表");
			return code;
		}
		case 40032: {
			logger.error("不合法的 openid 列表长度");
			return code;
		}
		case 40033: {
			logger.error("不合法的请求字符，不能包含 \\uxxxx 格式的字符");
			return code;
		}
		case 40035: {
			logger.error("不合法的参数");
			return code;
		}
		case 40038: {
			logger.error("不合法的请求格式");
			return code;
		}
		case 40039: {
			logger.error("不合法的 URL 长度");
			return code;
		}
		case 40050: {
			logger.error("不合法的分组 id");
			return code;
		}
		case 40051: {
			logger.error("分组名字不合法");
			return code;
		}
		case 40060: {
			logger.error("删除单篇图文时，指定的 article_idx 不合法");
			return code;
		}
		case 40117: {
			logger.error("分组名字不合法");
			return code;
		}
		case 40118: {
			logger.error("media_id 大小不合法");
			return code;
		}
		case 40119: {
			logger.error("button 类型错误");
			return code;
		}
		case 40120: {
			logger.error("button 类型错误");
			return code;
		}
		case 40121: {
			logger.error("不合法的 media_id 类型");
			return code;
		}
		case 40132: {
			logger.error("微信号不合法");
			return code;
		}
		case 40137: {
			logger.error("不支持的图片格式");
			return code;
		}
		case 40155: {
			logger.error("请勿添加其他公众号的主页链接");
			return code;
		}
		case 41001: {
			logger.error("缺少 access_token 参数");
			return code;
		}
		case 41002: {
			logger.error("缺少 appid 参数");
			return code;
		}
		case 41003: {
			logger.error("缺少 refresh_token 参数");
			return code;
		}
		case 41004: {
			logger.error("缺少 secret 参数");
			return code;
		}
		case 41005: {
			logger.error("缺少多媒体文件数据");
			return code;
		}
		case 41006: {
			logger.error("缺少 media_id 参数");
			return code;
		}
		case 41007: {
			logger.error("缺少子菜单数据");
			return code;
		}
		case 41008: {
			logger.error("缺少 oauth code");
			return code;
		}
		case 41009: {
			logger.error("缺少 openid");
			return code;
		}
		case 42001: {

			logger.error("access_token 超时，请检查 access_token 的有效期，请参考基础支持 - 获取 access_token 中，对 access_token 的详细机制说明");
			return code;
		}
		case 42002: {
			logger.error("refresh_token 超时");
			return code;
		}
		case 42003: {
			logger.error("oauth_code 超时");
			return code;
		}
		case 42007: {
			logger.error("用户修改微信密码， accesstoken 和 refreshtoken 失效，需要重新授权");
			return code;
		}
		case 43001: {
			logger.error("需要 GET 请求");
			return code;
		}
		case 43002: {
			logger.error("需要 POST 请求");
			return code;
		}
		case 43003: {
			logger.error("需要 HTTPS 请求");
			return code;
		}
		case 43004: {
			logger.error("需要接收者关注");
			return code;
		}
		case 43005: {
			logger.error("需要好友关系");
			return code;
		}
		case 43019: {
			logger.error("需要将接收者从黑名单中移除");
			return code;
		}
		case 44001: {
			logger.error("多媒体文件为空");
			return code;
		}
		case 44002: {
			logger.error("POST 的数据包为空");
			return code;
		}
		case 44003: {
			logger.error("图文消息内容为空");
			return code;
		}
		case 44004: {
			logger.error("文本消息内容为空");
			return code;
		}
		case 45001: {
			logger.error("多媒体文件大小超过限制");
			return code;
		}
		case 45002: {
			logger.error("消息内容超过限制");
			return code;
		}
		case 45003: {
			logger.error("标题字段超过限制");
			return code;
		}
		case 45004: {
			logger.error("描述字段超过限制");
			return code;
		}
		case 45005: {
			logger.error("链接字段超过限制");
			return code;
		}
		case 45006: {
			logger.error("图片链接字段超过限制");
			return code;
		}
		case 45007: {
			logger.error("语音播放时间超过限制");
			return code;
		}
		case 45008: {
			logger.error("图文消息超过限制");
			return code;
		}
		case 45009: {
			logger.error("接口调用超过限制");
			return code;
		}
		case 45010: {
			logger.error("创建菜单个数超过限制");
			return code;
		}
		case 45011: {
			logger.error("API 调用太频繁，请稍候再试");
			return code;
		}
		case 45015: {
			logger.error("回复时间超过限制");
			return code;
		}
		case 45016: {
			logger.error("系统分组，不允许修改");
			return code;
		}
		case 45017: {
			logger.error("分组名字过长");
			return code;
		}
		case 45018: {
			logger.error("分组数量超过上限");
			return code;
		}
		case 45047: {
			logger.error("客服接口下行条数超过上限");
			return code;
		}
		case 46001: {
			logger.error("不存在媒体数据");
			return code;
		}
		case 46002: {
			logger.error("不存在的菜单版本");
			return code;
		}
		case 46003: {
			logger.error("不存在的菜单数据");
			return code;
		}
		case 46004: {
			logger.error("不存在的用户");
			return code;
		}
		case 47001: {
			logger.error("解析 JSON/XML 内容错误");
			return code;
		}
		case 48001: {
			logger.error("api 功能未授权，请确认公众号已获得该接口，可以在公众平台官网 - 开发者中心页中查看接口权限");
			return code;
		}
		case 48002: {
			logger.error("粉丝拒收消息（粉丝在公众号选项中，关闭了 “ 接收消息 ” ）");
			return code;
		}
		case 48004: {
			logger.error("api 接口被封禁，请登录 mp.weixin.qq.com 查看详情");
			return code;
		}
		case 48005: {
			logger.error("api 禁止删除被自动回复和自定义菜单引用的素材");
			return code;
		}
		case 48006: {
			logger.error("api 禁止清零调用次数，因为清零次数达到上限");
			return code;
		}
		case 48008: {
			logger.error("没有该类型消息的发送权限");
			return code;
		}
		case 50001: {
			logger.error("用户未授权该 api");
			return code;
		}
		case 50002: {
			logger.error("用户受限，可能是违规后接口被封禁");
			return code;
		}
		case 50005: {
			logger.error("用户未关注公众号");
			return code;
		}
		case 61451: {
			logger.error("参数错误 (invalid parameter)");
			return code;
		}
		case 61452: {
			logger.error("无效客服账号 (invalid kf_account)");
			return code;
		}
		case 61453: {
			logger.error("客服帐号已存在 (kf_account exsited)");
			return code;
		}
		case 61454: {
			logger.error("客服帐号名长度超过限制 ( 仅允许 10 个英文字符，不包括 @ 及 @ 后的公众号的微信号 )(invalid kf_acount length)");
			return code;
		}
		case 61455: {
			logger.error("客服帐号名包含非法字符 ( 仅允许英文 + 数字 )(illegal character in kf_account)");
			return code;
		}
		case 61456: {
			logger.error("客服帐号个数超过限制 (10 个客服账号 )(kf_account count exceeded)");
			return code;
		}
		case 61457: {
			logger.error("无效头像文件类型 (invalid file type)");
			return code;
		}
		case 61450: {
			logger.error("系统错误 (system error)");
			return code;
		}
		case 61500: {
			logger.error("日期格式错误");
			return code;
		}
		case 65301: {
			logger.error("不存在此 menuid 对应的个性化菜单");
			return code;
		}
		case 65302: {
			logger.error("没有相应的用户");
			return code;
		}
		case 65303: {
			logger.error("没有默认菜单，不能创建个性化菜单");
			return code;
		}
		case 65304: {
			logger.error("MatchRule 信息为空");
			return code;
		}
		case 65305: {
			logger.error("个性化菜单数量受限");
			return code;
		}
		case 65306: {
			logger.error("不支持个性化菜单的帐号");
			return code;
		}
		case 65307: {
			logger.error("个性化菜单信息为空");
			return code;
		}
		case 65308: {
			logger.error("包含没有响应类型的 button");
			return code;
		}
		case 65309: {
			logger.error("个性化菜单开关处于关闭状态");
			return code;
		}
		case 65310: {
			logger.error("填写了省份或城市信息，国家信息不能为空");
			return code;
		}
		case 65311: {
			logger.error("填写了城市信息，省份信息不能为空");
			return code;
		}
		case 65312: {
			logger.error("不合法的国家信息");
			return code;
		}
		case 65313: {
			logger.error("不合法的省份信息");
			return code;
		}
		case 65314: {
			logger.error("不合法的城市信息");
			return code;
		}
		case 65316: {
			logger.error("该公众号的菜单设置了过多的域名外跳（最多跳转到 3 个域名的链接）");
			return code;
		}
		case 65317: {
			logger.error("不合法的 URL");
			return code;
		}
		case 9001001: {
			logger.error("POST 数据参数不合法");
			return code;
		}
		case 9001002: {
			logger.error("远端服务不可用");
			return code;
		}
		case 9001003: {
			logger.error("Ticket 不合法");
			return code;
		}
		case 9001004: {
			logger.error("获取摇周边用户信息失败");
			return code;
		}
		case 9001005: {
			logger.error("获取商户信息失败");
			return code;
		}
		case 9001006: {
			logger.error("获取 OpenID 失败");
			return code;
		}
		case 9001007: {
			logger.error("上传文件缺失");
			return code;
		}
		case 9001008: {
			logger.error("上传素材的文件类型不合法");
			return code;
		}
		case 9001009: {
			logger.error("上传素材的文件尺寸不合法");
			return code;
		}
		case 9001010: {
			logger.error("上传失败");
			return code;
		}
		case 9001020: {
			logger.error("帐号不合法");
			return code;
		}
		case 9001021: {
			logger.error("已有设备激活率低于 50% ，不能新增设备");
			return code;
		}
		case 9001022: {
			logger.error("设备申请数不合法，必须为大于 0 的数字");
			return code;
		}
		case 9001023: {
			logger.error("已存在审核中的设备 ID 申请");
			return code;
		}
		case 9001024: {
			logger.error("一次查询设备 ID 数量不能超过 50");
			return code;
		}
		case 9001025: {
			logger.error("设备 ID 不合法");
			return code;
		}
		case 9001026: {
			logger.error("页面 ID 不合法");
			return code;
		}
		case 9001027: {
			logger.error("页面参数不合法");
			return code;
		}
		case 9001028: {
			logger.error("一次删除页面 ID 数量不能超过 10");
			return code;
		}
		case 9001029: {
			logger.error("页面已应用在设备中，请先解除应用关系再删除");
			return code;
		}
		case 9001030: {
			logger.error("一次查询页面 ID 数量不能超过 50");
			return code;
		}
		case 9001031: {
			logger.error("时间区间不合法");
			return code;
		}
		case 9001032: {
			logger.error("保存设备与页面的绑定关系参数错误");
			return code;
		}
		case 9001033: {
			logger.error("门店 ID 不合法");
			return code;
		}
		case 9001034: {
			logger.error("设备备注信息过长");
			return code;
		}
		case 9001035: {
			logger.error("设备申请参数不合法");
			return code;
		}
		case 9001036: {
			logger.error("查询起始值 begin 不合法");
			return code;
		}
		}
		return 0;
	}
}
