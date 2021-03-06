package org.nhnnext.web;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityNotFoundException;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.nhnnext.log.Mylog;
import org.nhnnext.repository.CommentRepository;

@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	// 필드
	@Column(length = 50, nullable = false)
	private String title;

	@Column(length = 1000, nullable = false)
	private String contents;

	@Column(length = 100)
	private String filename;

	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
	private List<Comment> comments;

	@ManyToOne
	private User user;

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

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public boolean matchId(String id) {
		return id.equals(user.getUserid());
	}

	public String createContentsTag() {
		String tagContents = contents.replace("\n", "<br />");
		return tagContents;
	}


	@Override
	public String toString() {
		return "Board [id=" + id + ", title=" + title + ", contents="
				+ contents + ", user=" + user + "]";
	}

	public void deleteComments(CommentRepository commentRepository) {
		if (comments == null)
			return;

		for (Comment comment : comments) {

			if (comment.getComment() != null)
				continue;

			comment.deleteComments(commentRepository);
			commentRepository.delete(comment);
		}
	}
}
