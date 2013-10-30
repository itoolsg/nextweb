window.onload = function() {
	var timeline = document.getElementById("timeline");
	var blackscreen = document.getElementById("blackscreen");
	var img_screen = document.getElementById("thumb-screen");
	var is_dragging = false;
	var prePos = {
		x : 0,
		y : 0
	};

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

	var screen_thumb = getClassFirstElement(img_screen, "thumb");

	var thumbclick = function(e) {
		if (!checkTag(e, "IMG"))
			return false;

		var img = getElement(e);
		var src = img.getAttribute("src");

		blackscreen.style.display = "block";
		img_screen.style.display = "block";
		document.body.style.overflow = "hidden";
		if (img.naturalWidth) {
			img_screen.style.marginLeft = -img.naturalWidth / 2 + "px";
			img_screen.style.marginTop = -img.naturalHeight / 2 + "px";
		} else {
			// Using an Image Object
			img = new Image();
			img.onload = function() {
				img_screen.style.marginLeft = -this.width / 2 + "px";
				img_screen.style.marginTop = -this.height / 2 + "px";
			};
			img.src = 'http://lorempixel.com/output/nature-q-c-640-480-3.jpg';
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
			comment_area.setAttribute("is_open","false");
		} else {
			element.innerText = "댓글 접기";
			comments.style.display = "block";
			comment_reply.style.display = "block";
			comment_area.style.height = comments.offsetHeight
					+ comment_reply.offsetHeight + 16 + "px";
			comment_area.setAttribute("is_open","true");
		}

	}
	var countComments = function() {

		var comments = document.getElementsByClassName("comments");

		for ( var i = 0; i < comments.length; i++) {
			var count_area = getClassFirstElement(comments[i], "commentCount");
			var length = comments[i].getElementsByTagName("li").length;
			count_area.innerText = length + "개의 댓글";
		}
	}
	var getPosition = function(e) {
		var xy = {
			x : 0,
			y : 0
		}
		if (e.touches) {
			if (e.type == "touchend") {
				xy.x = e.changedTouches[0].pageX;
				xy.y = e.changedTouches[0].pageY;
			} else {
				xy.x = e.touches[0].clientX;
				xy.y = e.touches[0].clientY;
			}

		} else {
			xy.x = e.offsetX;
			xy.y = e.offsetY;
		}
		return xy;
	};
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
	addEvent(timeline, "click", thumbclick);
	addEvent(blackscreen, "click", function() {
		blackscreen.style.display = "none";
		img_screen.style.display = "none";
		document.body.style.overflow = "auto";
	});
	addEvent(img_screen, "mousedown", function(e) {
		prePos = getPosition(e);
		is_dragging = true;
	});
	addEvent(img_screen, "mousemove", mousemove);
	addEvent(img_screen, "mouseup", mouseup);
	addEvent(img_screen, "mouseleave", mouseup);

	var eles = document.getElementsByClassName("comments-show");
	for ( var i = 0; i < eles.length; i++) {
		addEvent(eles[i], "click", toggleCommnets);
	}
	countComments();
};