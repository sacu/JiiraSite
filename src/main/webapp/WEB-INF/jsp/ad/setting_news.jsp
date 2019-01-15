<%@page import="org.jiira.utils.CommandCollection"%>
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
	var ad = "";//提交服务器后是 ad/
	$(document).ready(function() {
		var adVLD;
		var adBook;
		var adType;
		$("#ulvi_btn").click(function() {
			//提交表单
			$.post({
				url : "addNews",
				data : $("#uln_form").serialize(),
				//成功后的方法
				success : function(result) {
					$('#uln_msg').empty();
					$('#uln_msg').html(JSON.stringify(result));
					getNIList();//更新列表
				}
			});
		})
		function getNIList() {
			//提交表单
			$.post({
				url : "getNewsList",
				//成功后的方法
				success : function(result) {
					adVLD = result['adNewsList'];
					var n_list_html = "";
					$.each(adVLD,function(idx, i) {
						n_list_html += "<div class='n_news_element'>";
						n_list_html += "<div class='n_news_name'>";
						n_list_html += "<input type='checkbox' name='ni_check' value='" + idx + "'/>" + i['title'];
						n_list_html += "</div>";
						n_list_html += "<div class='n_news_context'><a href='testNews?news_id="+ i['id'] +"' target='_blank'>";
						n_list_html += "<img class='n_news_img' src='" + i['thumb_id'] + "'></a></div>";
						//图文类型
						n_list_html += "<div class='n_news_type'>类型:" + adType[i['type']]['name'] + "</div>";
						//图书ID
						n_list_html += "<div class='n_news_type'>图书ID:" + i['name_id'] + "</div>";
						if(i['media_id'] == null || i['media_id'].length == 0){
							if(i['thumb_media_id'] == null || i['thumb_media_id'].length == 0){
								n_list_html += "<div class='n_news_media'>无Media</div>";
							} else {
								n_list_html += "<div class='n_news_media'>[<a id='gni' v='" + idx + "' href=''>获取Media</a>]</div>";
							}
						} else {
							n_list_html += "<div class='n_news_media'>[<a id='cni' v='" + idx + "' href=''>清除Media</a>]</div>";
							n_list_html += "<div class='n_news_push'>[<a id='dnpush' v='" + idx + "' href=''>推送</a>]</div>";
						}
						n_list_html += "<div class='n_news_delete'>[<a id='dni' v='" + idx + "' href=''>删除</a>]</div>";
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
			if (undefined != id) {
				switch (id) {
				case "gni":
					var adV = adVLD[target.attr("v")];
					nis_command("getNewsToWe",JSON.stringify([serializeObject(adV) ]))
					break;
				case "cni":
					var adV = adVLD[target.attr("v")];
					nis_command("clearNewsToWe",JSON.stringify([serializeObject(adV) ]))
					break;
				case "dni":
					var adV = adVLD[target.attr("v")];
					nis_command("deleteNewsToWe",JSON.stringify([serializeObject(adV) ]))
					break;
				case "dnpush":
					$.post({
						url : "pushNewsToWe",
						data : adVLD[target.attr("v")]['media_id'],
						contentType : "application/json",
						success : function(result) {
							alert(JSON.stringify(result))
						}
					});
					break;
				}
					
				event.preventDefault(); // 阻止链接跳转
			}
		})
						function nis_command(url, data) {
							if (url.length > 0) {//} && data['adNews'].length > 0){
								$.post({
									url : url,
									data : data,
									contentType : "application/json",
									success : function(result) {
										getNIList();
									}
								});
							}
						}
						$("#batch_gn")
								.click(
										function(event) {
											var url = ad + "getNewsToWe";
											var data = [];
											$
													.each(
															$('input:checkbox:checked'),
															function() {
																data
																		.push(serializeObject(adVLD[$(
																				this)
																				.val()]))
															});
											nis_command(url, JSON
													.stringify(data));
											event.preventDefault(); // 阻止链接跳转
										})
						$("#batch_cn")
								.click(
										function(event) {
											var url = ad + "clearNewsToWe";
											var data = [];
											$
													.each(
															$('input:checkbox:checked'),
															function() {
																data
																		.push(serializeObject(adVLD[$(
																				this)
																				.val()]))
															});
											nis_command(url, JSON
													.stringify(data));
											event.preventDefault(); // 阻止链接跳转
										})
						$("#batch_dn").click(function(event) {
							var url = ad + "deleteNewsToWe";
							var data = [];
							$.each($('input:checkbox:checked'),function() {
								console.info($(this).val())
								data.push(serializeObject(adVLD[$(this).val()]))
							});
							nis_command(url, JSON
									.stringify(data));
							event.preventDefault(); // 阻止链接跳转
						})

						//获取图文类型
						function getNTList(_adNTL) {
							adType = {};
							var type = $('#type');
							var etype = $('#etype');
							type.empty();
							etype.empty();
							$.each(_adNTL, function(idx, i) {
								adType[i['id']] = i;
								option = "<option value='" + i['id'] + "'>" + i['name'] + "</option>";
								type.append(option);
								etype.append(option);
							});
						}
						getNTList(<%=CommandCollection.GetNewsTypeJson()%>);
						$("#add_type_btn").click(function(event) {//增
							var url = ad + "addNewsType";
							$.post({
								url : url,
								data : {
									name : $("#type_name").val()
								},
								success : function(result) {
									if(result.hasOwnProperty("json")){
										getNTList(result['json']);
									} else {
										alert("添加失败");
									}
								}
							});
							event.preventDefault(); // 阻止链接跳转
						})
						$("#del_type_btn").click(function(event) {//删
							var url = ad + "delNewsType";
							$.post({
								url : url,
								data : {
									id : $("#etype").val()
								},
								success : function(result) {
									if(result.hasOwnProperty("json")){
										getNTList(result['json']);
									} else {
										alert("删除失败");
									}
								}
							});
							event.preventDefault(); // 阻止链接跳转
						})
						$("#re_type_btn").click(function(event) {//改
							var url = ad + "reNewsType";
							$.post({
								url : url,
								data : {
									id : $("#etype").val(),
									name : $("#type_name").val()
								},
								success : function(result) {
									if(result.hasOwnProperty("json")){
										getNTList(result['json']);
									} else {
										alert("修改失败");
									}
								}
							});
							event.preventDefault(); // 阻止链接跳转
						})
						//获取图文名称(书名)
						function getBookList() {
							//提交表单
							$.post({
								url : ad + "getNewsNameList",
								//成功后的方法
								success : function(result) {
									adBook = {};
									var _adNNL= result['adNewsNameList'];
									var name_id = $('#name_id');
									var ename_id = $('#ename_id');
									var option;
									name_id.empty();
									ename_id.empty();
									$.each(_adNNL, function(idx, i) {
										adBook[i['id']] = i;
										option = "<option value='" + i['id'] + "'>" + i['name'] + "</option>";
										name_id.append(option);
										ename_id.append(option);
									});
								}
							})
						}
						getBookList();
						$("#add_name_btn").click(function(event) {//增
							var url = ad + "addNewsName";
							$.post({
								url : url,
								data : {
									name : $("#book_name").val(),
									author : $("#book_author").val(),
									digest : $("#book_digest").val(),
								},
								success : function(result) {
									getBookList();
								}
							});
							event.preventDefault(); // 阻止链接跳转
						})
						$("#del_name_btn").click(function(event) {//删
							var url = ad + "delNewsName";
							$.post({
								url : url,
								data : {
									id : $("#ename_id").val()
								},
								success : function(result) {
									getBookList();
								}
							});
							event.preventDefault(); // 阻止链接跳转
						})
						$("#re_name_btn").click(function(event) {//改
							var url = ad + "reNewsName";
							$.post({
								url : url,
								data : {
									id: $("#ename_id").val(),
									name : $("#book_name").val(),
									author : $("#book_author").val(),
									digest : $("#book_digest").val(),
								},
								success : function(result) {
									getBookList();
								}
							});
							event.preventDefault(); // 阻止链接跳转
						})
						$("#ename_id").change(function(){
							var id = $("#ename_id").val();
							$("#book_name").val(adBook[id]['name']);
							$("#book_author").attr("value", adBook[id]['author']);
							$("#book_digest").val(adBook[id]['digest']);
						})
						$("#etype").change(function(){
							$("#type_name").val(adType[$("#etype").val()]['name']);
						})
						getNIList();
					});
	$("#edbook").click(function(event) {
		$("#edbooklayer").css('display','block'); 
		event.preventDefault();  // 阻止链接跳转
	});
	$("#cls_book_btn").click(function(event) {
		$("#edbooklayer").css('display','none'); 
		event.preventDefault();  // 阻止链接跳转
	})
	$("#edtype").click(function(event) {
		$("#edtypelayer").css('display','block'); 
		event.preventDefault();  // 阻止链接跳转
	});
	$("#cls_type_btn").click(function(event) {
		$("#edtypelayer").css('display','none'); 
		event.preventDefault();  // 阻止链接跳转
	})
	$("#upnews").click(function(event) {
		$("#upnewslayer").css('display','block'); 
		event.preventDefault();  // 阻止链接跳转
	});
	$("#cls_news_btn").click(function(event) {
		$("#upnewslayer").css('display','none'); 
		event.preventDefault();  // 阻止链接跳转
	})
	var total;
	$("#select_image").click(function(event) {//显示选择图片
		$("#upnewsimagelayer").css('display','block'); 
		total = {};
		$.post({
			url : "getIVAll",
			//成功后的方法
			success : function(result) {
				total = result['adIVList'];
				var i_list_html = "";
				var iurl = '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_IMAGE%>' + "/";
				var turl = '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_THUMB%>' + "/";
				$.each(total, function(idx, i) {
					i_list_html += "<div class='n_image_element'>";
					i_list_html += "<div class='n_image_name'>";
					i_list_html += i['iv'];
					i_list_html += "</div>";
					var u = i['type']=="image"?iurl + i['iv']:turl + i['iv'];
					i_list_html += "<div class='n_image_context'><img v='" + idx + "' class='n_image_img' src='" + u + "'></div>";
					i_list_html += "</div>";
				});
				$('#cnimagelayer').empty();
				$('#cnimagelayer').html(i_list_html);
			}
		});
		event.preventDefault();  // 阻止链接跳转
	})
	$("#cnimagelayer").click(function(event) {//选择图片
		var target = $(event.target);
		if(target.prop("tagName").toLowerCase() == "img"){
			var iurl = '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_IMAGE%>' + "/";
			var turl = '<%=CommandCollection.RES_NAME + CommandCollection.MESSAGE_THUMB%>' + "/";
			var current = total[target.attr("v")];
			var u = current['type']=="image"?iurl:turl;
			$("#thumb_id").val(u + current['iv']);
			$("#thumb_media_id").val(current['media_id']);
			$("#upnewsimagelayer").css('display','none'); 
		}
		event.preventDefault();  // 阻止链接跳转
	})
	
</script>
</head>
<body>
	<h1>图文</h1>
	<a href="" id="edbook">编辑图书</a>
	<a href="" id="edtype">编辑类型</a>
	<a href="" id="upnews">上传图文</a>
	<div id="uln_msg"></div>
	<div class="div_content_block">
		<a id="batch_gn" href="">批量获取Media</a>
		<a id="batch_cn" href="">批量清除Media</a>
		<a id="batch_dn" href="">批量删除</a>
	</div>
	<hr>
	<div class="div_content_block">
		<div class="div_content_block"><h2>已提交的图文列表</h2></div>
		<div class="n_context_list" id="n_list">
			<!-- 列表 -->
		</div>
	</div>
	<div class="n_float_layer" id="edbooklayer">
		<div class="n_float_news_book">
			<div class="div_content_block">
				<div class="div_content_block"><h2>编辑图书</h2></div>
				<div class="div_content_block">
					<div class="div_content_key">选择</div>
					<div class="div_content_value">
						<select name="ename_id" id="ename_id"></select>
					</div>
				</div>
				<div class="div_content_block">
					<div class="div_content_key">书名</div>
					<div class="div_content_value">
						<input type="text" id="book_name">
					</div>
				</div>
				<div class="div_content_block">
					<div class="div_content_key">作者</div>
					<div class="div_content_value">
						<input type="text" id="book_author">
					</div>
				</div>
				<div class="div_content_block">
					<div class="div_content_key">简介(100字)</div>
					<div class="div_content_value">
						<textarea id="book_digest" maxlength="100"></textarea>
					</div>
				</div>
				<div class="div_content_block">
					<a id="add_name_btn" href="">添加</a>
					<a id="del_name_btn" href="">删除</a>
					<a id="re_name_btn" href="">修改</a>
					<a id="cls_book_btn" href="">关闭</a>
				</div>
			</div>
		</div>
	</div>
	<div class="n_float_layer" id="edtypelayer">
		<div class="n_float_news_type">
			<div class="div_content_block">
				<div class="div_content_block"><h2>类型编辑</h2></div>
				<div class="div_content_block">
					<div class="div_content_key">选择</div>
					<div class="div_content_value">
						<select name="etype" id="etype"></select>
					</div>
				</div>
				<div class="div_content_block">
					<div class="div_content_key">类型名称</div>
					<div class="div_content_value">
						<input type="text" name="type_name" id="type_name">
					</div>
				</div>
				<div class="div_content_block">
					<a id="add_type_btn" href="">添加</a>
					<a id="del_type_btn" href="">删除</a>
					<a id="re_type_btn" href="">修改</a>
					<a id="cls_type_btn" href="">关闭</a>
				</div>
			</div>
		</div>
	</div>
	<div class="n_float_news_layer" id="upnewsimagelayer">
		<div class="n_float_news_image" id="cnimagelayer">
		</div>
	</div>
	<div class="n_float_layer" id="upnewslayer">
		<div class="n_float_news">
			<div class="div_content_block">
				<div class="div_content_block"><h2>发布图文</h2></div>
				<form id="uln_form" method="post">
					<div class="div_content_block">
						<div class="div_content_key">文章类型:</div>
						<div class="div_content_value">
							<select name="type" id="type"></select>
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">图书ID:</div>
						<div class="div_content_value">
							<select name="name_id" id="name_id"></select>
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">阅读花费:</div>
						<div class="div_content_value">
							<input type="text" name="consume" id="consume" value="0">
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">标题:</div>
						<div class="div_content_value">
							<input type="text" name="title" id="title"/>
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">封面素材ID:</div>
						<div class="div_content_value">
							<input type="text" name="thumb_id" id="thumb_id" placeholder="右侧选择图片后自动填充"/><br>
							<input type="text" name="thumb_media_id" id="thumb_media_id" placeholder="右侧选择图片后自动填充"/>
							<a href="" id="select_image">选择图片</a>
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">作者:</div>
						<div class="div_content_value">
							<input type="text" name="author" />
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">摘要(100字):</div>
						<div class="div_content_value">
							<textarea name="digest" maxlength="100"></textarea>
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">是否显示封面:</div>
						<div class="div_content_value">
							是<input type='radio' name='show_cover_pic' value="1" checked/>
							 否<input type='radio' name='show_cover_pic' value="0" />
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">内容(2000字):</div>
						<div class="div_content_value">
							<textarea name="content" maxlength="2000"></textarea>
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">是否打开评论(腾讯有BUG):</div>
						<div class="div_content_value">
							是<input type='radio' name='need_open_comment' value="1" />
							 否<input type='radio' name='need_open_comment' value="0" checked/>
						</div>
					</div>
					<div class="div_content_block">
						<div class="div_content_key">是否限制评论(腾讯有BUG):</div>
						<div class="div_content_value">
							是<input type='radio' name='only_fans_can_comment' value="1" />
							 否<input type='radio' name='only_fans_can_comment' value="0" checked/>
						</div>
					</div>
					<div class="div_content_block">
						 <input id="ulvi_btn" type="button" value="提交" />
						 <input id="cls_news_btn" type="button" value="关闭" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
