<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>login</title>
</head>

<body>
	<%
	if (null != session.getAttribute("adUser")) {
	%>
		<script type="text/javascript">
		$(document).ready(function() {
			//提交表单
			$.post({
				url : "ad/flip",
				data : {page:"welcome"},
				//成功后的方法
				success : function(result) {
					$('#content').empty()
					$('#content').html(result)
				}
			});
		});
		</script>
	<%}%>
	<form id="form" action="ad/login">
		<table>
			<tr>
				<td>用户名</td>
				<td><input type="text" id="userName" name="userName" value="" /></td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="password" id="passWord" name="passWord" value="" /></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><input type="submit" id="commit" value="提交" /></td>
			</tr>
		</table>
	</form>
</body>
</html>