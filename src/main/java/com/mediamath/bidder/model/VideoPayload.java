package com.mediamath.bidder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoPayload {
    private String id;
    private Impression imp;
    private Site site;
    private Device device;

    private double bidfloor;
    private boolean secure;

    public Impression getImp() {
        return imp;
    }

    public void setImp(Impression imp) {
        this.imp = imp;
    }

    public VideoPayload() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public double getBidfloor() {
        return bidfloor;
    }

    public void setBidfloor(double bidfloor) {
        this.bidfloor = bidfloor;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    @Override
    public String toString() {
        return "VideoPayload{" +
                "id='" + id + '\'' +
                ", imp=" + imp +
                ", site=" + site +
                ", device=" + device +
                ", bidfloor=" + bidfloor +
                ", secure=" + secure +
                '}';
    }

}
