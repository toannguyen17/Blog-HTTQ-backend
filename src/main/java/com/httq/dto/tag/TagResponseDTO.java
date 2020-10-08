package com.httq.dto.tag;

public class TagResponseDTO {
    private Long id;

    private String tag;

    public TagResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
