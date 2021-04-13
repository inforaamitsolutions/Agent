package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InteractionSubmissionModel {
    @SerializedName("field")
    @Expose
    private Object field;
    @SerializedName("value")
    @Expose
    private String value;

    public Object getField() {
        return field;
    }

    public void setField(Object field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
