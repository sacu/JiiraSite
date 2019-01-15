<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>video</title>
<script type="text/javascript">
	var ad = "";//提交服务器后是 ad/
	$(document).ready(function() {
		var adVLD;
		$("#ulvi_btn").click(function(event) {
			$("#uplayer").css('display','none'); 
			var formData = new FormData($("#ulvi_form")[0]);//[0]必须的
			$.ajax({//必须使用ajax
				url : ad + "addIVV",//地址是 ad/addNewsImage
				type: "POST",
				data : formData,
				processData: false,
                contentType: false,
				//成功后的方法
				success : function(result) {
					$('#ulvi_msg').empty();
					$('#ulvi_msg').html(JSON.stringify(result));
					getNIList();//更新列表
				}
			});
			event.preventDefault();  // 阻止链接跳转
		})
		function getNIList() {
			//提交表单
			$.post({
				url : ad + "getIVVList",
				data:{type:"video"},
				//成功后的方法
				success : function(result) {
					adVLD = result['adVideoList'];
					var vi_list_html = "";
					var aurl = '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_VIDEO%>' + "/";
					$.each(adVLD, function(idx, i) {
						vi_list_html += "<div class='n_video_element'>";
						vi_list_html += "<div class='n_video_name'>";
						vi_list_html += "<input type='checkbox' name='ni_check' value='" + idx + "'/>" + i['title'];
						vi_list_html += "</div>";
						vi_list_html += "<div class='n_video_context'><video class='n_video_video' src='" + aurl + i['video'] + "' controls='controls'></div>";
						if(i['media_id'] == null || i['media_id'].length == 0){
							vi_list_html += "<div class='n_video_media'>[<a id='gni' v='" + idx + "' href=''>获取Media</a>]</div>";
						} else {
							vi_list_html += "<div class='n_video_media'>[<a id='cni' v='" + idx + "' href=''>清除Media</a>]</div>";
						}
						vi_list_html += "<div class='n_video_delete'>[<a id='dni' v='" + idx + "' href=''>删除</a>]</div>";
						vi_list_html += "</div>";
					});
					$('#vi_list').empty();
					$('#vi_list').html(vi_list_html);
				}
			});
		}
		$("#vi_list").click(function(event) {
			var target = $(event.target);
			var id = target.attr("id");
			var url = "";
			var data;
			if(undefined != id){
				switch(id){
				case "gni":
					url = ad + "getIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['video']], type:"video", titles:[adV['title']], introductions:[adV['introduction']]}
					nis_command(url, data)
					break;
				case "cni":
					url = ad + "clearIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['video']], type:"video", media_ids:[adV['media_id']]}
					nis_command(url, data)
					break;
				case "dni":
					url = ad + "deleteIVVToWe";
					var adV = adVLD[target.attr("v")];
					data={ivvs:[adV['video']], type:"video", media_ids:[adV['media_id']]}
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
		$("#batch_gvi").click(function(event) {
			var url = ad + "getIVVToWe";
			var data = {ivvs:[], type:"video", titles:[], introductions:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['video'])
				data['titles'].push(adV['title'])
				data['introductions'].push(adV['introduction'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_cvi").click(function(event) {
			var url = ad + "clearIVVToWe";
			var data = {ivvs:[], type:"video", media_ids:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['video'])
				data['media_ids'].push(adV['media_id'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		$("#batch_dvi").click(function(event) {
			var url = ad + "deleteIVVToWe";
			var data = {ivvs:[], type:"video", media_ids:[]};
			$.each($('input:checkbox:checked'),function(){
				var adV = adVLD[$(this).val()];
				data['ivvs'].push(adV['video'])
				data['media_ids'].push(adV['media_id'])
            });
			nis_command(url, data);
			event.preventDefault();  // 阻止链接跳转
		})
		getNIList();
		$("#upvideo").click(function(event) {
			$("#uplayer").css('display','block'); 
			event.preventDefault();  // 阻止链接跳转
		});
		$("#cls_btn").click(function(event) {
			$("#uplayer").css('display','none'); 
			event.preventDefault();  // 阻止链接跳转
		})
	});
</script>
</head>
<body>
	<h1>视频</h1>
	<a href="" id="upvideo">上传视频</a>
	<div id="ulvi_msg"></div>
	<div class="div_content_block">
		<a id="batch_gvi" href="">批量获取Media</a>
		<a id="batch_cvi" href="">批量清除Media</a>
		<a id="batch_dvi" href="">批量删除</a>
	</div>
	<!-- 开始列表 -->
	<div class="n_context_list" id="vi_list"><!-- 列表 -->
	</div>
	<div class="n_float_layer" id="uplayer">
		<div class=n_float_video>
			<div class="div_content_block"><h2>上传视频</h2></div>
			<form id="ulvi_form">
				<div class="div_content_block">
					<div class="div_content_key">标题:</div>
					<div class="div_content_value"><input type="text" name="title" value="我是标题"/></div>
				</div>
				<div class="div_content_block">
					<div class="div_content_key">简介:</div>
					<div class="div_content_value"><textarea name="introduction" maxlength="200">这个文章不错的</textarea></div>
				</div>
				<div class="div_content_block">
					<input type="file" name="files" value="请选择上传的文件" accept="video/*"/>
				</div>
				<div class="div_content_block">
					<input type="hidden" name="type" value="video"/>
					<input type="button" id="ulvi_btn" value="提交" />
					<input type="button" id="cls_btn" value="关闭" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>
