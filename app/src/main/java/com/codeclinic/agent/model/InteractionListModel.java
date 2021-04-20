package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InteractionListModel {
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
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
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("interactionTypeName")
    @Expose
    private String interactionTypeName;
    @SerializedName("interactionCategoryName")
    @Expose
    private String interactionCategoryName;
    @SerializedName("interactionSubmissions")
    @Expose
    private List<InteractionSubmissionModel> interactionSubmissions = null;

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInteractionTypeName() {
        return interactionTypeName;
    }

    public void setInteractionTypeName(String interactionTypeName) {
        this.interactionTypeName = interactionTypeName;
    }

    public String getInteractionCategoryName() {
        return interactionCategoryName;
    }

    public void setInteractionCategoryName(String interactionCategoryName) {
        this.interactionCategoryName = interactionCategoryName;
    }

    public List<InteractionSubmissionModel> getInteractionSubmissions() {
        return interactionSubmissions;
    }

    public void setInteractionSubmissions(List<InteractionSubmissionModel> interactionSubmissions) {
        this.interactionSubmissions = interactionSubmissions;
    }
}
