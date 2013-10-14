package org.nhnnext.web;

import java.util.Iterator;

import org.nhnnext.repository.BoardRepository;
import org.nhnnext.support.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardRepository repository;

	@RequestMapping("/form")
	public String form() {
		return "form";
	}

	@RequestMapping("")
	public String list(Model model) {
		Iterable<Board> iterable = repository.findAll();
		Iterator<Board> iterator = iterable.iterator();
		model.addAttribute("list", iterator);
		return "list";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(Board board, String modify, MultipartFile file) {
		//modify
		if(modify.equals("1")) {
			long id = board.getId();
			Board b = repository.findOne(id);
			board.setFilename(b.getFilename());
		}
		//file upload
		
		String filename = FileUploader.upload(file);
		if(filename != null)
			board.setFilename(filename);
	
		Board savedBoard = repository.save(board);
		// model.addAttribute("board", savedBoard);
		System.out.println("modify : " + modify + " - " + board);
		return "redirect:/board/" + savedBoard.getId();
	}

	@RequestMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		// TODO DB에서 id에 해당하는 Board 데이터를 조회해야 한다. // TODO 조회한 Board 데이터를 Model에
		// 저장해야 한다.

		Board board = repository.findOne(id);
		if (board == null) {// No Document
			return "redirect:/board";
		}
		model.addAttribute("board", board);
		model.addAttribute("img", board.getFilename());
		return "show";
	}

	@RequestMapping("/{id}/modify")
	public String modify(@PathVariable Long id, Model model) {
		Board board = repository.findOne(id);
		if (board == null) {// No Document
			return "redirect:/board";
		}
		model.addAttribute("board", board);
		model.addAttribute("modify", 1);
		return "form";
	}

	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		repository.delete(id);
		return "redirect:/board";
	}
}
