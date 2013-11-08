package org.nhnnext.web;

public class WebError {
	private String name;
	private int no;
	private String desc;
	private boolean err = true;
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
	public static WebError error( String name, String desc) {
		return new WebError(0, name, desc);
	}
}
