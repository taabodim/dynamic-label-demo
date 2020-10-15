package com.mediamath.bidder.model;

import java.util.ArrayList;
import java.util.List;

public class LabelLogColumn {
    private List<LabelLogEntry> entries = new ArrayList<>();

    public List<LabelLogEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LabelLogEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(LabelLogEntry labelLogEntry) {
        entries.add(labelLogEntry);
    }
}
