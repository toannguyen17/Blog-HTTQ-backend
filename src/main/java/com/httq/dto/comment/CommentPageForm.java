package com.httq.dto.comment;

public class CommentPageForm {
	private Long id;
	private Integer page;
	public CommentPageForm(){
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
