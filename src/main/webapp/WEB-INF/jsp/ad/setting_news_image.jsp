<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>setting_news_image</title>
<script type="text/javascript">
	var ad = "";//提交服务器后是 ad/
	$(document).ready(function() {
		var adVLD;
		$("#ulni_btn").click(function(event) {
			var formData = new FormData($("#ulni_form")[0]);//[0]必须的
			$.ajax({//必须使用ajax
				url : ad + "addNewsImage",//地址是 ad/addNewsImage
				type: "POST",
				data : formData,
				processData: false,
                contentType: false,
				//成功后的方法
				success : function(result) {
					$('#ulni_msg').empty();
					$('#ulni_msg').html(JSON.stringify(result));
					getNIList();//更新列表
				}
			});
			event.preventDefault();  // 阻止链接跳转
		})
		function getNIList() {
			//提交表单
			$.post({
				url : ad + "getNewsImageList",
				//成功后的方法
				success : function(result) {
					adVLD = result['adNewsImageList'];
					var ni_list_html = "";
					var aurl = '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_NEWS_IMAGE%>' + "/";
					$.each(adVLD, function(idx, i) {
						
						
						ni_list_html += "<div class='n_image_element'>";
						ni_list_html += "<div class='n_image_name'>";
						ni_list_html += "<input type='checkbox' name='ni_check' value='" + idx + "'/>" + i['newsImage'];
						ni_list_html += "</div>";
						ni_list_html += "<div class='n_image_context'><img class='n_image_img' src='" + aurl + i['newsImage'] + "'></div>";
						if(i['url'] == null || i['url'].length == 0){
							ni_list_html += "<div class='n_image_media'>[<a id='gni' v='" + idx + "' href=''>获取URL</a>]</div>";
						} else {
							ni_list_html += "<div class='n_image_media'>[<a id='cni' v='" + idx + "' href=''>清除URL</a>]</div>";
						}
						ni_list_html += "<div class='n_image_delete'>[<a id='dni' v='" + idx + "' href=''>删除</a>]</div>";
						ni_list_html += "</div>";
					});
					$('#ni_list').empty();
					$('#ni_list').html(ni_list_html);
				}
			});
		}
		$("#ni_list").click(function(event) {
			var target = $(event.target);
			var id = target.attr("id");
			var url = "";
			var data;
			if(undefined != id){
				switch(id){
				case "gni":
					url = ad + "getNewsImageToWe";
					var adV = adVLD[target.attr("v")];
					data={newsImages:[adV['newsImage']]}
					nis_command(url, data)
					break;
				case "cni":
					url = ad + "clearNewsImageToWe";
					var adV = adVLD[target.attr("v")];
					data={newsImages:[adV['newsImage']]}
					nis_command(url, data)
					break;
				case "dni":
					url = ad + "deleteNewsImageToWe";
					var adV = adVLD[target.attr("v")];
					data={newsImages:[adV['newsImage']]}
					nis_command(url, data)
					break;
				}
				event.preventDefault();  // 阻止链接跳转
			}
		})
		function nis_command(url, data){
			if(url.length > 0 && data['newsImages'].length > 0){
				$.post({
					url : url,
					data : data,
					//成功后的方法
					success : function(result) {
						getNIList();
					}
				});
			}
		}
		$("#batch_gni").click(function(event) {
			var url = ad + "getNewsImageToWe";
			var data = {newsImages:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['newsImages'].push(adV['newsImage'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_cni").click(function(event) {
			var url = ad + "clearNewsImageToWe";
			var data = {newsImages:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['newsImages'].push(adV['newsImage'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_dni").click(function(event) {
			var url = ad + "deleteNewsImageToWe";
			var data = {newsImages:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['newsImages'].push(adV['newsImage'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		getNIList();
	});
</script>
</head>
<body>
	<h1>图文内图片</h1>
	<form id="ulni_form">
		<input type="file" name="files" value="请选择上传的文件" accept="image/gif, image/jpeg"/><br>
		<input type="button" id="ulni_btn" value="提交" />
	</form>
	<div class="div_content_block">
		<a id="batch_gni" href="">批量获取URL</a>
		<a id="batch_cni" href="">批量清除URL</a>
		<a id="batch_dni" href="">批量删除</a>
	</div>
	<div id="ulni_msg"></div>
	<div class="n_context_list" id="ni_list"><!-- 列表 -->
	</div>
</body>
</html>
