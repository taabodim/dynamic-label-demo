package com.mediamath.bidder.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Label {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Source source;

    private String field;

    private Boolean enabled;

    private Boolean experimental;

    @Enumerated(EnumType.STRING)
    private Operation operation;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getExperimental() {
        return experimental;
    }

    public void setExperimental(Boolean experimental) {
        this.experimental = experimental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label)) return false;
        Label label = (Label) o;
        return Objects.equals(getId(), label.getId()) &&
                getSource() == label.getSource() &&
                Objects.equals(getField(), label.getField()) &&
                Objects.equals(getEnabled(), label.getEnabled()) &&
                Objects.equals(getExperimental(), label.getExperimental()) &&
                getOperation() == label.getOperation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSource(), getField(), getEnabled(), getExperimental(), getOperation());
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", source=" + source +
                ", field='" + field + '\'' +
                ", enabled=" + enabled +
                ", experimental=" + experimental +
                ", operation=" + operation +
                '}';
    }
}
