package com.httq.model;

import javax.persistence.*;

@Entity
@Table(name = "post_tags")
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(referencedColumnName = "id")
    private Tag tagId;

    @JoinColumn(referencedColumnName = "id")
    private Post postId;

    public PostTag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tag getTagId() {
        return tagId;
    }

    public void setTagId(Tag tagId) {
        this.tagId = tagId;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }
}
