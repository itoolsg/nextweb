<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>연규 포토</title>
<link rel="stylesheet" type="text/css" href="/stylesheets/default.css" />
<link rel="stylesheet" type="text/css" href="/stylesheets/write.css" />
</head>
<body>
	<img src="/img/bg_lowClouds.png" class="cloud c1" />
	<img src="/img/bg_lowClouds.png" class="cloud c2" />
	<div class="container">
		<div class="back">
			<a href="/board">리스트로 돌아가기</a>
		</div>
		<form action="/board/write" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}"> <input
				type="hidden" name="modify" value="${modify}"> <input
				class="title" type="text" name="title" size=40
				value="${board.title}" placeholder="제목 입력해주세요."><br />
			<textarea class="contents" name="contents" rows="10" cols="50"
				placeholder="글자를 입력해주세요.">${board.contents}</textarea>

			<br />
			<div class="bottom-action">
				<div class="picture">
					<img id="getpicture" class="getpicture" alt="get picture"
						onclick="document.getElementById('file').click();" />
					<c:if test="${board.filename != null}">
						<img src="/images/${board.filename}" alt="image" width="40"
							height="40" />
					</c:if>
					<input id="file" type="file" name="file"
						onchange="document.getElementById('filename').innerHTML=this.value;" />
					<div id="filename"></div>
				</div>
				<button class="submit btn1">올리기</button>
			</div>
		</form>
	</div>
</body>
</html>