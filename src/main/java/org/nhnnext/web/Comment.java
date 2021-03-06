package org.nhnnext.web;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.nhnnext.repository.CommentRepository;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = 1000, nullable = false)
	private String contents;

	@JsonIgnore
	@ManyToOne
	private Board board;
	
	@JsonIgnore
	@ManyToOne
	private Comment comment;

	@OneToMany(mappedBy = "comment", fetch = FetchType.EAGER)
	private List<Comment> comments;

	@ManyToOne
	private User user;

	
	public Comment() { // 반드시 빈 생성자를 생성해야 한다.

	}

	public Comment(Board board, Comment comment, String contents) {
		this.board = board;
		this.comment = comment;
		this.contents = contents;
		
	}

	public Comment(Board board, String contents) {
		this.board = board;
		this.contents = contents;
		
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	// 메소드
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}
	public Long getBid() {
		return board.getId();
	}

	public Comment getComment() {
		return this.comment;
	}

	public String getHtmlReply() {
		StringBuilder builder = new StringBuilder();
		builder.append("<div class='comment-reply needLogin' style='display:none;'>");
		builder.append("<form action='/board/" + board.getId() + "/" + this.id
				+ "/comment_ok' method='post'>");
		builder.append("<textarea name='contents' cols='50' rows='3' placeholder='댓글 쓰세요.'></textarea>");
		builder.append("<button class='commentButton'>작성</button></form></div>");
		return builder.toString();
	}

	public String getHtml() {
		String html = "";
		html = "<li id='comment"+this.id+"'>";
		html += "<div class='comment-list'><div class='comment-indent'>";
		html += "<p class='comment-writer'>" + this.user.getName() + "</p>";
		html += "<p class='comment-contents'>" + this.contents + "</p>";
		html += "<p class='comment-delete' board_id="+this.board.getId()+" comment_id="+this.id+">삭제버튼</p>";
		html += "</div>";
		if (comments != null && comments.size() > 0) {
			html += "<ul>";
			for (Comment c : comments) {
				html += c.getHtml();
			}
			html += "</ul>";
		}
		html += getHtmlReply() + "</div></li>";
		return html;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", contents=" + contents + ", board="
				+ board.getId() + ", user=" + user.getUserid() + "]";
	}

	public void deleteComments(CommentRepository commentRepository) {
		if (comments == null)
			return;

		for (Comment comment : comments) {
			comment.deleteComments(commentRepository);
			commentRepository.delete(comment);
		}
		comments.clear();
	}

}
