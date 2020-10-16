package com.mediamath.bidder;

import com.mediamath.bidder.model.LabelEntry;
import com.mediamath.bidder.model.VideoPayload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DelphiRequest {

    private double bidfloor;
    private boolean secure;

    private Map<Integer, LabelEntry> labelEntries = new HashMap<>();

    public DelphiRequest() {
        // jackson needs me for deserialization
    }

    public DelphiRequest(VideoPayload vp) {
        bidfloor = vp.getBidfloor();
        secure = vp.isSecure();
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

    public Map<Integer, LabelEntry> getLabelEntries() {
        return labelEntries;
    }

    public void setLabelEntries(Map<Integer, LabelEntry> labelEntries) {
        this.labelEntries = labelEntries;
    }

    @Override
    public String toString() {
        return "DelphiRequest{" +
                "bidfloor=" + bidfloor +
                ", secure=" + secure +
                ", labelEntries=" + labelEntries +
                '}';
    }
}
