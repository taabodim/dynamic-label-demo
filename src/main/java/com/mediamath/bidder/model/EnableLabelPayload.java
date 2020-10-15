package com.mediamath.bidder.model;

import java.util.Objects;

public class EnableLabelPayload {

    private Integer id;

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UpdateLabelPayload{" +
                "id=" + id +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnableLabelPayload)) return false;
        EnableLabelPayload that = (EnableLabelPayload) o;
        return isEnabled() == that.isEnabled() &&
                getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), isEnabled());
    }
}
