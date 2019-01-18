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
		function getNews(autopay, isdir, news_id){
			cnews_id = news_id;
			//获取信息
			$.post({
				url : "getNews",
				data : {
					openid:'${weUser.openid}',
					news_id:news_id,
					autopay: autopay,
					isdir:isdir,
					sautopay: $("#autopay").is(":checked")
				},
				//成功后的方法
				success : function(result) {
					var adNews = result['adNews'];
					$('#news_title').html(adNews['title']);
					$('#news_author').html("作者 " + adNews['author']);
					$("#news_thumb").attr('src', adNews['thumb_id']);
					var check = result['check'];
					if(check == 0){
						$('#news_content').html(adNews['content']);
					} else if (check == 1){//余额不足、充值
						$('#news_content').html("<a v='news_pay' href=''>确认购买</a>");
						window.location.href="redirect?redirect=recharge";
					} else {//2
						$('#news_content').html("<a v='news_pay' href=''>确认购买</a>");
					}
					if(adNews['type'] == <%=CommandCollection.BOOK_TYPE%>){
						$("#news_dir").css("display", "");
						var dir_news = $("#dir_news")
						if(isdir){
							dir_data = result['dir'];
							$.each(dir_data, function(idx, i) {
								var option = "<option value='" + i['id'] + "'>" + i['title'] + "</option>";
								dir_news.append(option);
							});
						}
						dir_news.find("option:selected").attr("selected",false);
						dir_news.find("option[value='"+cnews_id+"']").attr("selected",true);
					} else {
						$("#news_dir").css("display", "none");
						dir_data = null;
					}
				}
			});
		}
		$('#news_content').click(function(event) {
			var target = $(event.target);
			if(target.prop("tagName").toLowerCase() == "a"){
				switch(target.attr("v")){
					case "news_pay":
						getNews(true, false, cnews_id);
						break;
				}
				event.preventDefault();  // 阻止链接跳转
			}
		});
		$('#prev_news').click(function(event) {
			for(i = 0; i < dir_data.length; ++i){
				if(dir_data[i].id == cnews_id){
					if(i > 0){
						getNews(false, false, dir_data[i-1].id);
					} else {
						alert("没有了");
					}
					break;
				}
			}
			event.preventDefault();  // 阻止链接跳转
		})
		$('#next_news').click(function(event) {
			for(i = 0; i < dir_data.length; ++i){
				if(dir_data[i].id == cnews_id){
					if(i < dir_data.length - 1){
						getNews(false, false, dir_data[i+1].id);
					} else {
						alert("没有了");
					}
					break;
				}
			}
			event.preventDefault();  // 阻止链接跳转
		})
		$("#dir_news").change(function(){
			var id = $("#dir_news").val();
			if(id != cnews_id){
				getNews(false, false, id);
			}
		})
		$("#autopay").prop("checked", ${weUser.autopay==1});
		getNews(false, true, ${news_id});
	})
</script>
</head>
<body>
<div class="div_content_block">
	<div class='div_news_title' id="news_title"></div>
	<div class='div_news_author' id="news_author"></div>
	<div class="div_news_autopay">自动购买:<input id="autopay" type="checkbox"></div>
	<div class='div_news_content' id="news_content"></div>
	<img class="div_image_img" id="news_thumb"/>
	<div class="div_flip" id="news_dir">
		<div class="div_flip_prev"><a id="prev_news" href=''>上一篇</a></div>
		<div class="div_flip_dir"><select name="dir_news" id="dir_news"></select></div>
		<div class="div_flip_next"><a id="next_news" href=''>下一篇</a></div>
	</div>
	<div style="height: 100px; width: 90%; float: left;"></div>
</div>
</body>
</html>