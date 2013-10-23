<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/stylesheets/default.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/list.css" />
</head>
<body>
	<div class="container">
			<c:choose>
				<c:when test="${not empty sessionScope.userid}">
					<a href="/user/logout">로그아웃하기</a>
				</c:when>
				<c:otherwise>
					<a href="/user/login">로그인하기</a>
					<a href="/user/signup">회원가입하기</a>
				</c:otherwise>
			</c:choose>
	<div class="title">
	<h1>사진 리스트</h1>
	</div>
	<ul class="documents">
		<c:forEach var="document" items="${boards}">
			<li>
			<c:if test="${document.filename != null}">
						<div class="thumb">
							<a href="/board/${document.id}"><img
								src="/images/${document.filename}" width="100" height="100"
								alt="image" /></a>
						</div>
					</c:if>
			
			<div class="content">
				<p>	<a class="subject" href="/board/${document.id}">${document.title}</a>
				</p>
				<p>
					<a class="contents" href="/board/${document.id}">${document.contents}
				</p>
			</div>
			</li>
		</c:forEach>
	</ul>
	<p class="fright btn1"> <a href="/board/form">글쓰기</a></p>
	</div>
</body>
</html>