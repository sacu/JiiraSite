<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<title></title>
<script type="text/javascript">
	$(document).ready(function() {
		//获取信息
		$.post({
			url : "getNews",
			data : {
				openid:'${weUser.openid}',
				news_id:${news_id}
			},
			//成功后的方法
			success : function(result) {
				if(result['check'] == 0){
					var info = result['info']
					$('#news_title').html(info);
					$('#news_author').html(info);
					$('#news_content').html(info);
				} else {
					var adNews = result['adNews'];
					$('#news_title').html(adNews['title']);
					$('#news_author').html(adNews['author']);
					$('#news_content').html(adNews['content']);
					$("#news_thumb").attr('src', '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_THUMB + "/"%>' + result['thumb']);
				}
			}
		});
	})
</script>
</head>
<body>
<div class="div_content_block">
	<div class='div_news_title' id="news_title"></div>
	<div class='div_news_author' id="news_author"></div>
	<div class='div_news_content' id="news_content"></div>
	<img id="news_thumb"/>
</div>
</body>
</html>