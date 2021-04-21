package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InteractionCategoryListModel {
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("dateTimeCreated")
    @Expose
    private String dateTimeCreated;
    @SerializedName("lastModifiedBy")
    @Expose
    private Object lastModifiedBy;
    @SerializedName("lastModifiedDate")
    @Expose
    private String lastModifiedDate;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("interactionCategoryFields")
    @Expose
    private List<InteractionCategoryFieldListModel> interactionCategoryFieldList = null;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Object getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Object lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public List<InteractionCategoryFieldListModel> getInteractionCategoryFieldList() {
        return interactionCategoryFieldList;
    }

    public void setInteractionCategoryFieldList(List<InteractionCategoryFieldListModel> interactionCategoryFieldList) {
        this.interactionCategoryFieldList = interactionCategoryFieldList;
    }

    @Override
    public String toString() {
        return name;
    }
}
