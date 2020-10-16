package com.mediamath.bidder.model;

import java.util.ArrayList;
import java.util.List;

public class LabelLogColumn {
    private List<LabelEntry> entries = new ArrayList<>();

    public List<LabelEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<LabelEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(LabelEntry labelEntry) {
        entries.add(labelEntry);
    }
}
