package com.codeclinic.agent.model.lead;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "LeadQuestionsToFollow")
public class LeadQuestionToFollowModel {
    @ColumnInfo(name = "questionToFollowMainId")
    @PrimaryKey(autoGenerate = true)
    @Expose
    private long mainId;

    @ColumnInfo(name = "questionId")
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "questionText")
    @SerializedName("questionText")
    @Expose
    private String questionText;

    @ColumnInfo(name = "fieldName")
    @SerializedName("fieldName")
    @Expose
    private String fieldName;

    @ColumnInfo(name = "required")
    @SerializedName("required")
    @Expose
    private Boolean required;

    @ColumnInfo(name = "tip")
    @SerializedName("tip")
    @Expose
    private String tip;

    @ColumnInfo(name = "regularExpression")
    @SerializedName("regularExpression")
    @Expose
    private String regularExpression;

    @ColumnInfo(name = "allowOtherTextBox")
    @SerializedName("allowOtherTextBox")
    @Expose
    private Boolean allowOtherTextBox;

    @ColumnInfo(name = "fieldType")
    @SerializedName("fieldType")
    @Expose
    private String fieldType;

    @ColumnInfo(name = "surveyDefinitionPageId")
    @SerializedName("surveyDefinitionPageId")
    @Expose
    private Integer surveyDefinitionPageId;

    @ColumnInfo(name = "parentQuestionId")
    @SerializedName("parentQuestionId")
    @Expose
    private Integer parentQuestionId;

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose
    private Boolean active;


    public long getMainId() {
        return mainId;
    }

    public void setMainId(long mainId) {
        this.mainId = mainId;
    }

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

    public Integer getParentQuestionId() {
        return parentQuestionId;
    }

    public void setParentQuestionId(Integer parentQuestionId) {
        this.parentQuestionId = parentQuestionId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
