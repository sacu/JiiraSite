<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>thumb</title>
<script type="text/javascript">
	var ad = "";//提交服务器后是 ad/
	$(document).ready(function() {
		var adVLD;
		$("#ult_btn").click(function(event) {
			var formData = new FormData($("#uli_form")[0]);//[0]必须的
			$.ajax({//必须使用ajax
				url : ad + "addIVV",//地址是 ad/addNewsImage
				type: "POST",
				data : formData,
				processData: false,
                contentType: false,
				//成功后的方法
				success : function(result) {
					$('#ult_msg').empty();
					$('#ult_msg').html(JSON.stringify(result));
					getNIList();//更新列表
				}
			});
			event.preventDefault();  // 阻止链接跳转
		})
		function getNIList() {
			//提交表单
			$.post({
				url : ad + "getIVVList",
				data:{type:"thumb"},
				//成功后的方法
				success : function(result) {
					adVLD = result['adIVList'];
					var t_list_html = "";
					var aurl = '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_THUMB%>' + "/";
					$.each(adVLD, function(idx, i) {
						t_list_html += "<div class='n_image_element'>";
						t_list_html += "<div class='n_image_name'>";
						t_list_html += "<input type='checkbox' name='ni_check' value='" + idx + "'/>" + i['iv'];
						t_list_html += "</div>";
						t_list_html += "<div class='n_image_context'><img class='n_image_img' src='" + aurl + i['iv'] + "'></div>";
						if(i['media_id'] == null || i['media_id'].length == 0){
							t_list_html += "<div class='n_image_media'>[<a id='gni' v='" + idx + "' href=''>获取Media</a>]</div>";
						} else {
							t_list_html += "<div class='n_image_media'>[<a id='cni' v='" + idx + "' href=''>清除Media</a>]</div>";
						}
						t_list_html += "<div class='n_image_delete'>[<a id='dni' v='" + idx + "' href=''>删除</a>]</div>";
						t_list_html += "</div>";
					});
					$('#t_list').empty();
					$('#t_list').html(t_list_html);
				}
			});
		}
		$("#t_list").click(function(event) {
			var target = $(event.target);
			var id = target.attr("id");
			var url = "";
			var data;
			if(undefined != id){
				switch(id){
				case "gni":
					url = ad + "getIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['iv']], type:"thumb"}
					nis_command(url, data)
					break;
				case "cni":
					url = ad + "clearIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['iv']], type:"thumb", media_ids:[adV['media_id']]}
					nis_command(url, data)
					break;
				case "dni":
					url = ad + "deleteIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['iv']], type:"thumb", media_ids:[adV['media_id']]}
					nis_command(url, data)
					break;
				}
				event.preventDefault();  // 阻止链接跳转
			}
		})
		function nis_command(url, data){
			if(url.length > 0 && data['ivvs'].length > 0){
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
		$("#batch_gt").click(function(event) {
			var url = ad + "getIVVToWe";
			var data = {ivvs:[], type:"thumb"};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['iv'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_ct").click(function(event) {
			var url = ad + "clearIVVToWe";
			var data = {ivvs:[], type:"thumb", media_ids:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['iv'])
				data['media_ids'].push(adV['media_id'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_dt").click(function(event) {
			var url = ad + "deleteIVVToWe";
			var data = {ivvs:[], type:"thumb", media_ids:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['iv'])
				data['media_ids'].push(adV['media_id'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		getNIList();
	});
</script>
</head>
<body>
	<h1>缩略图</h1>
	<form id="uli_form">
		<input type="file" name="files" value="请选择上传的文件" accept="image/gif, image/jpeg"/><br>
		<input type="hidden" name="type" value="thumb"/>
		<input type="button" id="ult_btn" value="提交" />
	</form>

	<div id="ult_msg"></div>
	<div class="div_content_block">
		<a id="batch_gt" href="">批量获取Media</a>
		<a id="batch_ct" href="">批量清除Media</a>
		<a id="batch_dt" href="">批量删除</a>
	</div>
	<div class="n_context_list" id="t_list"><!-- 列表 -->
	</div>
</body>
</html>
