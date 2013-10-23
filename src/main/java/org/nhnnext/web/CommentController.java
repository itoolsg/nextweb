package org.nhnnext.web;

import javax.servlet.http.HttpSession;

import org.nhnnext.exception.NoBoardException;
import org.nhnnext.exception.NoCommentException;
import org.nhnnext.exception.NoLoginException;
import org.nhnnext.exception.NoUserException;
import org.nhnnext.log.Mylog;
import org.nhnnext.repository.BoardRepository;
import org.nhnnext.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/board")
public class CommentController extends defaultController {

	/**
	 * Title : 코멘트 쓰기 과정
	 * <p>
	 * http://localhost:8080/board/100/comment_ok
	 * </p>
	 * 
	 * @see Comment, Board, User
	 * @throw NullPointerException if no contents in comment
	 * @exception NoBoardException
	 * @exception NoUserException
	 * @exception NoLoginException
	 * 
	 * @param id
	 *            게시글 아이디
	 * @param contents
	 *            코멘트 글
	 * @param session
	 *            세션
	 * */
	@RequestMapping(value = "/{id}/comment_ok", method = RequestMethod.POST)
	public String comment_write(@PathVariable Long id, String contents,
			String modify, HttpSession session) {
		try {
			if (contents == null || contents.equals(""))
				throw new NullPointerException("No Comments");

			User user = getLoginUser(session);// 유저가 없으면 바로 에러
			Board board = getBoard(id);
			Comment comment = new Comment(board, contents);
			comment.setUser(user);
			commentRepository.save(comment);
			return "redirect:/board";// + id;
		} catch (NullPointerException e) {
			Mylog.printError(e);
			return "redirect:/board";// + id;
		} catch (NoBoardException e) {
		} catch (NoUserException e) {
		} catch (NoLoginException e) {
			return "redirect:/user/login";
		} catch (Exception e) {
			Mylog.printError(e);
		}
		return "redirect:/board";
	}

	/**
	 * Title : 코멘트에 코멘트를 쓰기 과정(코멘트에 코멘트 달기 제한 현재 없음)
	 * <p>
	 * http://localhost:8080/board/100/3/comment_ok
	 * </p>
	 * 
	 * @see Comment, Board, User
	 * @throw NullPointerException if no contents in comment
	 * @exception NoBoardException
	 * @exception NoUserException
	 * @exception NoLoginException
	 * @exception NoCommentException
	 * @param id
	 *            게시글 아이디
	 * @param comment_id
	 *            코멘트 아이디
	 * @param contents
	 *            코멘트 글
	 * @param session
	 *            세션
	 * */
	@RequestMapping(value = "/{id}/{comment_id}/comment_ok", method = RequestMethod.POST)
	public String comment_write(@PathVariable Long id,
			@PathVariable Long comment_id, String contents, HttpSession session) {
		try {
			if (contents == null || contents.equals(""))
				throw new NullPointerException("No Comments");

			User user = getLoginUser(session);// 유저가 없으면 바로 에러
			Board board = getBoard(id);
			// 해당하는 부모 코멘트를 가져옴
			Comment parentComment = getComment(comment_id);

			// 부모에 속하는 코멘트 생성
			Comment comment = new Comment(board, parentComment, contents);
			// 코멘트 작성자 기입
			comment.setUser(user);

			commentRepository.save(comment);
			return "redirect:/board/" + id;
		} catch (NullPointerException e) {
			Mylog.printError(e);
			return "redirect:/board/" + id;
		} catch (NoBoardException e) {
		} catch (NoUserException e) {
		} catch (NoLoginException e) {
			return "redirect:/user/login";
		} catch (NoCommentException e) {
		} catch (Exception e) {
			Mylog.printError(e);
		}
		return "redirect:/board";
	}

}
