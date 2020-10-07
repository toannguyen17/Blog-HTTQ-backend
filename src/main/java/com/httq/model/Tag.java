package com.httq.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private long counter;

    public Tag() {
        this.counter = 0;
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

    public long getCounter() {
        return counter;
    }

    public void resetCounter() {
        this.counter = 0;
    }

    public void count(){
        this.counter++;
    }
}
