package com.mediamath.bidder.model;

import java.util.Objects;

public class LabelLogEntry {
    private Source source;
    private String field;
    private String value;

    public LabelLogEntry(Label label, String value) {
        field = label.getField();
        source = label.getSource();
        this.value = value;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelLogEntry)) return false;
        LabelLogEntry label = (LabelLogEntry) o;
        return Objects.equals(getSource(), label.getSource()) &&
                Objects.equals(getValue(), label.getValue()) &&
                Objects.equals(getField(), label.getField());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getValue(), getField());
    }

    @Override
    public String toString() {
        return "Label{" +
                "name='" + source + '\'' +
                ", field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
