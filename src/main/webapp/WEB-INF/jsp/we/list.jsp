<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<title></title>
	<script>
	$(document).ready(function() {
		//获取信息
		$.post({
			url : "getNewsList",
			//成功后的方法
			success : function(result) {
				var adNews = result['adNews'];
				var list_html = "";
				var u = "ic?redirect=news*news_id=";
				$.each(adNews, function(idx, i) {
					list_html += "<div class='div_we_list_content'><a href='"+u+i['id']+"'>"+i['title']+"</a></div><br>";
				});
				$('#we_list_content').html(list_html);
			}
		});
		
	})
	</script>
</head>
<body>
往期回顾
<div class="div_content_block" id="we_list_content">
	
</div>
<%-- <%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_THUMB + "/"%>' + '${thumb}'; --%>
<%-- 	<div class='div_news_title'>" + ${json.title} + "</div>"; --%>
<%-- 	<div class='div_news_author'>" + "作者:" + ${json.author} + "</div>"; --%>
<!-- 	<img src='" + thumb + "'/>"; -->
<%-- 	<div class='div_news_content'>" + ${json.content} + "</div>"; --%>
</body>
</html>