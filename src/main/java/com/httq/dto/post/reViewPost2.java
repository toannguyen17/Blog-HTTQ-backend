package com.httq.dto.post;

import com.httq.app.helper.StaticUtils;
import java.time.LocalDateTime;

public class reViewPost2 {
	private String thumbnail;
	private String title;
	private String seo;
	private String contentPlainText;
	private LocalDateTime createdAt;

	public reViewPost2() {
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSeo() {
		return seo;
	}

	public void setSeo(String seo) {
		this.seo = seo;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getContentPlainText() {
		return contentPlainText;
	}

	public void setContentPlainText(String contentPlainText) {
		this.contentPlainText = StaticUtils.getHelpers().cutText(contentPlainText, 0, 175);
	}
}
