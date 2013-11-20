function $(element, query) {
	if (typeof element === "object") {
		if (element instanceof day)
			element = element._object;

		if (!query)
			return new day(element);
	} else {
		query = element;
		element = document;
	}

	if (typeof query === "string") {
		if (query.indexOf("id:") == 0) {
			if (element === document)
				return new day(document
						.getElementById(query.replace("id:", "")));

		} else if (query.indexOf("tag:") == 0)
			return days(element.getElementsByTagName(query.replace("tag:", "")));
		else if (query.indexOf("class:") == 0)
			return new day(element.getElementsByClassName(query.replace(
					"class:", ""))[0]);
		else if (query.indexOf("classAll:") == 0)
			return days(element.getElementsByClassName(query.replace(
					"classAll:", "")));
		else
			return days(element.querySelectorAll(query));
	}

	alert("ERR");

	console.log("ERR" + "   " + query);
	console.log(element);
}
var $e = function(e) {
	var e = e || window.event;
	var target = e.target || e.srcElement;
	return $(target);
}
function days(nodelist) {
	var ds = [];
	for ( var i = 0; i < nodelist.length; i++) {
		ds.push(new day(nodelist[i]));
	}
	return ds;
}
var Day = {
	_extend : function(objs) {
		for ( var key in objs)
			this[key] = objs[key];
	}
}
var day = function(object) {
	this._object = object;
	this.isNull = function() {
		if(!this._object)
			return true;
		
		return false;
	}
	this.addEvent = function(evnt, func) {
		if (this._object.addEventListener) // W3C DOM
			this._object.addEventListener(evnt, func, false);
		else if (this._object.attachEvent) { // IE DOM
			this._object.attachEvent("on" + evnt, func);
		} else { // No much to do
			this._object[evnt] = func;
		}
	}
	this.getAttribute = function(name) {
		return this._object.getAttribute(name);
	}
	this.setAttribute = function(name, value) {
		this._object.setAttribute(name, value);
	}
	this.attr = function(name, value) {
		if (!value)
			return this.getAttribute(name);

		this.setAttribute(name, value);
		return value;
	}
	this.css = function(name, value) {
		if (!value)
			return this._object.currentStyle[name];

		this._object.style[name] = value;
		return value;
	}

	this.findAll = function(query) {
		return days(this._object.querySelectorAll(query));
	}
	this.find = function(query) {
		return $(this._object.querySelector(query));
	}
	this.parent = function(obj) {
		var parent = this._object.parentNode;
		if (typeof obj === "number") {
			for ( var i = 2; i <= obj; i++) {
				parent = parent.parentNode;
			}
		} else if (typeof obj === "string") {
			while (parent) {
				if (obj.indexOf("class:") == 0) {
					if (Day.hasClass(parent, obj.replace("class:", "")))
						break;
				} else if (obj.indexOf("id:") == 0) {
					if (parent.id == obj.replace("id:", ""))
						break;
				} else if (obj.indexOf("tag:") == 0) {
					if (parent.nodeName == obj.replace("tag:", "")
							.toUpperCase())
						break;
				}

				parent = parent.parentNode;
			}
		}
		if (!parent) {
			console.log("parent is null");
			return null;
		} else {
			return $(parent);
		}
	}
	this.previous = function() {
		return $(this._object.previousElementSibling);
	}
	this.next = function() {
		return $(this._object.nextElementSibling);
	}
	this.html = function(html) {
		if(!html)
			return this._object.innerHTML;
		
		this._object.innerHTML = html;
	}
	this.click = function(func) {
		this.addEvent("click", func);
	}
	this.text = function(text) {
		if(!text)
			return this._object.innerText;
		
		this._object.innerText = text;
	}
	this.insert = function(pos, html) {
		this._object.insertAdjacentHTML(pos, html);
	}
	this.remove = function(obj) {
		if (obj instanceof day) {
			this._object.removeChild(obj._object);
		} else {
			this._object.removeChild(obj);
		}
	}
	this.height = function() {
		return this._object.clientHeight || this._object.innerHeight;
	}
	this.width = function() {
		return this._object.clientWidth || this._object.innerWidth;
	}
	this.outerWidth = function(b) {
		if (b)
			return this._object.offsetWidth + this.css("margin-left")
					+ this.css("margin-right");
		else
			return this._object.offsetWidth;
	}
	this.outerHeight = function(b) {
		if (b)
			return this._object.offsetHeight + this.css("margin-top")
					+ this.css("margin-bottom");
		else
			return this._object.offsetHeight;
	}
	if(this._object) {
		this.tagName = this._object.nodeName;
		this.naturalWidth = this._object.naturalWidth;
		this.naturalHeight = this._object.naturalHeight;
	}
}

Day._extend({
	each : function(days, callback) {
		var returnValue;
		for ( var i = 0; i < days.length; i++)
			callback.apply(days[i], [ i, days[i] ]);
	},
	hasClass : function(obj, className) {
		if (!obj) {
			alert("Err");
			return false;
		}
		if (!obj.className)
			return false;

		var classs = obj.className.split(" ");
		return (classs.indexOf(className) != -1);
	},
	single : function(c, obj) {
		return new c(obj);
	}
});
