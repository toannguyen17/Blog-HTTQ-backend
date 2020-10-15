package com.httq.dto.post;

import java.util.List;

public class PostFindByUser {
	private String key;
	private Integer page;
	private List<String> tags;

	public PostFindByUser(){
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
