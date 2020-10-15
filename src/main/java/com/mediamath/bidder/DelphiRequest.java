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
}
