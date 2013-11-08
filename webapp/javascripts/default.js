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
	if (!target)
		return false;

	console.log("click tag : " + target.nodeName);

	// Tag인지 아닌지 체크
	if (target.nodeName != tag)
		return false;
	return true;
}
var getElement = function(e) {
	var e = e || window.event;
	var target = e.target || e.srcElement;
	return target;
}
var getClassFirstElement = function(element, name) {
	var es = element.getElementsByClassName(name);
	return es[0];
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
function replaceAll(str, searchStr, replaceStr) {
	while (str.indexOf(searchStr) != -1) {
		str = str.replace(searchStr, replaceStr);
	}
	return str;
}