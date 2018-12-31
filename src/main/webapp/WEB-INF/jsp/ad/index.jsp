<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<title>test1</title>
	<link rel="stylesheet" href="style/test1.css"/>
	<script src="javascript/test1_1.js" language="javascript" type="text/javascript"></script>
	<script src="javascript/test1_2.js" language="javascript" type="text/javascript"></script>
</head>
	
<body>
<!--通常被放置在页面或者页面中某个区块元素的顶部，包含整个页面或者区块的标题、简介等信息，起到引导与导航的作用。-->
	<header>
		极光互娱后台管理系统
	</header><!--用来定义主体中的头-->
	
	<article>
		<%
        HttpSession s = request.getSession();
  		%>
	</article>
	<!--一般被放置在页面或者页面中某个区块的底部，包含版权信息、联系方式等信息。-->
	<footer>
		<nav><!--表示页面的导航，可以通过导航连接到网站的其他页面，或者当前页面的其它部分。-->
		<li class="li1"><a href="#1">首页</a></li>
		<li class="li1"><a href="#3">分类</a></li>
		<li class="li1"><a href="#3">会员</a></li>
		</nav>
	</footer>
</body>
</html>