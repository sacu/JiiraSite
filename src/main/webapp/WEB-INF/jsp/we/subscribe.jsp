<%@page import="org.jiira.utils.CommandCollection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
   		<title>哎呀,您还没有关注</title>
		<meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
        <link rel="stylesheet" type="text/css" href="https://res.wx.qq.com/open/libs/weui/0.4.1/weui.css">
		<link rel="stylesheet" href="../style/we.css?v=<%=CommandCollection.CSS_V %>" />
    </head>
    <body>
        
        <div class="weui_msg">
        	<div class="weui_icon_area">
        		<i class="weui_icon_info weui_icon_msg"></i>
        	</div>
       		<div class="weui_text_area">
        		<h4 class="weui_msg_title">
        		您还没有关注我呀？<br>
        		长按下方二维码，选择<font color="red">识别图中二维码</font>来关注我吧。<br>
        		</h4>
        	</div>
       		<div class="weui_text_area">
       			<img width="60%" src="../manager/resource/image/ewm.jpg">
       		</div>
       		<div class="weui_text_area">
       			<a href="ic?redirect=${subscribe_redirect}" class="weui-btn weui-btn_plain-primary">点我直接阅读</a>
        	</div>
        </div>
    </body>
</html>
