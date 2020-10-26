package com.httq.dto.comment;

public class CommentCreate {
	private Long post;
	private String comment;

	public CommentCreate() {
	}

	public Long getPost() {
		return post;
	}

	public void setPost(Long post) {
		this.post = post;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
