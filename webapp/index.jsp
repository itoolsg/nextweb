<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.userid}">
			<a href="/user/logout">로그아웃하기</a>
		</c:when>
		<c:otherwise>
			<a href="/user/form">로그인하기</a>
		</c:otherwise>
	</c:choose>
	<br />
	<br />
	<a href="/board">리스트로 가기</a><br />
	<br />
	<a href="/board/form">글쓰기</a>

</body>
</html>