package org.nhnnext.web;

import javax.servlet.http.HttpSession;

import org.nhnnext.exception.NoBoardException;
import org.nhnnext.exception.NoLoginException;
import org.nhnnext.exception.NoUserException;
import org.nhnnext.log.Mylog;
import org.nhnnext.repository.BoardRepository;
import org.nhnnext.repository.UserRepository;
import org.nhnnext.support.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author younkue
 */
@Controller
@RequestMapping("/board")
public class BoardController extends defaultController {

	/**
	 * Title : 리스트 보기
	 * <p>
	 * http://localhost:8080/board
	 * </p>
	 * 
	 */
	@RequestMapping("")
	public String showList(Model model) {
		model.addAttribute("boards", boardRepository.findAll());
		return "list";
	}

	/**
	 * Title : 글쓰기 폼 보기
	 * <p>
	 * http://localhost:8080/board/form
	 * </p>
	 * 
	 * @throws NoUserException
	 *             ,NoLoginException
	 */
	@RequestMapping("/form")
	public String showForm(HttpSession session) {

		try {
			User user = getLoginUser(session);
			return "form";
		} catch (NoUserException e) {
			return "redirect:/user/form";
		} catch (NoLoginException e) {
			return "redirect:/user/form";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	/**
	 * Title : 글쓰기 과정
	 * <p>
	 * http://localhost:8080/board/write
	 * </p>
	 * 
	 * @see Board
	 * @throws NullPointerException
	 *             if board is null in Modify Page
	 * 
	 * @throws NoUserException
	 *             ,NoLoginException
	 * */
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(Board board, String modify, MultipartFile file,
			HttpSession session) {

		// 수정하는지 판단
		// modify가 1일시 수정으로 판단.
		if (modify != null && modify.equals("1"))
			return modify(board, file, session);

		try {
			User user = getLoginUser(session);
			board.setUser(user);// 게시자 입력

			// file upload
			String filename = FileUploader.upload(file);

			// 파일이 존재 안할시 null로 반환
			if (filename != null)
				board.setFilename(filename);

			// 저장
			Board savedBoard = boardRepository.save(board);

			return "redirect:/board/" + savedBoard.getId();// view페이지로 갑니다.
		} catch (NullPointerException e) {
			Mylog.printError(e);
		} catch (NoUserException e) {//
		} catch (NoLoginException e) {
			return "redirect:/user/form";
		} catch (Exception e) {
			Mylog.printError(e);
		}
		return "redirect:/board";
	}

	/**
	 * Title : 수정하는 과정
	 * <p>
	 * http://localhost:8080/board/write
	 * </p>
	 * <p>
	 * modify = "1"
	 * </p>
	 * 
	 * @param board
	 *            게시글
	 * @param file
	 *            첨부파일
	 * @param session
	 *            세션
	 * 
	 * @exception NoUserException
	 *                ,NoLoginException, NoBoardException
	 * */
	private String modify(Board board, MultipartFile file, HttpSession session) {
		try {
			User user = getLoginUser(session);

			long id = board.getId();
			Board originalBoard = getBoard(id);

			// No match ERROR
			if (!originalBoard.matchId(user.getUserid())) { // 게시자가 아닐 때
				System.out.println("No Match Err");
				return "redirect:/board/" + id;
			}

			// 파일 이름을 원래의 파일로 저장을 해놓음
			// 수정해서 파일을 고치지 않을시 board의 filename은 null이다.
			board.setFilename(originalBoard.getFilename());
			board.setUser(user);

			// file upload
			String filename = FileUploader.upload(file);

			// 파일이 존재 안할시 null로 반환
			if (filename != null)
				board.setFilename(filename);

			// 수정
			Board savedBoard = boardRepository.save(board);
		} catch (NoUserException e) {
		} catch (NoLoginException e) {
			return "redirect:/user/form";
		} catch (NoBoardException e) {
		} catch (Exception e) {
			Mylog.printError(e);
		}
		return "redirect:/board/" + board.getId();// view페이지로 갑니다.
	}

	/**
	 * Title : 게시글 보기
	 * <p>
	 * http://localhost:8080/board/100
	 * </p>
	 * 
	 * @throws NullPointerException
	 *             if board is null
	 * */
	@RequestMapping("/{id}")
	public String view(@PathVariable Long id, Model model) {
		// TODO DB에서 id에 해당하는 Board 데이터를 조회해야 한다. // TODO 조회한 Board 데이터를 Model에
		// 저장해야 한다.
		try {
			Board board = getBoard(id);
			model.addAttribute("board", board);
			// null Exception
			model.addAttribute("comments", board.getComments());// 코멘트 리스트
			return "show";
		} catch (NullPointerException e) {
			// 게시물 존재 X
			Mylog.printError(e);
		} catch (NoBoardException e) {
		} catch (Exception e) {
			Mylog.printError(e);
		}
		return "redirect:/board";

	}

	/**
	 * Title : 수정하기
	 * <p>
	 * http://localhost:8080/board/100/modify
	 * </p>
	 * 
	 * @see Board
	 * @throws NullPointerException
	 *             if board is null
	 * */
	@RequestMapping("/{id}/modify")
	public String showModifyForm(@PathVariable Long id, Model model,
			HttpSession session) {
		try {
			User user = getLoginUser(session);
			Board board = getBoard(id);

			if (!board.matchId(user.getUserid())) { // 게시자가 아닐 때
				// No match ERROR
				System.out.println("No Match Err");
				return "redirect:/board/" + id;
			}
			model.addAttribute("board", board);
			model.addAttribute("modify", 1);// 수정표시

			return "form";
		} catch (NullPointerException e) {
			// 해당하는 id값이 없음.
			Mylog.printError(e);
			return "redirect:/board";
		} catch (NoUserException e) {
		} catch (NoLoginException e) {
			return "redirect:/user/form";
		} catch (NoBoardException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/board/" + id;
	}

	/**
	 * Title : 삭제하기 바로 삭제됨 ㅠㅠㅠ
	 * <p>
	 * http://localhost:8080/board/100/delete
	 * </p>
	 * 
	 * @see Board
	 * @throws IllegalArgumentException
	 *             if document is not in BOARD db
	 * */
	@RequestMapping("/{id}/delete")
	public String delete(@PathVariable Long id, HttpSession session) {
		try {
			User user = getLoginUser(session);

			Board board = getBoard(id);
			board.deleteComments(commentRepository);
			if (!board.matchId(user.getUserid())) { // 게시자가 아닐 때
				// No match ERROR
				System.out.println("No Match Err");
				return "redirect:/board/" + id;
			}
			boardRepository.delete(id);
		} catch (IllegalArgumentException e) {
			Mylog.printError(e);// 삭제시 정보가 없을 떄
		} catch (NoUserException e) {
		} catch (NoLoginException e) {
			return "redirect:/user/form";
		} catch (NoBoardException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/board";
	}
}
