package com.seidor.inventario.navigation;

public class BreadCrumb {

	private String text;
	private String bookmark;
	
	public BreadCrumb() {}
	
	public BreadCrumb(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBookmark() {
		return bookmark;
	}

	public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}
	
}
