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
			url : "getNewsList",
			//成功后的方法
			success : function(result) {
				var adNews = result['adNews'];
				var list_html = "";
				var u = "ic?redirect=news*news_id=";
				$.each(adNews, function(idx, i) {
					list_html += "<div class='div_we_list_content'><a class='aunselect' href='"+u+i['id']+"'><font class='div_we_list_a'>"+i['title']+"</font></a></div><br>";
				});
				$('#we_list_content').html(list_html);
			}
		});
	})
	
	</script>
</head>
<body>
<div class="div_title_block"><h2>往期回顾</h2></div>
<div class="div_title_block" id="we_list_content">
	
</div>
</body>
</html>