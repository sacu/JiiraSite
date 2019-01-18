<%@page import="org.jiira.pojo.ad.AdUser"%>
<%@page import="org.jiira.utils.CommandCollection"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>极光互娱</title>
<link rel="stylesheet" href="../style/test1.css?v=1.3" />
<link rel="stylesheet" href="../style/we.css?v=1.5" />
<script type="text/javascript" src="../javascript/jquery-3.2.0.js?v=1.1"></script>
<script type="text/javascript" src="../javascript/ad.js?v=1.1"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var onPage2 = $("#index");
		$("#index").click(flip);
		$("#list").click(flip);
		$("#search").click(flip);
		$("#user").click(flip);
		function flip(event) {
			flipForPage($(this).attr("id"));
			event.preventDefault();  // 阻止链接跳转
		}
		function flipForPage(page){
			//提交表单
			$.post({
				url : "ic",
				data : {redirect:page},
				//成功后的方法
				success : function(result) {
					onPage2.addClass("aunselect");
					onPage2.removeClass("aselect");
					onPage2 = $("#" + page);
					onPage2.removeClass("aunselect");
					onPage2.addClass("aselect");
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
	<header id="header1">极光互娱</header>
	<!--用来定义主体中的头-->
	<article>
		<div style="height: 48px; width: 90%; float: left;"></div>
		<div id="content">
			<jsp:include page="${page}.jsp"></jsp:include>
		</div>
		<div style="height: 150px; width: 90%; float: left;"></div>
	</article>
	<!--一般被放置在页面或者页面中某个区块的底部，包含版权信息、联系方式等信息。-->
	<footer>
		<nav id="nav1">
			<!--表示页面的导航，可以通过导航连接到网站的其他页面，或者当前页面的其它部分。-->
			<li class="li1"><a class="aselect" id=index href="">首页</a></li>
			<li class="li1"><a class="aunselect" id="list" href="">往期回顾</a></li>
			<li class="li1"><a class="aunselect" id="search" href="">搜索</a></li>
			<li class="li1"><a class="aunselect" id="user" href="">我的</a></li>
		</nav>
	</footer>
</body>
</html>