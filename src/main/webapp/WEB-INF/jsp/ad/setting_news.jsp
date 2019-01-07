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
		var adBook;
		var adType;
		$("#ulvi_btn").click(function() {
			//提交表单
			$.post({
				url : ad + "addNews",
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
							$
									.post({
										url : ad + "getNewsList",
										data : {
											type : "news"
										},
										//成功后的方法
										success : function(result) {
											var _adVLD = result['adNewsList'];
											adVLD = {};
											var n_list_html = "";
											$
													.each(
															_adVLD,
															function(idx, i) {
																var _d = i['id'];
																adVLD[_d] = i;
																n_list_html += "<div class='div_list'><div class='div_list_check'>";
																n_list_html += "<input type='checkbox' name='ni_check' value='" + _d + "'/></div>";
																n_list_html += "<div class='div_list_title'>" + i['title'] + "</div>";
																n_list_html += "<div class='div_list_type'>" + i['type'] + "</div>";
																n_list_html += "<div class='div_list_type'>" + i['consume'] + "</div>";
																n_list_html += "<div class='div_list_type'>" + i['name_id'] + "</div>";
																n_list_html += "<div class='div_list_digest'>"
																		+ i['digest']
																		+ "</div>";
																n_list_html += "<div class='div_list_media_id'>"
																		+ i['media_id']
																		+ "</div>";
																n_list_html += "<div class='div_list_check'>"
																		+ (i['need_open_comment'] == 0 ? "关闭"
																				: "开启")
																		+ "</div>";
																n_list_html += "<div class='div_list_check'>"
																		+ (i['only_fans_can_comment'] == 0 ? "粉丝"
																				: "全部")
																		+ "</div>";
																n_list_html += "<div class='div_list_submit'><a id='gni' v='" + _d + "' href=''>获取Media</a></div>";
																n_list_html += "<div class='div_list_clear'><a id='cni' v='" + _d + "' href=''>清除Media</a></div>";
																n_list_html += "<div class='div_list_delete'><a id='dni' v='" + _d + "' href=''>删除</a></div>";
																n_list_html += "<div class='div_list_delete'><a id='dnpush' v='" + _d + "' href=''>推送</a></div>";
																n_list_html += "</div>";
															});
											$('#n_list').empty();
											$('#n_list').html(n_list_html);
										}
									});
						}
						$("#n_list")
								.click(
										function(event) {
											var target = $(event.target);
											var id = target.attr("id");
											var url = "";
											var data;
											if (undefined != id) {
												switch (id) {
												case "gni":
													url = ad + "getNewsToWe";
													var adV = adVLD[target
															.attr("v")];
													nis_command(
															url,
															JSON
																	.stringify([ serializeObject(adV) ]))
													break;
												case "cni":
													url = ad + "clearNewsToWe";
													var adV = adVLD[target
															.attr("v")];
													nis_command(
															url,
															JSON
																	.stringify([ serializeObject(adV) ]))
													break;
												case "dni":
													url = ad + "deleteNewsToWe";
													var adV = adVLD[target
															.attr("v")];
													nis_command(
															url,
															JSON
																	.stringify([ serializeObject(adV) ]))
													break;
												case "dnpush":
													url = ad + "pushNewsToWe";
													$.post({
														url : url,
														data : adVLD[target.attr("v")]['media_id'],
														contentType : "application/json",
														success : function(result) {
															alert(result)
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
						getNIList();

						//获取图文类型
						function getNTList() {
							//提交表单
							$.post({
								url : ad + "getNewsTypeList",
								//成功后的方法
								success : function(result) {
									var _adNTL = result['adNewsTypeList'];
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
							})
						}
						getNTList();
						$("#add_type_btn").click(function(event) {//增
							var url = ad + "addNewsType";
							$.post({
								url : url,
								data : {
									name : $("#type_name").val()
								},
								success : function(result) {
									getNTList();
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
									getNTList();
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
									getNTList();
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
					});
</script>
</head>
<body>
	<h1>图文</h1>
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
		</div>
	</div>
	<hr>
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
		</div>
	</div>
	<hr>
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
					<input type="text" name="title" id="title" value="我是标题" />
				</div>
			</div>
			<div class="div_content_block">
				<div class="div_content_key">封面素材ID:</div>
				<div class="div_content_value">
					<input type="text" name="thumb_media_id"
					value="xz7FUpQVD_jqdjkY2vb6IFhDAxwOsOe6S_rigdKb4WQ" />
				</div>
			</div>
			<div class="div_content_block">
				<div class="div_content_key">作者:</div>
				<div class="div_content_value">
					<input type="text" name="author" value="sa" />
				</div>
			</div>
			<div class="div_content_block">
				<div class="div_content_key">摘要(100字):</div>
				<div class="div_content_value">
					<textarea name="digest" maxlength="100">你好</textarea>
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
			</div>
		</form>
	</div>
	<div id="uln_msg"></div>
	<hr>
	<div class="div_content_block">
		<div class="div_content_block"><h2>已提交的图文列表</h2></div>
		<div class="div_list">
			<!-- 每一行 -->
			<!-- 行里的列 -->
			<div class="div_list_check">批量</div>
			<div class="div_list_title">标题</div>
			<div class="div_list_type">图文类型</div>
			<div class="div_list_type">阅读花费</div>
			<div class="div_list_type">书名ID</div>
			<div class="div_list_digest">简介</div>
			<div class="div_list_media_id">media_id</div>
			<div class="div_list_check">评论</div>
			<div class="div_list_check">评论权限</div>
			<div class="div_list_submit">获取Media</div>
			<div class="div_list_clear">清除Media</div>
			<div class="div_list_delete">删除</div>
			<div class="div_list_delete">推送</div>
		</div>
		<div id="n_list">
			<!-- 列表 -->
		</div>
	</div>
	<div class="div_content_block">
		<a id="batch_gn" href="">批量获取Media</a>
		<a id="batch_cn" href="">批量清除Media</a>
		<a id="batch_dn" href="">批量删除</a>
	</div>
</body>
</html>
