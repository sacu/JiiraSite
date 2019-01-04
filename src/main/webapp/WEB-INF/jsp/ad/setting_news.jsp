<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>news</title>
<script type="text/javascript">
	var ad = "ad/";//提交服务器后是 ad/
	$(document).ready(function() {
		var adVLD;
		$("#ulvi_btn").click(function() {
			//提交表单
        	$.post({
                url: ad + "addNews",
                data: $("#uln_form").serialize(),
                //成功后的方法
                success: function (result) {
                	$('#uln_msg').empty();
					$('#uln_msg').html(JSON.stringify(result));
					getNIList();//更新列表
                }
            });
		})
		function getNIList() {
			//提交表单
			$.post({
				url : ad + "getNewsList",
				data:{type:"news"},
				//成功后的方法
				success : function(result) {
					var _adVLD = result['adNewsList'];
					adVLD = {};
					var n_list_html = "";
					$.each(_adVLD, function(idx, i) {
						var _d = i['id'];
						adVLD[_d] = i;
						n_list_html += "<div class='div_list'><div class='div_list_check'>";
						n_list_html += "<input type='checkbox' name='ni_check' value='" + _d + "'/></div>";
						n_list_html += "<div class='div_list_title'>" + i['title'] + "</div>";
						n_list_html += "<div class='div_list_digest'>" + i['digest'] + "</div>";
						n_list_html += "<div class='div_list_media_id'>" + i['media_id'] + "</div>";
						n_list_html += "<div class='div_list_check'>"+(i['need_open_comment']==0?"关闭":"开启")+"</div>";
						n_list_html += "<div class='div_list_check'>"+(i['only_fans_can_comment']==0?"粉丝":"全部")+"</div>";
						n_list_html += "<div class='div_list_submit'><a id='gni' v='" + _d + "' href=''>获取Media</a></div>";
						n_list_html += "<div class='div_list_clear'><a id='cni' v='" + _d + "' href=''>清除Media</a></div>";
						n_list_html += "<div class='div_list_delete'><a id='dni' v='" + _d + "' href=''>删除</a></div>";
						n_list_html += "</div>";
					});
					$('#n_list').empty();
					$('#n_list').html(n_list_html);
				}
			});
		}
		$("#n_list").click(function(event) {
			var target = $(event.target);
			var id = target.attr("id");
			var url = "";
			var data;
			if(undefined != id){
				switch(id){
				case "gni":
					url = ad + "getNewsToWe";
					var adV = adVLD[target.attr("v")];
					nis_command(url, JSON.stringify([serializeObject(adV)]))
					break;
				case "cni":
					url = ad + "clearNewsToWe";
					var adV = adVLD[target.attr("v")];
					nis_command(url, JSON.stringify([serializeObject(adV)]))
					break;
				case "dni":
					url = ad + "deleteNewsToWe";
					var adV = adVLD[target.attr("v")];
					nis_command(url, JSON.stringify([serializeObject(adV)]))
					break;
				}
				event.preventDefault();  // 阻止链接跳转
			}
		})
		function nis_command(url, data){
			if(url.length > 0){//} && data['adNews'].length > 0){
				$.post({
					url : url,
					data : data,
					contentType:"application/json",
					success : function(result) {
						getNIList();
					}
				});
			}
		}
		$("#batch_gn").click(function(event) {
			var url = ad + "getNewsToWe";
			var data = [];
			$.each($('input:checkbox:checked'),function(){
				data.push(serializeObject(adVLD[$(this).val()]))
            });
			nis_command(url, JSON.stringify(data));
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_cn").click(function(event) {
			var url = ad + "clearNewsToWe";
			var data = [];
			$.each($('input:checkbox:checked'),function(){
				data.push(serializeObject(adVLD[$(this).val()]))
            });
			nis_command(url, JSON.stringify(data));
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_dn").click(function(event) {
			var url = ad + "deleteNewsToWe";
			var data = [];
			$.each($('input:checkbox:checked'),function(){
				console.info($(this).val())
				data.push(serializeObject(adVLD[$(this).val()]))
            });
			nis_command(url, JSON.stringify(data));
			event.preventDefault();  // 阻止链接跳转
		})
		getNIList();
	});
</script>
</head>
<body>
	<h1>图文</h1>
	<form id="uln_form" method="post">
		标题:<input type="text" name="title" id="title" value="我是标题"/><br>
		封面素材ID:<input type="text" name="thumb_media_id" value="IkKppKVsdtT8T0V05gBYvlBQcB4ivZz6hFO4cZWzwbk"/><br>
		作者:<input type="text" name="author" value="sa"/><br>
		摘要:<textarea name="digest" maxlength="100">你好</textarea><br>
		是否显示封面:
		是<input type='radio' name='show_cover_pic' id='show_cover_pic' value="1" checked/>
		否<input type='radio' name='show_cover_pic' value="0"/>
		<br>
		内容:<textarea name="content" maxlength="2000">阿萨德撒</textarea><br>
		原地址:<input type="text" name="content_source_url" value="http://www.baidu.com"/><br>
		是否打开评论:
		是<input type='radio' name='need_open_comment' value="1" checked/>
		否<input type='radio' name='need_open_comment' value="0"/>
		<br>
		是否限制评论:
		是<input type='radio' name='only_fans_can_comment' value="1" checked/>
		否<input type='radio' name='only_fans_can_comment' value="0"/>
		<br>
		<input id="ulvi_btn" type="button" value="提交" />
	</form>

	<div id="uln_msg"></div>
	<!-- 开始列表 -->
	<div class="div_list"><!-- 每一行 -->
		<!-- 行里的列 -->
		<div class="div_list_check">批量</div>
		<div class="div_list_title">标题</div>
		<div class="div_list_digest">简介</div>
		<div class="div_list_media_id">media_id</div>
		<div class="div_list_check">评论</div>
		<div class="div_list_check">评论权限</div>
		<div class="div_list_submit">获取Media</div>
		<div class="div_list_clear">清除Media</div>
		<div class="div_list_delete">删除</div>
	</div>
	<div id="n_list"><!-- 列表 -->
	</div>
	<a id="batch_gn" href="">批量获取Media</a>
	<a id="batch_cn" href="">批量清除Media</a>
	<a id="batch_dn" href="">批量删除</a>
</body>
</html>
