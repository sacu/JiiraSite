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
					$.each(adVLD, function(idx, i) {
						ni_list_html += "<div class='div_list'><div class='div_list_check'>";
						ni_list_html += "<input type='checkbox' name='ni_check' value='" + idx + "'/></div>";
						ni_list_html += "<div class='div_list_key'>" + i['newsImage'] + "</div>";
						ni_list_html += "<div class='div_list_value'>" + i['url'] + "</div>";
						ni_list_html += "<div class='div_list_submit'><a id='gni' v='" + idx + "' href=''>获取URL</a></div>";
						ni_list_html += "<div class='div_list_clear'><a id='cni' v='" + idx + "' href=''>清除URL</a></div>";
						ni_list_html += "<div class='div_list_delete'><a id='dni' v='" + idx + "' href=''>删除</a></div>";
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
	bmp/png/jpeg/jpg/gif
		<input type="file" name="files" value="请选择上传的文件" accept="image/gif, image/jpeg"/><br>
		<input type="file" name="files" value="请选择上传的文件" accept="image/gif, image/jpeg"/><br>
		<input type="file" name="files" value="请选择上传的文件" accept="image/gif, image/jpeg"/><br>
		<input type="file" name="files" value="请选择上传的文件" accept="image/gif, image/jpeg"/><br>
		<input type="button" id="ulni_btn" value="提交" />
	</form>

	<div id="ulni_msg"></div>
	<!-- 开始列表 -->
	<div class="div_list"><!-- 每一行 -->
		<!-- 行里的列 -->
		<div class="div_list_check">批量</div>
		<div class="div_list_key">名称</div>
		<div class="div_list_value">URL</div>
		<div class="div_list_submit">获取URL</div>
		<div class="div_list_clear">清除URL</div>
		<div class="div_list_delete">删除</div>
	</div>
	<div id="ni_list"><!-- 列表 -->
	</div>
	<a id="batch_gni" href="">批量获取URL</a>
	<a id="batch_cni" href="">批量清除URL</a>
	<a id="batch_dni" href="">批量删除</a>
</body>
</html>
