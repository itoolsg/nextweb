var scrolling = function(e) {
	var scroll = document.documentElement.scrollTop || document.body.scrollTop;
	var clouds = $("classAll:cloud");
	var h = window.innerHeight || document.documentElement.clientHeight
			|| document.body.clientHeight;

	Day.each(clouds, function(index) {
		var y = scroll + h
				- (scroll * parseFloat(this.attr("speed")) + h / 4 * index)
				% (h + this.height());

		var wheel = Math.floor((scroll * parseFloat(this.attr("speed")) + h / 4
				* index)
				/ (h + this.height()));
		if (wheel < 0)
			wheel = 0;

		var pos = this.find("img").attr("positions").split(",");
		this.css("left", pos[wheel % 3]);
		this.css("top", y + "px");

	});
};
window.onscroll = scrolling;
window.onresize = scrolling;
window.onload = function() {
	scrolling();

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
		
		var ele = $e(e);

		if (ele.getAttribute("class") == "comments-show")
			toggleCommnets(e);// 댓글 보기
		else if (ele.tagName === "BUTTON"
				&& ele.getAttribute("submit") == "comment")
			writeComment(e);
		else if (ele.tagName === "BUTTON"
				&& ele.getAttribute("submit") == "board")
			writeBoard(e);
		else if (ele.tagName == "A"
				&& ele.getAttribute("class") == "comment-delete")
			deleteComment(e);
		else if (ele.tagName == "A"
				&& ele.getAttribute("class") == "board-delete")
			deleteBoard(e);
		else if (ele.getAttribute("class") == "thumbnail")
			showThumbnail($e(e));// 이미지 확대
		else if (ele.getAttribute("class") == "subject")
			showThumbnail($e(e).parent("class:board").find(".thumbnail"));

		return false;
	}
	var writeComment = function(e) {
		console.log("writeComment");
		e.preventDefault(); // submit 이 자동으로 동작하는 것을 막는다.
		var eleForm = getElement(e).form;

		var oFormData = new FormData(eleForm); // form data들을 자동으로 묶어준다.

		var sID = eleForm[0].value; // // 현재페이지의 ID값을 확인한다.! !
		var url = "/board/" + sID + "/comments.json"; // 서버로 보낼 주소!

		var request = new XMLHttpRequest();
		request.open("POST", url, true);

		request.onreadystatechange = function() {

			if (request.readyState == 4 && request.status == 200) {
				console.log("get Request");

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
				var comments_area = $(eleForm).parent(2);
				// <div class="comments-area">
				var comments = comments_area.parent();
				// <div class="comments">
				var eleCommentList = $(comments, "class:comments-list");
				// <ul class="comments-list">

				html = getClassFirstElement(document, "commentSample").innerHTML;
				html = replaceAll(html, "{comment.id}", obj.id);
				html = replaceAll(html, "{comment.contents}", obj.contents);
				html = replaceAll(html, "{comment.userid}", obj.user.userid);
				html = replaceAll(html, "{comment.bid}", obj.bid);

				eleCommentList.insert("beforeend", html);
				eleForm.reset();

				// 카운트
				openComments(comments);// 오픈할때 style.height가 댓글들의 크기만큼 증가한다.
				countComment(comments);
			}
		};
		request.send(oFormData);
	}
	// 코멘트 삭제
	var deleteComment = function(e) {
		console.log("deleteComment");
		e.preventDefault(); // submit 이 자동으로 동작하는 것을 막는다.

		var element = $e(e);// 삭제 버튼
		var board = element.parent("class:board");
		var board_id = board.getAttribute("board_id");
		var comment_id = element.getAttribute("comment_id");

		var url = "/board/" + board_id + "/" + comment_id
				+ "/comment_delete.json"; // 서버로 보낼 주소!

		var request = new XMLHttpRequest();
		request.open("POST", url, true);
		request.onreadystatechange = function() {
			if (request.readyState == 4 && request.status == 200) {
				console.log("get Request");

				if (request.responseText === "true") {
					alert("삭제 되었습니다.");

					var comment = element.parent();
					var comments = $(board, "class:comments");
					var comments_list = $(board, "class:comments-list");
					comments_list.remove(comment);

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
	var showThumbnail = function(element) {

		if (element.isNull()) {
			alert("이미지가 없습니다.");
			return;
		}
		var src = element.attr("src");

		blackscreen.style.display = "block";
		img_screen.style.display = "block";
		document.body.style.overflow = "hidden";

		if (element.naturalWidth) {
			img_screen.style.marginLeft = -element.naturalWidth / 2 + "px";
			img_screen.style.marginTop = -element.naturalHeight / 2 + "px";
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
		var element = $e(e);
		var comments = element.parent(2);
		var comments_area = comments.find(".comments-area");
		var comments_list = comments.find(".comments-list");
		var comment_reply = comments.find(".main-comment-reply");

		if (comments_area.attr("is_open") == "true") {
			element.html("댓글 보기");
			comments_area.css("height", 0 + "px");
			comments_area.attr("is_open", "false");
		} else {
			element.html("댓글 접기");
			comments_list.css("display", "block");
			comment_reply.css("display", "block");

			comments_area.css("height", comments_list.outerHeight()
					+ comment_reply.outerHeight() + 16 + "px");
			comments_area.setAttribute("is_open", "true");
		}
		e.preventDefault();
	}
	var countComments = function() {// 코멘트 세기
		var comments = document.getElementsByClassName("comments");// 코멘트 영역
		Day.each(comments, function() {
			countComment(this);
		});
	}
	var openComments = function(comments) {
		var comments_area = $(comments, "class:comments-area");
		var comments_list = $(comments_area, "class:comments-list");
		var comment_reply = $(comments_area, "class:main-comment-reply");

		comments_area.css("height", comments_list.outerHeight()
				+ comment_reply.outerHeight() + 16 + "px");

		comments_area.setAttribute("is_open", "true");
	}
	var countComment = function(comments) {
		var count_area = $(comments, "class:commentCount");// 코멘트
		var length = $(comments, "tag:li").length;
		count_area.html(length + "개의 댓글");
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