package org.nhnnext.web;

import javax.servlet.http.HttpSession;

import org.nhnnext.exception.InvalidUserException;
import org.nhnnext.exception.NoLoginException;
import org.nhnnext.exception.NoUserException;
import org.nhnnext.log.Mylog;
import org.nhnnext.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author younkue
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	private static final Logger log = LoggerFactory
			.getLogger(UserController.class);
	
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
	 * @throws NoLoginException
	 *             if user is not in session
	 */
	public User getLoginUser(HttpSession session) throws NoUserException,
			NoLoginException {
		// session을 통해 가져옴
		String userid = (String) session.getAttribute("userid");

		if (userid == null)
			throw new NoLoginException(); // 로그인이 안되어 있을 때

		User user = userRepository.findOne(userid);

		if (user == null)
			throw new NoUserException(session); // 해당하는 유저가 없을 때

		return user;
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
	 * Title : 로그인 폼
	 * <p>
	 * http://localhost:8080/user/login
	 * </p>
	 * 
	 * */
	@RequestMapping("/login")
	public String showForm(HttpSession session) {

		try {
			User user = getLoginUser(session);
			return "redirect:/";
		} catch (NoUserException e) {
		} catch (NoLoginException e) {
		}
		return "login/form";
	}

	/**
	 * Title : 로그인 체크
	 * <p>
	 * http://localhost:8080/user/login_check
	 * </p>
	 * 
	 * @throws NullPointerException
	 *             if userid,password is null or empty
	 * */
	@RequestMapping(value = "/login_check", method = RequestMethod.POST)
	public String login(String userid, String password, HttpSession session,
			Model model) {
		// TODO userid에 해당하는 사용를 데이터베이스에서 조회
		// TODO 사용자가 입력한 비밀번호와 데이터베이스에서 조회한 사용자 비밀번호가 같은지 확인
		try {
			if (userid == null || userid.equals(""))
				throw new NullPointerException("No User Id");

			if (password == null || password.equals(""))
				throw new NullPointerException("No User password");

			User user = getUser(userid);

			// 비번 매치
			if (!user.matchPs(password))
				throw new InvalidUserException("No User password");

			session.setAttribute("userid", userid);
			
			log.info("Hello Login ~ : {}", userid);
			return "redirect:/board";

		} catch (NullPointerException e) {
			return WebError.showError(model, Strings.ERR_BLANK,
					Strings.LINK_BACK);
		} catch (NoUserException e) {
			return WebError.showError(model, Strings.ERR_NOUSER,
					Strings.LINK_BACK);
		} catch (InvalidUserException e) {
			return WebError.showError(model, Strings.ERR_NOMATCHPASSWORD,
					Strings.LINK_BACK);
		} catch (Exception e) {

		}
		return "redirect:/user/login";
	}

	/**
	 * Title : 회원가입 폼
	 * <p>
	 * http://localhost:8080/user/signup
	 * </p>
	 * 
	 * */
	@RequestMapping(value = "/signup")
	public String signup() {
		return "/login/signup";
	}

	/**
	 * Title : 가입 완료 체크
	 * <p>
	 * http://localhost:8080/user/join
	 * </p>
	 * 
	 * @throws NullPointerException
	 *             if userid,password is null or empty
	 * @throws InvalidUserException
	 *             if user is invalid
	 * */
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String signup(User user, Model model) {
		try {
			if (user == null)
				throw new NullPointerException("User is Null");
			if (!user.isValidUser())
				throw new InvalidUserException();

			User savedUser = userRepository.save(user);
			log.info("User registered : {}", savedUser);
			
			
			return "redirect:/user/login";

		} catch (NullPointerException e) {
			return WebError.showError(model, Strings.ERR_BLANK,
					Strings.LINK_BACK);
		} catch (InvalidUserException e) {
			return WebError.showError(model, Strings.ERR_INVALIDUSER,
					Strings.LINK_BACK);
		} catch (Exception e) {
			return WebError.showError(model, Strings.ERR_UNKNOWN,
					Strings.LINK_BACK);
		}
		// return "redirect:/user/signup";
	}

	/**
	 * Title : 로그아웃
	 * <p>
	 * http://localhost:8080/user/logout
	 * </p>
	 * 
	 * */
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		log.info("Good Bye : {}", session.getAttribute("userid"));
		
		session.removeAttribute("userid");
		return "redirect:/board";
	}
}