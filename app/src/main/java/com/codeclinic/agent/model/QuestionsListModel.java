package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionsListModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("questionText")
    @Expose
    private String questionText;
    @SerializedName("fieldName")
    @Expose
    private String fieldName;
    @SerializedName("required")
    @Expose
    private Boolean required;
    @SerializedName("tip")
    @Expose
    private String tip;
    @SerializedName("regularExpression")
    @Expose
    private String regularExpression;
    @SerializedName("allowOtherTextBox")
    @Expose
    private Boolean allowOtherTextBox;
    @SerializedName("questionOrder")
    @Expose
    private Integer questionOrder;
    @SerializedName("min")
    @Expose
    private Integer min;
    @SerializedName("max")
    @Expose
    private Integer max;
    @SerializedName("fieldType")
    @Expose
    private String fieldType;
    @SerializedName("surveyDefinitionPageId")
    @Expose
    private Integer surveyDefinitionPageId;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("options")
    @Expose
    private List<OptionsListModel> options = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public Boolean getAllowOtherTextBox() {
        return allowOtherTextBox;
    }

    public void setAllowOtherTextBox(Boolean allowOtherTextBox) {
        this.allowOtherTextBox = allowOtherTextBox;
    }

    public Integer getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(Integer questionOrder) {
        this.questionOrder = questionOrder;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getSurveyDefinitionPageId() {
        return surveyDefinitionPageId;
    }

    public void setSurveyDefinitionPageId(Integer surveyDefinitionPageId) {
        this.surveyDefinitionPageId = surveyDefinitionPageId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<OptionsListModel> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsListModel> options) {
        this.options = options;
    }
}
