package com.codeclinic.agent.model.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.codeclinic.agent.typeConverters.customer.CustomerQuestionsGsonTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "CustomerSurveyPages")
public class CustomerSurveyDefinitionPageModel {

    @ColumnInfo(name = "surveyMainId")
    @PrimaryKey(autoGenerate = true)
    @Expose
    private long mainId;

    @ColumnInfo(name = "surveyId")
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "surveyTitle")
    @SerializedName("pageTitle")
    @Expose
    private String title;

    @ColumnInfo(name = "pageName")
    @SerializedName("pageName")
    @Expose
    private String pageName;

    @ColumnInfo(name = "instructions")
    @SerializedName("instructions")
    @Expose
    private String instructions;

    @ColumnInfo(name = "pageOrder")
    @SerializedName("pageOrder")
    @Expose
    private int pageOrder;

    @ColumnInfo(name = "randomizeQuestions")
    @SerializedName("randomizeQuestions")
    @Expose
    private Boolean randomizeQuestions;

    @ColumnInfo(name = "surveyDefinitionId")
    @SerializedName("surveyDefinitionId")
    @Expose
    private Integer surveyDefinitionId;

    @SerializedName("questions")
    @Expose
    @TypeConverters(CustomerQuestionsGsonTypeConverter.class)
    private List<CustomerQuestionsListModel> questions = null;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(int pageOrder) {
        this.pageOrder = pageOrder;
    }

    public Boolean getRandomizeQuestions() {
        return randomizeQuestions;
    }

    public void setRandomizeQuestions(Boolean randomizeQuestions) {
        this.randomizeQuestions = randomizeQuestions;
    }

    public Integer getSurveyDefinitionId() {
        return surveyDefinitionId;
    }

    public void setSurveyDefinitionId(Integer surveyDefinitionId) {
        this.surveyDefinitionId = surveyDefinitionId;
    }

    public List<CustomerQuestionsListModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CustomerQuestionsListModel> questions) {
        this.questions = questions;
    }
}
