<%@page import="org.jiira.we.url.SAHttpKVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.jiira.we.DecriptUtil"%>
<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<title></title>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript">
		$(document).ready(function() {
			var wepayr;
			<%
			//if(!CommandCollection.JSSDK_INIT){//注册jssdk
			//	CommandCollection.JSSDK_INIT = true;
			%>
				$.post({//获取配置
					url : "jssdk_config",
					//成功后的方法
					success : function(result) {
						var jssdk = result['jssdk']
						wx.config({
						    debug: jssdk['debug'], // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
						    appId: jssdk['appId'], // 必填，公众号的唯一标识
						    timestamp: jssdk['timestamp'], // 必填，生成签名的时间戳
						    nonceStr: jssdk['nonceStr'], // 必填，生成签名的随机串
						    signature: jssdk['signature'],// 必填，签名
						    jsApiList: jssdk['jsApiList'] // 必填，需要使用的JS接口列表
						});
					}
				});
			<%//}%>
			
			function onBridgeReady(){
				//腾讯你妈个比！！！timestamp的s要小写
				wx.chooseWXPay({
					"appId":wepayr['appId'],         //到底要不要？？？     
					"timestamp":wepayr['timeStamp'],         //时间戳，自1970年以来的秒数     
					"nonceStr":wepayr['nonceStr'], //随机串     
					"package" : wepayr['we_package'],
					"signType" : wepayr['signType'], //微信签名方式：     
					"paySign" : wepayr['paySign'],//微信签名 
					success: function(res) {
						if (res.errMsg == "chooseWXPay:ok") {
							// 使用以上方式判断前端返回,微信团队郑重提示：
							//res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
							window.history.back(-1);//支付成功后 返回
						} else {
							alert("error" + res.err_msg)
						}
					}
				});
			}
			$('#recharge').click(function(event) {
				$.post({
					url : "pay",
					data : {
						money:$('#recharge_txt').val(),
						openid:'${weUser.openid}'
					},
					//成功后的方法
					success : function(result) {
						var wepay = result['wepay'];
						if(wepay['return_code'] == '<%=CommandCollection.SUCCESS%>'){
							if(wepay['result_code'] == '<%=CommandCollection.SUCCESS%>'){
								wepayr = result['wepayr'];
								if (typeof WeixinJSBridge == "undefined") {
									if (document.addEventListener) {
										document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
									} else if (document.attachEvent) {
										document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
										document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
									}
								} else {//开始支付
									onBridgeReady();
								}
							} else {
								alert(wepay['err_code'] + ":" +wepay['err_code_des'])
							}
						} else {
							alert(wepay['return_msg'])
						}
					}
				})
				event.preventDefault();
			})
		})
</script>
</head>
<body>
	充值
	<input type="text" id="recharge_txt" value="1">
	<a href='' id='recharge'>充值</a>
	<div id="test"></div>
	<%-- <%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_THUMB + "/"%>' + '${thumb}'; --%>
	<%-- 	<div class='div_news_title'>" + ${json.title} + "</div>"; --%>
	<%-- 	<div class='div_news_author'>" + "作者:" + ${json.author} + "</div>"; --%>
	<!-- 	<img src='" + thumb + "'/>"; -->
	<%-- 	<div class='div_news_content'>" + ${json.content} + "</div>"; --%>
</body>
</html>