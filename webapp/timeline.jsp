<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Day Board</title>

<link rel="stylesheet" type="text/css" href="/stylesheets/default.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/timeline.css" />

<script src="/javascripts/day.js" type="text/javascript"></script>
<script src="/javascripts/default.js" type="text/javascript"></script>
<script src="/javascripts/write.js" type="text/javascript"></script>
<script src="/javascripts/timeline.js" type="text/javascript"></script>

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
	<div id="sample">
		<ul class="commentSample">
			<li id='comment{comment.id}'>
				<p class='comment-writer'>{comment.userid}</p>
				<p class="comment-contents">{comment.contents}</p> <a href="#"
				class='comment-delete' comment_id="{comment.id}">x</a>
			</li>
		</ul>
		<ul class="boardSample">
			<li class="board" id="board{document.id}" board_id="{document.id}">
				<div class="contents">
					<a class="board-modify" href="#">M</a>  <a class="board-delete" href="#">x</a>
					<p>
						<a class="subject" href="/board/{document.id}">{document.title}</a>
					</p>
					<hr class="line" />
					<div class="content">
						{document.contents}
						<div class="thumb" style="display: {is_image">
							<img is_src="/images/{document.filename}" width="100"
								height="100" alt="image" class="thumbnail" />
						</div>
						<p class="posted">
							Posted By <span class="by">{document.user}</span>
						</p>
					</div>

					<div class="comments">
						<div class="comments-show-area">
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
							<a class="comments-show" href="#">댓글 보기</a>
						</div>
						<div class="comments-area">
							<ul class="comments-list">
							</ul>
							<div class="main-comment-reply comment-reply needLogin">
								<form action="/board/{document.id}/comment_ok" method="post">
									<input type="hidden" name="id" value="{document.id}" /> <span><textarea
											name="contents" cols="50" rows="3" placeholder="글쓰세요."></textarea>
										<button submit='comment'>작성</button></span>
								</form>
							</div>
						</div>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div id="blackscreen">
		<div id="thumb-screen">
			<img class="thumb" src="" alt="thumb open" draggable="false" />
		</div>
	</div>

	<div class="cloud" speed="1.2">
		<span></span> <img src="/img/bg_highClouds.png" alt="high cloud"
			speed="1.2" positions="10%,30%,50%" pos=0 />
	</div>
	<div class="cloud" speed="1.8">
		<span></span> <img src="/img/bg_lowClouds.png" alt="high cloud"
			speed="1.8" positions="0%,20%,50%" pos=0 />
	</div>
	<div class="cloud" speed="1.5">
		<span></span> <img src="/img/bg_lowClouds.png" alt="high cloud"
			speed="1.5" positions="-10%,55%,35%" pos=0 />
	</div>
	<div class="cloud" speed="1.1">
		<span></span> <img src="/img/bg_lowClouds.png" alt="high cloud"
			speed="1.1" positions="35%,5%,50%" pos=0 />
	</div>
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
		<ul class="documents">
			<li class="writeDocument">
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
								<button class="submit btn1" submit="board">올리기</button>
							</div>
						</div>
					</form>
				</div>
			</li>
			<c:forEach var="document" items="${boards}">
				<li class="board" id="board${document.id}" board_id="${document.id}">
					<div class="contents">
						<a class="board-delete" href="#">x</a>
						<a class="board-modify" href="/board/${document.id }/modify">M</a>
						<p>
							<a class="subject" href="#">${document.title}</a>
						</p>
						<hr class="line" />
						<div class="content">
							${document.createContentsTag()}
							<c:if test="${document.filename != null}">
								<div class="thumb">
									<img src="/images/${document.filename}" width="100"
										height="100" alt="image" class="thumbnail" />
								</div>
							</c:if>
							<p class="posted">
								Posted By <span class="by">${document.user.userid}</span>
							</p>
						</div>

						<div class="comments">
							<div class="comments-show-area">
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
								<a class="comments-show" href="#">댓글 보기</a>
							</div>
							<div class="comments-area">
								<ul class="comments-list">
									<c:forEach var="comment" items="${document.comments}">
										<li id='comment${comment.id}'>
											<p class='comment-writer'>${comment.user.userid}</p>
											<p class="comment-contents">${comment.contents}</p> <a
											href="#" class='comment-delete' board_id="${document.id}"
											comment_id="${comment.id}">x</a>
										</li>
									</c:forEach>
								</ul>
								<div class="main-comment-reply comment-reply needLogin">
									<form action="/board/${document.id}/comment_ok" method="post">
										<input type="hidden" name="id" value="${document.id}" /> <span><textarea
												name="contents" cols="50" rows="3" placeholder="글쓰세요."></textarea>
											<button submit='comment'>작성</button></span>
									</form>
								</div>
							</div>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>



	</div>
	</div>
</body>
</html>
