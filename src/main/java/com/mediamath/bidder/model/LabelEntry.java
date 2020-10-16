package com.mediamath.bidder.model;

import java.util.Objects;

public class LabelEntry {
    private Source source;
    private Integer id;
    private String field;
    private String value;

    public LabelEntry() {
        //jackson needs me
    }

    public LabelEntry(Label label, String value) {
        id = label.getId();
        field = label.getField();
        source = label.getSource();
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(o instanceof LabelEntry)) return false;
        LabelEntry label = (LabelEntry) o;
        return Objects.equals(getSource(), label.getSource()) &&
                Objects.equals(getValue(), label.getValue()) &&
                Objects.equals(getId(), label.getId()) &&
                Objects.equals(getField(), label.getField());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getValue(), getId(), getField());
    }

    @Override
    public String toString() {
        return "Label{" +
                "name='" + source + '\'' +
                ", id='" + id + '\'' +
                ", field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
