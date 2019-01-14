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
<link rel="stylesheet" href="../style/test1.css" />
<link rel="stylesheet" href="../style/ad.css" />
<script type="text/javascript" src="../javascript/jquery-3.2.0.js"></script>
<script type="text/javascript" src="../javascript/ad.js"></script>
<script>
		$(document).ready(function() {
			function onBridgeReady(){
				<%
				String timeStamp = DecriptUtil.create_timestamp();
				String nonceStr = DecriptUtil.create_nonce_str();
				ArrayList<SAHttpKVO> params = new ArrayList<SAHttpKVO>();
				params.add(new SAHttpKVO("appId", CommandCollection.AppID));
				params.add(new SAHttpKVO("timeStamp", timeStamp));
				params.add(new SAHttpKVO("nonceStr", nonceStr));
				params.add(new SAHttpKVO("package", "prepay_id=test"));
				params.add(new SAHttpKVO("signType", "MD5"));
				String paySign = DecriptUtil.ReqSign(params);
				%>
				WeixinJSBridge.invoke(
					'getBrandWCPayRequest', {
						"appId":"<%=CommandCollection.AppID%>",     //公众号名称，由商户传入     
						"timeStamp":"<%=timeStamp%>",         //时间戳，自1970年以来的秒数     
						"nonceStr":"<%=nonceStr%>", //随机串     
						"package" : "prepay_id=test",
						"signType" : "MD5", //微信签名方式：     
						"paySign" : "<%=paySign%>" //微信签名 
					}, 
					function(res) {
						if (res.err_msg == "get_brand_wcpay_request:ok") {
							alert(res)
							// 使用以上方式判断前端返回,微信团队郑重提示：
							//res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
						} else {
							alert(res.err_msg)
						}
					});
			}
			$('#recharge').click(function(event) {
				$.post({
					url : "pay",
					data : {
						money:$('#recharge_txt').val()
					},
					//成功后的方法
					success : function(result) {
						var wepay = result['wepay'];
						alert(wepay['return_code'])
						alert(wepay['result_code'])
						alert(wepay['prepay_id'])
					}
				})
				if (typeof WeixinJSBridge == "undefined") {
					if (document.addEventListener) {
						document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
					} else if (document.attachEvent) {
						document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
						document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
					}
				} else {
					//onBridgeReady();
				}
				event.preventDefault();
			})
		})
</script>
</head>
<body>
	充值
	<input type="text" id="recharge_txt" value="1">
	<a href='' id='recharge'>充值</a>
	<%-- <%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_THUMB + "/"%>' + '${thumb}'; --%>
	<%-- 	<div class='div_news_title'>" + ${json.title} + "</div>"; --%>
	<%-- 	<div class='div_news_author'>" + "作者:" + ${json.author} + "</div>"; --%>
	<!-- 	<img src='" + thumb + "'/>"; -->
	<%-- 	<div class='div_news_content'>" + ${json.content} + "</div>"; --%>
</body>
</html>