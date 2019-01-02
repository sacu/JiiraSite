<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>setting</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#image").click(setting_flip);
		$("#voice").click(setting_flip);
		$("#video").click(setting_flip);
		$("#news_image").click(setting_flip);
		$("#news").click(setting_flip);
	});
	function setting_flip(event) {
		$.post({
			url : "ad/setting_flip",
			data : {page:$(this).attr("id")},
			//成功后的方法
			success : function(result) {
				$('#setting').empty();
				$('#setting').html(result);
			}
		});
		event.preventDefault();  // 阻止链接跳转
	}
</script>
</head>
<body>
	<div style="height: 48px;"></div>
	<header id="header2">
		<nav id="nav2">
			<!--表示页面的导航，可以通过导航连接到网站的其他页面，或者当前页面的其它部分。-->
			<li class="li1"><a id="image" href="">图片</a></li>
			<li class="li1"><a id="voice" href="">语音</a></li>
			<li class="li1"><a id="video" href="">缩略图</a></li>
			<li class="li1"><a id="news_image" href="">图文内图片</a></li>
			<li class="li1"><a id="news" href="">图文</a></li>
		</nav>
	</header>
	<article>
		<div style="height: 48px;"></div>
		<div id="setting">
			<%@ include file="setting_image.jsp"%>
		</div>
	</article>
</body>
</html>
