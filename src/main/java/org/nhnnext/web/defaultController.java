package org.nhnnext.web;

import javax.servlet.http.HttpSession;

import org.nhnnext.exception.NoBoardException;
import org.nhnnext.exception.NoCommentException;
import org.nhnnext.exception.NoLoginException;
import org.nhnnext.exception.NoUserException;
import org.nhnnext.repository.BoardRepository;
import org.nhnnext.repository.CommentRepository;
import org.nhnnext.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Title : Controller의 공통적인 변수와 메소드
 * <p>
 * 예외처리 포함해서 가져옴
 * </p>
 * 
 */
@Controller
public class defaultController {
	@Autowired
	protected BoardRepository boardRepository;

	@Autowired
	protected CommentRepository commentRepository;

	@Autowired
	protected UserRepository userRepository;

	/**
	 * Title : 로그인한 유저 가져오기
	 * <p>
	 * 예외처리 포함해서 가져옴
	 * </p>
	 * 
	 * @param session
	 *            세션
	 * 
	 * @throws NoUserException
	 *             if no user
	 * @throws NoLoginException
	 *             if user is not in session
	 */
	public User getLoginUser(HttpSession session) throws NoUserException,
			NoLoginException {
		// session을 통해 가져옴
		String userid = (String) session.getAttribute("userid");
//		TEST계정.
//		User user = userRepository.findOne("aa");
//		User user2 = new User();
//		user2.setUserid("aa");
//		user2.setPassword("asas");
//		user2.setName("asdasd");
//		if (user != null) {
//			user2 = user;
//		} else {
//			user2 = userRepository.save(user2);
//		}
//		return user2;

		if (userid == null)
			throw new NoLoginException(); // 로그인이 안되어 있을 때

		User user = userRepository.findOne(userid);

		if (user == null)
			throw new NoUserException(session); // 해당하는 유저가 없을 때

		return user;
	}

	/**
	 * Title : 게시글 가져오기
	 * <p>
	 * 예외처리 포함해서 가져옴
	 * </p>
	 * 
	 * @param id
	 *            게시글 아이디
	 * @throws NoBoardException
	 *             if board is null
	 */
	public Board getBoard(long id) throws NoBoardException {
		Board board = boardRepository.findOne(id);
		if (board == null)
			throw new NoBoardException(id);// 해당하는 게시물이 없을 때

		return board;
	}
	
	
	/**
	 * Title : 유저 가져오기
	 * <p>
	 * 예외처리 포함해서 가져옴
	 * </p>
	 * 
	 * @param session
	 *            세션
	 * 
	 * @throws NoUserException
	 *             if no user
	 */
	public User getUser(String userid) throws NoUserException {
		// session을 통해 가져옴

		User user = userRepository.findOne(userid);

		if (user == null)
			throw new NoUserException(userid); // 해당하는 유저가 없을 때

		return user;
	}
	
	
	/**
	 * Title : 코멘트 가져오기
	 * <p>
	 * 예외처리 포함해서 가져옴
	 * </p>
	 * 
	 * @param id
	 *            코멘트 아이디
	 * 
	 * @throws NoCommentException
	 *             if comment is null
	 */
	public Comment getComment(long id) throws NoCommentException {
		Comment comment = commentRepository.findOne(id);
		if (comment == null)
			throw new NoCommentException(id);// 해당하는 게시물이 없을 때

		return comment;
	}
}
