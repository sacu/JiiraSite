<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<title></title>
	<script>
	$(document).ready(function() {
		$("#search").click(function(event) {//搜索
			$.post({
				url : "getNewsSearch",
				data : {
					type:$("#search_type").val(),
					search:$("#search_text").val(),
					},
				success : function(result) {
					var adNews = result['adNews'];
					var list_html = "";
					var u = "news*news_id=";
					$.each(adNews, function(idx, i) {
						list_html += "<div class='div_we_list_content'><a href='' v='"+u+i['id']+"'>"+i['title']+"</a></div><br>";
					});
					$('#we_search_content').html(list_html);
				}
			});
			event.preventDefault(); // 阻止链接跳转
		})
		function getSearchType() {//获取搜索类型
			var adType = <%=CommandCollection.GetNewsTypeJson()%>
			var type = $('#search_type');
			type.empty();
			$.each(adType, function(idx, i) {
				option = "<option value='" + i['id'] + "'>" + i['name'] + "</option>";
				type.append(option);
			});
		}
		getSearchType();
		

		$("#we_search_content").click(function(event) {
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
<div class='div_we_list_content'>
<input type="text" id="search_text">
<select id="search_type"></select>
<a id="search" href="">搜索</a>
</div>
<div class="div_content_block" id="we_search_content">
	
</div>
</body>
</html>