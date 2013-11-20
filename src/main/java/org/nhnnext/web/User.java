package org.nhnnext.web;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long no = 0l;
	// 필드
	
	
	@Id
	@Column(length = 50, nullable = false)
	private String userid;

	@Column(length = 1000, nullable = false)
	private String password;
	
	@Column(length = 100, nullable = false)
	private String name;
	
	//@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	//private List<Board> boards;

	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean matchPs(String ps) {
		return this.password.equals(ps);
	}
	public boolean isValidUser() {
		if(userid == null || userid.equals(""))
			return false;
		if(password == null || password.equals(""))
			return false;
		if(name == null || name.equals(""))
			return false;
		
		return true;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	@Column(length = 100)
	private String comment;


	@Override
	public String toString() {
		return "User [UserId=" + userid + "]";
	}
}
