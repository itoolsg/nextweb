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
	var checkButton = function(e){
		var e = e || window.event;
		var target = e.target || e.srcElement;
		var recover = "";
		if(!target)
			return false;
		
		//ul일때 null값 방지
		if(target.nodeName != "BUTTON")
			return false;
		
		return true;
	}
	var viewCommentForm = function(e) {
		e = e || window.event;
		
		if(checkButton(e))
			return false;
		
		e.preventDefault();
		e.stopPropagation();
		var element = getElement(this, "reply");
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