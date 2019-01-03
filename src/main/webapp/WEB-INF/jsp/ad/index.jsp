<%@page import="org.jiira.pojo.ad.AdUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>极光互娱</title>
<link rel="stylesheet" href="style/test1.css" />
<link rel="stylesheet" href="style/ad.css" />
<script src="https://code.jquery.com/jquery-3.2.0.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#welcome").click(flip);
		$("#setting").click(flip);
		$("#about").click(flip);
		function flip(event) {
			flipForPage($(this).attr("id"));
			event.preventDefault();  // 阻止链接跳转
		}
		function flipForPage(page){
			//提交表单
			$.post({
				url : "ad/flip",
				data : {page:page},
				//成功后的方法
				success : function(result) {
					$('#content').empty()
					$('#content').html(result)
				}
			});
		}
	});
	</script>
</head>

<body>
	<!--通常被放置在页面或者页面中某个区块元素的顶部，包含整个页面或者区块的标题、简介等信息，起到引导与导航的作用。-->
	<header id="header1"> 极光互娱后台管理系统 </header>
	<!--用来定义主体中的头-->
	<article>
		<div style="height: 48px;"></div>
		${msg}
		<div id="content">
			<%@ include file="login.jsp"%>
		</div>
		<div style="height: 58px;"></div>
	</article>
	<!--一般被放置在页面或者页面中某个区块的底部，包含版权信息、联系方式等信息。-->
	<%if (null != session.getAttribute("adUser")) { %>
	<footer>
		<nav id="nav1">
			<!--表示页面的导航，可以通过导航连接到网站的其他页面，或者当前页面的其它部分。-->
			<li class="li1"><a id="welcome" href="">首页</a></li>
			<li class="li1"><a id="setting" href="">设置</a></li>
			<li class="li1"><a id="about" href="">介绍</a></li>
		</nav>
	</footer>
	<%} %>
</body>
</html>