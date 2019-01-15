<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<title></title>
<link rel="stylesheet" href="../style/test1.css" />
<link rel="stylesheet" href="../style/we.css" />
<script type="text/javascript" src="../javascript/jquery-3.2.0.js"></script>
<script type="text/javascript" src="../javascript/ad.js"></script>
</head>
<body>
<div class="div_content_block">
	<div class='div_news_title' id="news_title">${adNews.title}</div>
	<div class='div_news_author' id="news_author">${adNews.author}</div>
	<div class='div_news_content' id="news_content">${adNews.content}</div>
	<img id="news_thumb" src="${adNews.thumb_id}"/>
</div>
</body>
</html>