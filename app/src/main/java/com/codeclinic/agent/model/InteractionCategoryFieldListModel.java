package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InteractionCategoryFieldListModel {
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("dateTimeCreated")
    @Expose
    private Object dateTimeCreated;
    @SerializedName("lastModifiedBy")
    @Expose
    private Object lastModifiedBy;
    @SerializedName("lastModifiedDate")
    @Expose
    private Object lastModifiedDate;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("interactionCategoryName")
    @Expose
    private Object interactionCategoryName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("placeHolder")
    @Expose
    private Object placeHolder;
    @SerializedName("position")
    @Expose
    private String position;
    @SerializedName("hasOptions")
    @Expose
    private Boolean hasOptions;
    @SerializedName("hasConditionField")
    @Expose
    private Boolean hasConditionField;
    @SerializedName("defaultValue")
    @Expose
    private Object defaultValue;
    @SerializedName("required")
    @Expose
    private Boolean required;
    @SerializedName("validationRegex")
    @Expose
    private String validationRegex;
    @SerializedName("fieldType")
    @Expose
    private String fieldType;
    @SerializedName("fieldOptions")
    @Expose
    private List<InteractionFieldOptionListModel> interactionFieldOptionList = null;

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Object dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Object getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Object lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Object getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Object lastModifiedDate) {
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

    public Object getInteractionCategoryName() {
        return interactionCategoryName;
    }

    public void setInteractionCategoryName(Object interactionCategoryName) {
        this.interactionCategoryName = interactionCategoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(Object placeHolder) {
        this.placeHolder = placeHolder;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getHasOptions() {
        return hasOptions;
    }

    public void setHasOptions(Boolean hasOptions) {
        this.hasOptions = hasOptions;
    }

    public Boolean getHasConditionField() {
        return hasConditionField;
    }

    public void setHasConditionField(Boolean hasConditionField) {
        this.hasConditionField = hasConditionField;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getValidationRegex() {
        return validationRegex;
    }

    public void setValidationRegex(String validationRegex) {
        this.validationRegex = validationRegex;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public List<InteractionFieldOptionListModel> getInteractionFieldOptionList() {
        return interactionFieldOptionList;
    }

    public void setInteractionFieldOptionList(List<InteractionFieldOptionListModel> interactionFieldOptionList) {
        this.interactionFieldOptionList = interactionFieldOptionList;
    }
}
