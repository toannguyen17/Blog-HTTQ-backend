package com.httq.dto.tag;

import io.swagger.annotations.ApiModelProperty;

public class TagResponseDTO {
    @ApiModelProperty(position = 0)
    private Long id;

    @ApiModelProperty(position = 1)
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
