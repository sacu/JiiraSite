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
	<script>
	$(document).ready(function() {
		$("#search").click(function(event) {//搜索
			var type = $("#search_type").val();
			$.post({
				url : "getNewsSearch",
				data : {
					type:type,
					search:$("#search_text").val(),
					},
				success : function(result) {
					var list_html = "";
					if(type==<%=CommandCollection.ALL_TYPE%> || type==<%=CommandCollection.BOOK_TYPE%>){
						var adNewsName = result['adNewsName'];
						var u = "ic?redirect=name*name_id=";
						$.each(adNewsName, function(idx, i) {
							list_html += "<div class='div_we_list_content'><a href='"+u+i['id']+"'>"+i['name']+"</a></div><br>";
						});
					}
					if(type==<%=CommandCollection.ALL_TYPE%> || type==<%=CommandCollection.BOOK_TYPE%>){
						var adNews = result['adNews'];
						var u = "ic?redirect=news*news_id=";
						$.each(adNews, function(idx, i) {
							list_html += "<div class='div_we_list_content'><a href='"+u+i['id']+"'>"+i['title']+"</a></div><br>";
						});
					}
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