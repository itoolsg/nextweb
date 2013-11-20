package org.nhnnext.web;

import org.springframework.ui.Model;


public class WebError {
	private String name;
	private int no;
	private String desc;
	private boolean err = true;
	private String link;
	private WebError(int no, String name, String desc) {
		this.no = no;
		this.name = name;
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
	public String getName() {
		return name;
	}
	public int getNo() {
		return no;
	}
	public boolean getErr() {
		return err;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLink() {
		return link;
	}
	public static WebError error( String name, String desc) {
		return new WebError(0, name, desc);
	}
	public static WebError errorLink(String desc, String link) {
		WebError err =  new WebError(0, "ERR", desc);
		err.setLink(link);
		return err;
	}
	public static String showError(Model model, WebError err) {
		model.addAttribute("error", err);
		return "error";
	}
	public static String showError(Model model, String desc, String link) {
		WebError err =  new WebError(0, "ERR", desc);
		err.setLink(link);
		
		model.addAttribute("error", err);
		return "error";
	}
}
