package com.httq.dto.post;

public class PostFormData {
	private String title;

	private String subTitle;

	private String content;

	private String contentPlainText;

	private String status;

	public PostFormData(){

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentPlainText() {
		return contentPlainText;
	}

	public void setContentPlainText(String contentPlainText) {
		this.contentPlainText = contentPlainText;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
