package com.mediamath.bidder.model;

import java.util.Objects;

public class AddLabelPayload {

    private Source source;

    private String field;

    private Operation operation;

    private boolean enabled;

    private boolean experimental;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExperimental() {
        return experimental;
    }

    public void setExperimental(boolean experimental) {
        this.experimental = experimental;
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

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "UpdateLabelPayload{" +
                ", source=" + source +
                ", field='" + field + '\'' +
                ", operation=" + operation +
                ", enabled=" + enabled +
                ", experimental=" + experimental +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddLabelPayload)) return false;
        AddLabelPayload that = (AddLabelPayload) o;
        return isEnabled() == that.isEnabled() &&
                isExperimental() == that.isExperimental() &&
                getSource() == that.getSource() &&
                getField().equals(that.getField()) &&
                getOperation() == that.getOperation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getField(), getOperation(), isEnabled(), isExperimental());
    }
}
