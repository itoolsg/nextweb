<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>YK :: ${error.desc}</title>

<link rel="stylesheet" type="text/css" href="/stylesheets/default.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/error.css" />
<script src="/javascripts/day.js" type="text/javascript"></script>
<script src="/javascripts/default.js" type="text/javascript"></script>
<script>
window.onload = function() {
	var btn = $("class:ok");
	if(btn.attr("link") == "BACK")
		btn.text("뒤로 가기");
	else
		btn.text("이동 하기");
	
	btn.click(function(e) {
		e.preventDefault();
		var link = $(this).attr("link");
		if(link == "BACK")
			history.go(-1);
		else
			location.href = link;
		
		return false;
	});	
};
</script>
</head>
<body>
<img src="/img/bg_lowClouds.png" class="cloud c1"/>
<img src="/img/bg_lowClouds.png" class="cloud c2"/>
<div class="container">
	<div class="err_iocn"><img src="/img/error.png" /></div>
	<div class="message">${error.desc}</div>
	<a href="#" class="btn1 ok" link="${error.link}">이동 or 뒤로 가</a>
	
</div>

</body>
</html>