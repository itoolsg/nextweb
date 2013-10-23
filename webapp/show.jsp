<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>younkue :: ${board.title}</title>
<script src="/javascripts/view.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/stylesheets/default.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/view.css" />
<c:if test="${empty sessionScope.userid}">
	<script type="text/javascript">
	<!--
		var oldload = window.onload;
		window.onload = function() {
			oldload();

			var replyForm = document.getElementsByClassName("comment-reply");
			for ( var i = 0; i < replyForm.length; i++) {
				var textarea = replyForm[i].getElementsByTagName("textarea");
				var submit = replyForm[i].getElementsByTagName("button");
				if (textarea.length == 1) {
					textarea[0].disabled = true;
					textarea[0].placeholder = "로그인을 해주세요.";
				}
				if (submit.length == 1)
					submit[0].disabled = true;
			}
		}
		-->
	</script>
</c:if>
</head>
<body>
	<div class="container">
		<div class="top-menu">
			<c:choose>
				<c:when test="${not empty sessionScope.userid}">
					<a href="/user/logout">로그아웃하기</a>
				</c:when>
				<c:otherwise>
					<a href="/user/login">로그인하기</a>
					<a href="/user/signup">회원가입하기</a>
				</c:otherwise>
			</c:choose>
			&nbsp;&nbsp;<a href="/board">list</a>&nbsp;
		</div>

		<div class="content">
			<h1>${board.title}</h1>

			<c:if test="${board.filename != null}">
				<img src="/images/${board.filename}" width="100%"  />
				<br>
			</c:if>
			<div class="originalContent">
				${board.contents}
			</div>
			<div class="btnArea">
				<a href="./${id}/modify"><button class="modify">수정</button></a> <a href="./${id}/delete"><button class="delete">삭제</button></a>
			</div>
		</div>
		<div class="comments">
			<ul>
				<c:forEach var="comment" items="${comments}">
					<c:if test="${comment.getComment() == null}">
						${comment.getHtml()}
				</c:if>
				</c:forEach>
			</ul>
		</div>
		<div class="comment-reply">
			<form action="/board/${id}/comment_ok" method="post">
				<span><textarea name="contents" cols="50" rows="3"
						placeholder="글쓰세요."></textarea>
					<button>작성</button></span>
			</form>
		</div>
	</div>
</body>
</html>