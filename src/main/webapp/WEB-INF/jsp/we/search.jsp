<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<title></title>
<script type="text/javascript">
	$(document).ready(function() {
		var a = true;
		getSearchType();
		$("#search_btn").click(function(e) {//搜索
			e.preventDefault();  // 阻止链接跳转
			var type = $("#search_type").val();
			$.post({
				url : "getNewsSearch",
				data : {
					type:type,
					search:$("#search_text").val(),
				},
				success : function(result) {
					var list_html = "";
					if(type==<%=CommandCollection.ALL_TYPE%> 
					|| type==<%=CommandCollection.BOOK_TYPE%>
					|| type==<%=CommandCollection.STRATEGY_TYPE%>){
						var adNewsName = result['adNewsName'];
						var u = "ic?redirect=name*name_id=";
						$.each(adNewsName, function(idx, i) {
							list_html += "<div class='div_we_list_content'><a href='"+u+i['id']+"'>"+i['name']+"</a></div><br>";
						});
					}
					if(type!=<%=CommandCollection.BOOK_TYPE%> && type!=<%=CommandCollection.STRATEGY_TYPE%>){
						var adNews = result['adNews'];
						var u = "ic?redirect=news*news_id=";
						$.each(adNews, function(idx, i) {
							list_html += "<div class='div_we_list_content'><a class='aunselect' href='"+u+i['id']+"'><font class='div_we_list_a'>"+i['title']+"</font></a></div><br>";
						});
					}
					$('#we_search_content').html(list_html);
				}
			});
		});
		function getSearchType() {//获取搜索类型
			var adType = <%=CommandCollection.GetNewsTypeJson()%>
			var type = $('#search_type');
			type.empty();
			$.each(adType, function(idx, i) {
				option = "<option value='" + i['id'] + "'>" + i['name'] + "</option>";
				type.append(option);
			});
		}
	})
	</script>
</head>
<body>
<div class="div_title_block"><h2>搜索</h2></div>
<div class='div_we_list_content'>
	<input type="text" id="search_text">
	<select id="search_type"></select>
	<a id="search_btn" href="javascript:void(0)">搜索</a>
</div>
<div class="div_title_block" id="we_search_content">
	
</div>
</body>
</html>