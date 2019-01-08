<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="initial-scale=1, width=device-width, maximum-scale=1, minimum-scale=1, user-scalable=no">
<title>welcome</title>
<script type="text/javascript">
$(document).ready(function() {
	$("#createAccessToken").click(create);
	$("#createMenu").click(create);
	$("#clearQuota").click(create);
	function create(event) {
		alert($(this).attr("id"))
		//提交表单
		$.post({
			url : "ad/" + $(this).attr("id"),
			//成功后的方法
			success : function(result) {
				alert(JSON.stringify(result))
				Object.keys(result).forEach(function(key){
				     console.log(key,result[key]);
				});
				$('#msg').empty();
				$('#msg').html(JSON.stringify(result))
			}
		});
		event.preventDefault();  // 阻止链接跳转
	}
})
</script>
</head>
<body>
	欢迎你 :${adUser.nickName}
	<form action="ad/exit">
		<input type="submit" value="退出">
	</form>
	<br>
	<a id="createAccessToken" href="">生成Access Token</a>
	<br>
	<a id="createMenu" href="">生成Menu</a>
	<br>
	<a id="clearQuota" href="">清除使用次数</a>
	<div stype="word-wrap:break-word" id="msg">
	这里是信息
	</div>
</body>
</html>
