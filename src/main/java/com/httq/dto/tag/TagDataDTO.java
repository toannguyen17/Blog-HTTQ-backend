package com.httq.dto.tag;

import io.swagger.annotations.ApiModelProperty;

public class TagDataDTO {
    @ApiModelProperty
    private String tag;

    public TagDataDTO() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
