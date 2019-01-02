<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>login</title>
<script type="text/javascript">
	/*
	 * 转化json视图
	 */
	 /*
	$(document).ready(function() {
		$("#commit").click(function() {
			//提交表单
			$.post({
				url : "ad/login",
				//将form数据序列化，传递给后台，则将数据以roleName=xxx&&note=xxx传递
				data : $("form").serialize(),
				//成功后的方法
				success : function(result) {
					//window.location.reload();//提交成功后
					//$('#content').remove();                          
					//$('#content').load(url + ' #content').fadeIn('slow'); // 加载新内容,url地址与该地址下的选择器之间要有空格,表示该url下的#container
				}
			});
		});
	});*/
</script>
</head>

<body>
	<%
	if (null != session.getAttribute("adUser")) {
	%>
		<script type="text/javascript">
		$(document).ready(function() {
			flipForPage("welcome");
		});
		</script>
	<%}%>
	<form id="form" action="ad/login">
		<table>
			<tr>
				<td>用户名</td>
				<td><input type="text" id="userName" name="userName" value="sa" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" id="passWord" name="passWord" value="521haiqi" /></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><input type="submit" id="commit" value="提交" /></td>
			</tr>
		</table>
	</form>
</body>
</html>