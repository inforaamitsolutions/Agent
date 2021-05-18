package com.codeclinic.agent.model.businesDataUpdate;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.codeclinic.agent.typeConverters.business.BusinessDataOptionsConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "BusinessQuestions")
public class BusinessDataQuestionListModel {
    @ColumnInfo(name = "questionMainId")
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

    @ColumnInfo(name = "questionOrder")
    @SerializedName("questionOrder")
    @Expose
    private Integer questionOrder;

    @ColumnInfo(name = "min")
    @SerializedName("min")
    @Expose
    private Integer min;

    @ColumnInfo(name = "max")
    @SerializedName("max")
    @Expose
    private Integer max;

    @ColumnInfo(name = "fieldType")
    @SerializedName("fieldType")
    @Expose
    private String fieldType;

    @ColumnInfo(name = "surveyDefinitionPageId")
    @SerializedName("surveyDefinitionPageId")
    @Expose
    private Integer surveyDefinitionPageId;

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose
    private Boolean active;


    @SerializedName("options")
    @Expose
    @TypeConverters(BusinessDataOptionsConverter.class)
    private List<BusinessDataOptionsListModel> options = null;

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

    public List<BusinessDataOptionsListModel> getOptions() {
        return options;
    }

    public void setOptions(List<BusinessDataOptionsListModel> options) {
        this.options = options;
    }
}
