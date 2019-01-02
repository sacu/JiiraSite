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
	欢迎你 :${adUser.nickName}
	<form action="ad/exit">
		<input type="submit" value="退出">
	</form>
</body>
</html>
