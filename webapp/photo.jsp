<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>사진 업로드</h1>
	<form action="/board/photo" method="POST">
		제목 : <input type="text" name="title" size="30" /><br>
		<textarea name="contents" placeholder="글자를 입력하세요" rows="10" cols="50">글자를 미리 넣어보</textarea><br>
		 <input type="file" name="photofile" size="20">
		<input type="submit" name="submit" value="글쓰기"/>
	</form>
</body>
</html>
