package org.nhnnext.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	
	@RequestMapping("form")
	public String form() {
		return "form";
	}
	@RequestMapping("photo")
	public String photo() {
		return "photo";
	}
	@RequestMapping(value = "photo", method = RequestMethod.POST)
	public String create(String title, String contents) {
		System.out.println("title : " + title + " contents : " + contents
				+ "photo");
		return "redirect:/";
	}
}
