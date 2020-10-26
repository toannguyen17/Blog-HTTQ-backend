package com.httq.dto.home;

import com.httq.dto.post.reViewPost;

import java.util.List;

public class HomeResponse {
    private List<reViewPost> topTrend;
    private List<reViewPost> lastPost;

    public HomeResponse(){}

    public List<reViewPost> getLastPost() {
        return lastPost;
    }

    public void setLastPost(List<reViewPost> lastPost) {
        this.lastPost = lastPost;
    }

    public List<reViewPost> getTopTrend() {
        return topTrend;
    }

    public void setTopTrend(List<reViewPost> topTrend) {
        this.topTrend = topTrend;
    }
}
