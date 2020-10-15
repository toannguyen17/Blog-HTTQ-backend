package com.httq.dto.post;

import com.httq.model.PostStatusList;

import java.time.LocalDateTime;

public class reViewPost {
    private String thumbnail;
    private String title;
    private PostStatusList status;
    private String seo;
    private String description;
    private LocalDateTime createdAt;

    public reViewPost(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public PostStatusList getStatus() {
        return status;
    }

    public void setStatus(PostStatusList status) {
        this.status = status;
    }
}
