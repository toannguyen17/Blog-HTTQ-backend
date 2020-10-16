package com.httq.dto.comment;

import com.httq.dto.post.PostAuth;
import java.time.LocalDateTime;

public class CommentDTO {
	private Long id;

	private String content;

	private PostAuth auth;

	private Long like;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public CommentDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PostAuth getAuth() {
		return auth;
	}

	public void setAuth(PostAuth auth) {
		this.auth = auth;
	}

	public Long getLike() {
		return like;
	}

	public void setLike(Long like) {
		this.like = like;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
