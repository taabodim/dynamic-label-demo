package com.mediamath.bidder.model;

public class Device {
    private String id;
    private Geo geo;

    public Device() {
    }

    public String getId() {
        return id;
    }

    public Device(Geo geo) {
        this.geo = geo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }
}
