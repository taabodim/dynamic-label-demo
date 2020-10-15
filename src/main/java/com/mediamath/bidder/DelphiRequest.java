package com.mediamath.bidder;

import com.mediamath.bidder.model.LabelDelphiEntry;
import com.mediamath.bidder.model.VideoPayload;

import java.util.ArrayList;
import java.util.List;

public class DelphiRequest {

    private double bidfloor;
    private boolean secure;

    private List<LabelDelphiEntry> labelEntries = new ArrayList<>();

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

    public List<LabelDelphiEntry> getLabelEntries() {
        return labelEntries;
    }

    public void setLabelEntries(List<LabelDelphiEntry> labelEntries) {
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
