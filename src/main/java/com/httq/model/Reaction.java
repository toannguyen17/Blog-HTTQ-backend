package com.httq.model;

import javax.persistence.*;

@Entity
@Table(name = "reactions")
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(referencedColumnName = "id")
    private Post postId;

    @JoinColumn(referencedColumnName = "id")
    private User userId;

    private PostReaction type;

    public Reaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public PostReaction getType() {
        return type;
    }

    public void setType(PostReaction type) {
        this.type = type;
    }
}
