<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>사진 리스트</h1>
	<a href="/board/form">글쓰기</a>
	<ul>
	<c:forEach var="document" items="${boards}">
		<li>
		<c:if test="${document.filename != null}">
			<a href="/board/${document.id}"><img src="/images/${document.filename}" width="100" height="100" alt="image" /></a>
		</c:if>
		<p><a href="/board/${document.id}">${document.title}</a></p>
		<p><a href="/board/${document.id}">${document.contents}</p>
		</li>
	</c:forEach>
	</ul>
</body>
</html>