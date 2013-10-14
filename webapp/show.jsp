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
<a href="/board">리스트</a>
<h1>sss${board.title}</h1>
<div>
<c:if test="${board.filename != null}">
<img src="/images/${board.filename}" width="200" height="200"/>
<br>
</c:if>

sss${board.contents}
<a href="./${id}/modify"><button class="modify">수정</button></a>
<a href="./${id}/delete"><button class="delete">삭제</button></a>
</div>
</body>
</html>