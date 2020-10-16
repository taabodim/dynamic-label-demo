package com.mediamath.bidder.model;

public class Impression {
    private String id;
    private Video video;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
