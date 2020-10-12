package com.httq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String subTitle;

    private String thumbnail;

    @Column(name = "view", nullable = false)
    private long view;

    @Column(name = "view_trend", nullable = false)
    private long viewTrend;

    @Column(name = "seo", unique = true)
    private String seo;

    @Lob
    private String content;

    @Lob
    private String contentPlainText;

    private PostStatusList status;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name = "post_tags", joinColumns = {@JoinColumn(name = "post_id",referencedColumnName = "id")},
               inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    @JsonIgnoreProperties
    private List<Tag> tags;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public long getViewTrend() {
        return viewTrend;
    }

    public void setViewTrend(long viewTrend) {
        this.viewTrend = viewTrend;
    }
}
