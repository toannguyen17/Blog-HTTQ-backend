package com.httq.model;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(referencedColumnName = "id")
    private User userId;

    @JoinColumn(referencedColumnName = "id")
    private Post postId;

    @Lob
    private String content;

    @Lob
    private String contentPlainText;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
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
}
