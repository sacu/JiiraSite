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
<link rel="stylesheet" href="../style/test1.css" />
<link rel="stylesheet" href="../style/ad.css?v=1.1" />
<script type="text/javascript" src="../javascript/jquery-3.2.0.js"></script>
<script type="text/javascript" src="../javascript/ad.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var onPage1 = $("#welcome");
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
				url : "flip",
				data : {page:page},
				//成功后的方法
				success : function(result) {
					onPage1.addClass("aunselect");
					onPage1.removeClass("aselect");
					onPage1 = $("#" + page);
					onPage1.removeClass("aunselect");
					onPage1.addClass("aselect");
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
		<div style="height: 48px; width: 100%; float: left;"></div>
		<div style="height: auto" id="content">
			<%@ include file="login.jsp"%>
		</div>
		<div style="height: 150px; width: 100%; float: left;"></div>
	</article>
	<!--一般被放置在页面或者页面中某个区块的底部，包含版权信息、联系方式等信息。-->
	<%if (null != session.getAttribute("adUser")) { %>
	<footer>
		<nav id="nav1">
			<!--表示页面的导航，可以通过导航连接到网站的其他页面，或者当前页面的其它部分。-->
			<li class="li1"><a class="aselect" id="welcome" href="">首页</a></li>
			<li class="li1"><a class="aunselect" id="setting" href="">设置</a></li>
			<li class="li1"><a class="aunselect" id="about" href="">介绍</a></li>
		</nav>
	</footer>
	<%} %>
</body>
</html>