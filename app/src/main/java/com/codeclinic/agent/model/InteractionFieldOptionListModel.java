package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InteractionFieldOptionListModel {

    @SerializedName("dateTimeCreated")
    @Expose
    private Object dateTimeCreated;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("ipAddress")
    @Expose
    private Object ipAddress;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("interactionCategoryField")
    @Expose
    private Object interactionCategoryField;
    @SerializedName("conditionFieldId")
    @Expose
    private Object conditionFieldId;
    @SerializedName("new")
    @Expose
    private Boolean _new;

    public Object getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Object dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Object getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Object ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Object getInteractionCategoryField() {
        return interactionCategoryField;
    }

    public void setInteractionCategoryField(Object interactionCategoryField) {
        this.interactionCategoryField = interactionCategoryField;
    }

    public Object getConditionFieldId() {
        return conditionFieldId;
    }

    public void setConditionFieldId(Object conditionFieldId) {
        this.conditionFieldId = conditionFieldId;
    }

    public Boolean getNew() {
        return _new;
    }

    public void setNew(Boolean _new) {
        this._new = _new;
    }

}
