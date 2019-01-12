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
				var u = "news*news_id=";
				$.each(adNews, function(idx, i) {
					list_html += "<div class='div_we_list_content'><a href='' v='"+u+i['id']+"'>"+i['title']+"</a></div><br>";
				});
				$('#we_list_content').html(list_html);
			}
		});
		
		$("#we_list_content").click(function(event) {
			var target = $(event.target);
			if (target.prop("tagName").toLowerCase() == "a") {
				$.post({
					url : "ic",
					data : {redirect:target.attr("v")},
					//成功后的方法
					success : function(result) {
						$('#content').empty()
						$('#content').html(result)
					}
				});
				event.preventDefault();  // 阻止链接跳转
			}
		});
		
	})
	</script>
</head>
<body>
往期回顾
<div class="div_content_block" id="we_list_content">
	
</div>
</body>
</html>