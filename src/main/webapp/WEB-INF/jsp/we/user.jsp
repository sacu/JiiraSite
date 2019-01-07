<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
	<title></title>
</head>
<body>
	<div class="div_content_block">
		<div class="div_content_block"><h2>账户信息</h2></div>
		<div class="div_content_block">
			<div class="div_content_key">昵称:</div>
			<div class="div_content_value">${weUser.nickname}</div>
		</div>
		<div class="div_content_block">
			<div class="div_content_key">头像:</div>
			<div class="div_content_value"><img src="${weUser.headimgurl}"/></div>
		</div>
		<div class="div_content_block">
			<div class="div_content_key">性别:</div>
			<div class="div_content_value">${weUser.sex}</div>
		</div>
		<div class="div_content_block">
			<div class="div_content_key">代金券:</div>
			<div class="div_content_value">${weUser.vouchers}</div>
		</div>
		<div class="div_content_block">
			<div class="div_content_key">国家:</div>
			<div class="div_content_value">${weUser.country}</div>
		</div>
		<div class="div_content_block">
			<div class="div_content_key">省:</div>
			<div class="div_content_value">${weUser.province}</div>
		</div>
		<div class="div_content_block">
			<div class="div_content_key">市:</div>
			<div class="div_content_value">${weUser.city}</div>
		</div>
		<div class="div_content_block">
			<div class="div_content_key">创建时间:</div>
			<div class="div_content_value">${weUser.jointime}</div>
		</div>
	</div>
</body>
</html>