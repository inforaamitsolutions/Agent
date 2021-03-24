package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OptionsListModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("value")
    @Expose
    private Object value;
    @SerializedName("order")
    @Expose
    private Object order;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("questionToFollow")
    @Expose
    private Object questionToFollow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getOrder() {
        return order;
    }

    public void setOrder(Object order) {
        this.order = order;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Object getQuestionToFollow() {
        return questionToFollow;
    }

    public void setQuestionToFollow(Object questionToFollow) {
        this.questionToFollow = questionToFollow;
    }

    @Override
    public String toString() {
        return label;
    }
}
