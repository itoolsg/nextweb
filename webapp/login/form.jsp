<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>연규 포토</title>
<link rel="stylesheet" type="text/css" href="/stylesheets/default.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/login.css" />
</head>
<body>
	<div class="container">
<div class="title">LOGIN</div>
		<div class="back"><a href="/board">리스트로 돌아가기</a></div>
		
		<form action="/user/login_check" method="post">
			<input class="id" type="text" name="userid" placeholder="username">
			<input class="ps" type="password" name="password" placeholder="password"> 
			<div class="bottom-action">
				<button class="submit btn2">SIGN IN</button>
				Not a Member? <a class="signup" href="/user/signup">Sign up</a>
			</div>
				
			</div>
		</form>
	</div>
</body>
</html>