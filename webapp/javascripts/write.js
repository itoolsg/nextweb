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
			var writeDocument = getClassFirstElement(document, "writeDocument");
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
var deleteBoard = function(e) {
	console.log("deleteBoard");
	e.preventDefault(); // submit 이 자동으로 동작하는 것을 막는다.

	var element = getElement(e);// 삭제 버튼
	
	var board = element.parentNode.parentNode;
	
	var board_id = board.getAttribute("board");// 이건 조금 다시 수정해야 할 ㅋ 안좋은

	var url = "/board/" + board_id + "/board_delete.json"; // 서버로
																				// 보낼
																				// 주소!

	var request = new XMLHttpRequest();
	request.open("POST", url, true);
	request.onreadystatechange = function() {
		if (request.readyState == 4 && request.status == 200) {
			console.log("hello");
			if (request.responseText === "true") {
				alert("삭제 되었습니다.");
				var documents = getClassFirstElement(document, "documents");				
				documents.removeChild(board);
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

		}
	};
	request.send();
}