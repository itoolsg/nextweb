window.onload = function() {
	var getElement = function(target, name) {
		return target.getElementsByClassName(name);
	};
	var addEvent = function(element, evnt, func) {
		if (element.addEventListener) // W3C DOM
			element.addEventListener(evnt, func, false);
		else if (element.attachEvent) { // IE DOM
			element.attachEvent("on" + evnt, func);
		} else { // No much to do
			element[evnt] = func;
		}
	}
	var checkTag = function(e, tag) {
		var e = e || window.event;
		var target = e.target || e.srcElement;
		var recover = "";
		if (!target)
			return false;

		console.log("click tag : " + target.nodeName);

		// Tag인지 아닌지 체크
		if (target.nodeName != tag)
			return false;

		return true;
	}
	var viewCommentForm = function(e) {
		e = e || window.event;

		// 전송버튼
		if (checkTag(e, "BUTTON"))
			return false;

		// 글을 클릭할때만 반응
		if (!checkTag(e, "SPAN"))
			return false;

		e.preventDefault();
		e.stopPropagation();

		var element = getElement(this, "comment-reply");
		var len = element.length;
		if (len == 0)
			return false;
		element[len - 1].style.display = "block";
		return false;
	};
	var elements = getElement(document, "comment-list");
	for ( var i = 0; i < elements.length; i++) {
		addEvent(elements[i], "click", viewCommentForm);
	}
};