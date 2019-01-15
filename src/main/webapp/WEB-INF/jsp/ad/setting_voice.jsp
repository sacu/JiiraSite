<%@page import="org.jiira.utils.CommandCollection"%>
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
	var ad = "";//提交服务器后是 ad/
	$(document).ready(function() {
		var adVLD;
		$("#ulvo_btn").click(function(event) {
			var formData = new FormData($("#ulvo_form")[0]);//[0]必须的
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
					adVLD = result['adVoiceList'];
					var vo_list_html = "";
					var aurl = '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_VOICE%>' + "/";
					$.each(adVLD, function(idx, i) {
						vo_list_html += "<div class='n_voice_element'>";
						vo_list_html += "<div class='n_voice_name'>";
						vo_list_html += "<input type='checkbox' name='ni_check' value='" + idx + "'/>" + i['voice'];
						vo_list_html += "</div>";
						vo_list_html += "<div class='n_voice_context'>";
						vo_list_html += "<audio id='" + idx + "' src='" + aurl+ i['voice'] + "'></audio>";
						vo_list_html += "<a id='vic' v='#" + idx + "' href=''>播放</a>";
						vo_list_html += "</div>";
						if(i['media_id'] == null || i['media_id'].length == 0){
							vo_list_html += "<div class='n_voice_media'>[<a id='gni' v='" + idx + "' href=''>获取Media</a>]</div>";
						} else {
							vo_list_html += "<div class='n_voice_media'>[<a id='cni' v='" + idx + "' href=''>清除Media</a>]</div>";
						}
						vo_list_html += "<div class='n_voice_delete'>[<a id='dni' v='" + idx + "' href=''>删除</a>]</div>";
						vo_list_html += "</div>";
					});
					$('#vo_list').empty();
					$('#vo_list').html(vo_list_html);
				}
			});
		}
		var cvic = undefined;
		$("#vo_list").click(function(event) {
			var target = $(event.target);
			var id = target.attr("id");
			var url = "";
			var data;
			if(undefined != id){
				switch(id){
				case "gni":
					url = ad + "getIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['voice']], type:"voice"}
					nis_command(url, data)
					break;
				case "cni":
					url = ad + "clearIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['voice']], type:"voice", media_ids:[adV['media_id']]}
					nis_command(url, data)
					break;
				case "dni":
					url = ad + "deleteIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['voice']], type:"voice", media_ids:[adV['media_id']]}
					nis_command(url, data)
					break;
				case "vic":
					var vicp;
					if(cvic != undefined && target.attr("v") == cvic.attr("v")){
						vicp = $(cvic.attr("v"))[0];
						$(cvic.attr("v")).unbind();
						vicp.pause();
						cvic.html("播放");
					}
					if(cvic == undefined || target.attr("v") != cvic.attr("v")){
						cvic = target;
						vicp = $(cvic.attr("v"))[0];
						cvic.html("暂停");
						vicp.play();
						$(cvic.attr("v")).bind('ended',function () {
							cvic.html("播放");
							$(cvic.attr("v")).unbind();
							vicp.pause();
						});
					} else {
						cvic = undefined;
					}
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
			var data = {ivvs:[], type:"voice"};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['voice'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_cvo").click(function(event) {
			var url = ad + "clearIVVToWe";
			var data = {ivvs:[], type:"voice", media_ids:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['voice'])
				data['media_ids'].push(adV['media_id'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_dvo").click(function(event) {
			var url = ad + "deleteIVVToWe";
			var data = {ivvs:[], type:"voice", media_ids:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['voice'])
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
	<h1>语音</h1>
	<div class="div_content_block">
		<form id="ulvo_form">
			<input type="file" name="files" value="请选择上传的文件" accept="audio/*"/>
<!-- 			<input type="file" name="files" value="请选择上传的文件" accept="audio/*"/><br> -->
<!-- 			<input type="file" name="files" value="请选择上传的文件" accept="audio/*"/><br> -->
<!-- 			<input type="file" name="files" value="请选择上传的文件" accept="audio/*"/><br> -->
			<input type="hidden" name="type" value="voice"/>
			<input type="button" id="ulvo_btn" value="提交" />
		</form>
	</div>
	<div id="ulvo_msg"></div>
	<div class="div_content_block">
		<a id="batch_gvo" href="">批量获取Media</a>
		<a id="batch_cvo" href="">批量清除Media</a>
		<a id="batch_dvo" href="">批量删除</a>
	</div>
	<!-- 开始列表 -->
	<div class="n_context_list" id="vo_list"><!-- 列表 -->
	</div>
</body>
</html>
