<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>voice</title>
<script type="text/javascript">
	var ad = "ad/";//提交服务器后是 ad/
	$(document).ready(function() {
		$("#ulvo_btn").click(function(event) {
			var formData = new FormData($("#uli_form")[0]);//[0]必须的
			$.ajax({//必须使用ajax
				url : ad + "addIVV",//地址是 ad/addNewsImage
				type: "POST",
				data : formData,
				processData: false,
                contentType: false,
				//成功后的方法
				success : function(result) {
					$('#ulvo_msg').empty();
					$('#ulvo_msg').html(JSON.stringify(result));
					getNIList();//更新列表
				}
			});
			event.preventDefault();  // 阻止链接跳转
		})
		function getNIList() {
			//提交表单
			$.post({
				url : ad + "getIVVList",
				data:{type:"voice"},
				//成功后的方法
				success : function(result) {
					var vo_list = result['adVoiceList'];
					var vo_list_html = "";
					var ni, nm;
					$.each(vo_list, function(idx, i) {
						ni = i['voice'];
						nm = i['media_id']
						vo_list_html += "<div class='div_list'><div class='div_list_check'>";
						vo_list_html += "<input type='checkbox' name='ni_check' value='" + ni + "'/></div>";
						vo_list_html += "<div class='div_list_key'>" + ni + "</div>";
						vo_list_html += "<div class='div_list_media_id'>" + i['media_id'] + "</div>";
						vo_list_html += "<div class='div_list_submit'><a id='gni' v='" + ni + "' href=''>获取URL</a></div>";
						vo_list_html += "<div class='div_list_clear'><a id='cni' v='" + ni+"|"+nm + "' href=''>清除URL</a></div>";
						vo_list_html += "<div class='div_list_delete'><a id='dni' v='" + ni+"|"+nm + "' href=''>删除</a></div>";
						vo_list_html += "</div>";
					});
					$('#vo_list').empty();
					$('#vo_list').html(vo_list_html);
				}
			});
		}
		$("#vo_list").click(function(event) {
			var target = $(event.target);
			var id = target.attr("id");
			var url = "";
			var data;
			if(undefined != id){
				switch(id){
				case "gni":
					url = ad + "getIVVToWe";
					data={ivvs:[target.attr("v")], type:"voice"}
					nis_command(url, data)
					break;
				case "cni":
					url = ad + "clearIVVToWe";
					data={ivvs:[target.attr("v")], type:"voice"}
					nis_command(url, data)
					break;
				case "dni":
					url = ad + "deleteIVVToWe";
					data={ivvs:[target.attr("v")], type:"voice"}
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
		$("#batch_gvo").click(function(event) {
			var url = ad + "getIVVToWe";
			var data = {ivvs:[]};
			$.each($('input:checkbox:checked'),function(){
				data['ivvs'].push($(this).val())
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_cvo").click(function(event) {
			var url = ad + "clearIVVToWe";
			var data = {ivvs:[]};
			$.each($('input:checkbox:checked'),function(){
				data['ivvs'].push($(this).val())
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_dvo").click(function(event) {
			var url = ad + "deleteIVVToWe";
			var data = {ivvs:[]};
			$.each($('input:checkbox:checked'),function(){
				data['ivvs'].push($(this).val())
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		getNIList();
	});
</script>
</head>
<body>
	<h1>语音</h1>
	<form id="uli_form">
		<input type="file" name="files" value="请选择上传的文件" /><br>
		<input type="file" name="files" value="请选择上传的文件" /><br>
		<input type="file" name="files" value="请选择上传的文件" /><br>
		<input type="file" name="files" value="请选择上传的文件" /><br>
		<input type="hidden" name="type" value="voice"/>
		<input type="button" id="ulvo_btn" value="提交" />
	</form>

	<div id="ulvo_msg"></div>
	<!-- 开始列表 -->
	<div class="div_list"><!-- 每一行 -->
		<!-- 行里的列 -->
		<div class="div_list_check">批量</div>
		<div class="div_list_key">名称</div>
		<div class="div_list_media_id">media_id</div>
		<div class="div_list_submit">获取URL</div>
		<div class="div_list_clear">清除URL</div>
		<div class="div_list_delete">删除</div>
	</div>
	<div id="vo_list"><!-- 列表 -->
	</div>
	<a id="batch_gvo" href="">批量获取URL</a>
	<a id="batch_cvo" href="">批量清除URL</a>
	<a id="batch_dvo" href="">批量删除</a>
</body>
</html>
