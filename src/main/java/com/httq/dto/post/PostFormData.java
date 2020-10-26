package com.httq.dto.post;

import com.httq.model.PostStatusList;

import java.util.List;

public class PostFormData {
	private String title;

	private String subTitle;

	private String seo;

	private String content;

	private String thumbnail;

	private String contentPlainText;

	private PostStatusList status;

	private List<String> tags;

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

	public PostStatusList getStatus() {
		return status;
	}

	public void setStatus(PostStatusList status) {
		this.status = status;
	}

	public String getSeo() {
		return seo;
	}

	public void setSeo(String seo) {
		this.seo = seo;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
