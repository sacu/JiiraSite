<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<title></title>
<link rel="stylesheet" href="../style/test1.css?v=1.3" />
<link rel="stylesheet" href="../style/we.css?v=1.5" />
<script type="text/javascript" src="../javascript/jquery-3.2.0.js?v=1.1"></script>
<script type="text/javascript" src="../javascript/ad.js?v=1.1"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var dir_data;
		var cnews_id;
		function getNews(name_id){
			//获取信息
			$.post({
				url : "getNewsName",
				data : {
					name_id:name_id,
					openid:'${weUser.openid}'
				},
				//成功后的方法
				success : function(result) {
					var adNewsName = result['adNewsName'];
					$('#news_name').html(adNewsName['name']);
					$('#news_author').html(adNewsName['author']);
					$('#news_digest').html(adNewsName['digest']);
					$('#name_news').attr('href','ic?redirect=news*news_id='+ result['news_id']);
				}
			});
		}
		getNews(${name_id});
	})
</script>
</head>
<body>
<div class="div_content_block">
	<div class='div_news_title' id="news_title"></div>
	<div class='div_news_author' id="news_author"></div>
	<div class='div_news_content' id="news_digest"></div>
</div>
<div class="div_content_block">
	<a id="name_news" href="ic?redirect=news*news_id=">开始阅读</a><br>
	<a href="ic?redirect=index">返回首页</a><br>
	<div style="height: 48px; width: 90%; float: left;"></div>
</div>
</body>
</html>