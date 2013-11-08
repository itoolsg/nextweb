window.onload = function() {
	var timeline = getClassFirstElement(document, "documents");
	var blackscreen = document.getElementById("blackscreen");
	var img_screen = document.getElementById("thumb-screen");
	var is_dragging = false;
	var prePos = {
		x : 0,
		y : 0
	};

	var screen_thumb = getClassFirstElement(img_screen, "thumb");

	var timelineclick = function(e) {

		var ele = getElement(e);

		if (ele.getAttribute("class") == "comments-show") {
			toggleCommnets(e);
			return false;
		}
		if (checkTag(e, "BUTTON") && ele.getAttribute("submit") == "comment")
			writeComment(e);

		if (checkTag(e, "BUTTON") && ele.getAttribute("submit") == "board")
			writeBoard(e);

		if (checkTag(e, "A") && ele.getAttribute("class") == "comment-delete")
			deleteComment(e);

		if (ele.getAttribute("class") == "thumbnail")
			showThumbnail(e);

		return false;
	}
	var writeBoard = function(e) {
		console.log("writeBoard");
		e.preventDefault(); // submit 이 자동으로 동작하는 것을 막는다.
		var eleForm = getElement(e).form;

		var oFormData = new FormData(eleForm); // form data들을 자동으로 묶어준다.

		var sID = eleForm[0].value; // // 현재페이지의 ID값을 확인한다.! !
		var url = "/board/write.json"; // 서버로 보낼 주소!

		var request = new XMLHttpRequest();
		request.open("POST", url, true);
		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				console.log("hello");
				var obj = JSON.parse(request.responseText);
				var writeDocument = getClassFirstElement(document,
						"writeDocument");
				var sample = getClassFirstElement(document, "boardSample");
				var html = sample.innerHTML;
				var is_img = (obj.filename == null) ? "none" : "block";

				html = html.replace("{is_image}", is_img);

				if (is_img == "block") {
					html = html.replace("{document.filename}", obj.filename);
					html = html.replace("is_src", "src");
				}

				html = replaceAll(html, "{document.id}", obj.id);

				html = html.replace("{document.user}", obj.user.userid);

				html = html.replace("{document.title}", obj.title);
				html = html.replace("{document.contents}", obj.contents);

				writeDocument.insertAdjacentHTML("afterend", html);
				eleForm.reset();
			}
		};
		request.send(oFormData);
	}
	var writeComment = function(e) {
		console.log("writeComment");
		e.preventDefault(); // submit 이 자동으로 동작하는 것을 막는다.
		var eleForm = getElement(e).form;

		var comment_area = eleForm.parentNode.parentNode
		var comments = comment_area.parentNode;

		var oFormData = new FormData(eleForm); // form data들을 자동으로 묶어준다.

		var sID = eleForm[0].value; // // 현재페이지의 ID값을 확인한다.! !
		var url = "/board/" + sID + "/comments.json"; // 서버로 보낼 주소!

		var request = new XMLHttpRequest();
		request.open("POST", url, true);
		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				console.log("hello");

				var obj;
				try {
					obj = JSON.parse(request.responseText);

					if (obj.err) { // 에러 메시지
						alert(obj.desc);
						return false;
					}
				} catch (e) {
					alert("FAIL");
					return false;
				}
				var eleCommentList = eleForm.parentNode.previousElementSibling;

				html = getClassFirstElement(document, "commentSample").innerHTML;
				html = replaceAll(html, "{comment.id}", obj.id);
				html = replaceAll(html, "{comment.contents}", obj.contents);
				html = replaceAll(html, "{comment.userid}", obj.user.userid);
				html = replaceAll(html, "{comment.bid}", obj.bid);

				eleCommentList.insertAdjacentHTML("beforeend", html);
				eleForm.reset();

				

				// 카운트
				openComments(comments);
				countComment(comments);
			}
		};
		request.send(oFormData);
	}
	// 코멘트 삭제
	var deleteComment = function(e) {
		console.log("deleteComment");
		e.preventDefault(); // submit 이 자동으로 동작하는 것을 막는다.

		var element = getElement(e);// 삭제 버튼

		var board_id = element.getAttribute("board_id");// 이건 조금 다시 수정해야 할 ㅋ 안좋은
		// 방식
		var comment_id = element.getAttribute("comment_id");

		var url = "/board/" + board_id + "/" + comment_id
				+ "/comment_delete.json"; // 서버로 보낼 주소!

		var request = new XMLHttpRequest();
		request.open("POST", url, true);
		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				console.log("hello");

				if (request.responseText === "true") {
					alert("삭제 되었습니다.");

					var bElement = document.getElementById("board" + board_id);
					var comment = document.getElementById("comment"
							+ comment_id);
					var comments = getClassFirstElement(bElement, "comments");
					var comments_list = getClassFirstElement(comments,
							"comments-list");
					comments_list.removeChild(comment);
					
					openComments(comments);
					countComment(comments);
					return true;
				}
				try {
					var obj = JSON.parse(request.responseText);// 에러 메시지
					if (obj.err) {
						alert(obj.desc);
						return false;
					}
				} catch (e) {
					alert("실패");
				}
				return false;

				// comment_area.style.height = comments_list.offsetHeight
				// + comment_reply.offsetHeight + 16 + "px";

				// 카운트

			}
		};
		request.send();
	}
	var showThumbnail = function(e) {
		var src = ele.getAttribute("src");
		blackscreen.style.display = "block";
		img_screen.style.display = "block";
		document.body.style.overflow = "hidden";
		if (ele.naturalWidth) {
			img_screen.style.marginLeft = -ele.naturalWidth / 2 + "px";
			img_screen.style.marginTop = -ele.naturalHeight / 2 + "px";
		} else {
			// Using an Image Object
			var img = new Image();
			img.onload = function() {
				img_screen.style.marginLeft = -this.width / 2 + "px";
				img_screen.style.marginTop = -this.height / 2 + "px";
			};
			img.src = src;
		}

		screen_thumb.setAttribute("src", src);
	}
	var toggleCommnets = function(e) {
		var element = getElement(e);
		var parent = element.parentNode.parentNode;
		var comments = getClassFirstElement(parent, "comments-list");
		var comment_reply = getClassFirstElement(parent, "main-comment-reply");
		var comment_area = getClassFirstElement(parent, "comment-area");

		if (comment_area.getAttribute("is_open") == "true") {
			element.innerText = "댓글 보기";
			comment_area.style.height = 0 + "px";
			comment_area.setAttribute("is_open", "false");
		} else {
			element.innerText = "댓글 접기";
			comments.style.display = "block";
			comment_reply.style.display = "block";
			comment_area.style.height = comments.offsetHeight
					+ comment_reply.offsetHeight + 16 + "px";
			comment_area.setAttribute("is_open", "true");
		}
		e.preventDefault();
	}
	var countComments = function() {// 코멘트 세기
		var comments = document.getElementsByClassName("comments");// 코멘트 영역
		for ( var i = 0; i < comments.length; i++) {
			countComment(comments[i]);
		}
	}
	var openComments = function(comments) {
		var comment_area = getClassFirstElement(comments, "comment-area");
		var comments_list = getClassFirstElement(comment_area, "comments-list");
		var comment_reply = getClassFirstElement(comment_area,
				"main-comment-reply");
		comment_area.style.height = comments_list.offsetHeight
				+ comment_reply.offsetHeight + 16 + "px";
		comment_area.setAttribute("is_open", "true");
	}
	var countComment = function(comments) {
		var count_area = getClassFirstElement(comments, "commentCount");// 코멘트
		var length = comments.getElementsByTagName("li").length;
		count_area.innerText = length + "개의 댓글";
	}

	// 블랙스크린에서 이미지의 이동
	var mousedown = function(e) {
		prePos = getPosition(e);
		is_dragging = true;
	}
	var mouseup = function() {
		is_dragging = false;
		console.log("mouseup");
	};
	var mousemove = function(e) {
		if (!is_dragging)
			return false;
		// var height = document.body.offsetHeight;
		// var width = document.body.offsetWidth;
		var xy = getPosition(e);
		var mL = parseInt(img_screen.style.marginLeft, 10);
		var mT = parseInt(img_screen.style.marginTop, 10);
		img_screen.style.marginLeft = mL + (xy.x - prePos.x) + "px";
		img_screen.style.marginTop = mT + (xy.y - prePos.y) + "px";

		console.log(xy);
	};
	addEvent(timeline, "click", timelineclick);// 타임라인 전체 클릭
	addEvent(blackscreen, "click", function(e) {
		if (checkTag(e, "IMG"))
			return false;
		blackscreen.style.display = "none";
		img_screen.style.display = "none";
		document.body.style.overflow = "auto";

	});
	addEvent(img_screen, "mousedown", mousedown);
	addEvent(img_screen, "mousemove", mousemove);
	addEvent(img_screen, "mouseup", mouseup);
	addEvent(img_screen, "mouseleave", mouseup);

	countComments();
};