<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/stylesheets/default.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/timeline.css" />
<script type="text/javascript">
<!--
	//var oldload = window.onload;
	window.onload = function() {
		//if (typeof oldload != "object")
		//	oldload();

		var leftDocuments = document.getElementById("leftDocuments");
		var rightDocuments = document.getElementById("rightDocuments");

		var ul = document.getElementsByClassName("documents");

		if (!ul || ul.length < 3)
			return;

		var left = ul[0];
		var right = ul[1];
		var original = ul[2];

		var documents = original.getElementsByClassName("timeDocument");
		var leftHeight = 0;
		var rightHeight = 0;
		for ( var i = 0; documents.length != 0; i++) {
			if (leftHeight <= rightHeight) {
				leftHeight += documents[0].offsetHeight;
				left.appendChild(documents[0]);

			} else {
				rightHeight += documents[0].offsetHeight;
				right.appendChild(documents[0]);
			}
		}
	};

	-->
</script>
<c:if test="${empty sessionScope.userid}">
	<script type="text/javascript">
	<!--
		var oldload = window.onload;
		window.onload = function() {

			oldload();

			var needLoginForm = document.getElementsByClassName("needLogin");
			for ( var i = 0; i < needLoginForm.length; i++) {
				var textarea = needLoginForm[i]
						.getElementsByTagName("textarea");
				var input = needLoginForm[i].getElementsByTagName("input");
				var submit = needLoginForm[i].getElementsByTagName("button");

				if (input.length >= 1) {
					input[0].disabled = true;
					input[0].placeholder = "로그인을 해주세요.";
				}

				if (textarea.length >= 1) {
					textarea[0].disabled = true;
					textarea[0].placeholder = "로그인을 해주세요.";
				}
				if (submit.length >= 1)
					submit[0].disabled = true;
			}

		}
		-->
	</script>
</c:if>
</head>
<body>
	<div class="timelineContainer">
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

		</div>
		<div id="timeline">
			<div id="leftDocuments">
				<ul class="documents">
				</ul>
			</div>
			<div id="rightDocuments">
				<ul class="documents">
				</ul>
			</div>


			<ul class="documents">
				<li class="timeDocument">
					<div class="write-form needLogin">
						<form action="/board/write" method="post"
							enctype="multipart/form-data">
							<input type="text" name="title" size=40 placeholder="제목 입력해주세요."
								onfocus="document.getElementById('content').style.display='block';"><br />
							<div id="content">
								<textarea class="contents" name="contents" rows="10" cols="50"
									placeholder="글자를 입력해주세요.">${board.contents}</textarea>

								<div class="bottom-action">
									<div class="picture">
										<img id="getpicture" class="getpicture" alt="get picture"
											onclick="document.getElementById('file').click();" /> <input
											id="file" type="file" name="file"
											onchange="document.getElementById('filename').innerHTML=this.value;" />
										<div id="filename"></div>
									</div>
									<button class="submit btn1">올리기</button>
								</div>
							</div>
						</form>
					</div>
				</li>
				<c:forEach var="document" items="${boards}">
					<li class="timeDocument">
						<div class="content">
							<p>
								<a class="subject" href="/board/${document.id}">${document.title}</a>
							</p>
							<p>
								<a class="contents" href="/board/${document.id}">${document.contents}</a>
							</p>
							<c:if test="${document.filename != null}">
								<div class="thumb">
									<a href="/board/${document.id}"><img
										src="/images/${document.filename}" width="100" height="100"
										alt="image" /></a>
								</div>
							</c:if>
							<div class="comments">
								<p class="commentCount">
									<c:choose>
										<c:when test="${not empty document.comments}">
								${document.comments.size()}개의 댓글
							</c:when>
										<c:otherwise>
								0개의 댓글. &nbsp; &nbsp; 댓글을 달아주세요.
							</c:otherwise>
									</c:choose>
								</p>
								<ul>
									<c:forEach var="comment" items="${document.comments}">
										<c:if test="${comment.getComment() == null}">
										${comment.getHtml()}
									</c:if>
									</c:forEach>
								</ul>
							</div>
							<div class="comment-reply needLogin">
								<form action="/board/${document.id}/comment_ok" method="post">
									<span><textarea name="contents" cols="50" rows="3"
											placeholder="글쓰세요."></textarea>
										<button>작성</button></span>
								</form>
							</div>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>