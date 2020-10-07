package com.httq.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "post_tags")
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Tag tag;

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    public PostTag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
