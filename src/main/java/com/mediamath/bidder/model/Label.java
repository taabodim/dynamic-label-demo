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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label)) return false;
        Label label = (Label) o;
        return Objects.equals(getSource(), label.getSource()) &&
                Objects.equals(getId(), label.getId()) &&
                Objects.equals(getField(), label.getField()) &&
                Objects.equals(getOperation(), label.getOperation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSource(), getId(), getField(), getOperation());
    }

    @Override
    public String toString() {
        return "Label{" +
                "id='" + id + '\'' +
                "name='" + source + '\'' +
                ", value='" + field + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}
