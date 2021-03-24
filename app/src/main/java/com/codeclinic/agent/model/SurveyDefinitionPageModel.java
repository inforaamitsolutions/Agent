package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SurveyDefinitionPageModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pageName")
    @Expose
    private String pageName;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("pageOrder")
    @Expose
    private Object pageOrder;
    @SerializedName("randomizeQuestions")
    @Expose
    private Boolean randomizeQuestions;
    @SerializedName("surveyDefinitionId")
    @Expose
    private Integer surveyDefinitionId;
    @SerializedName("questions")
    @Expose
    private List<QuestionsListModel> questions = null;

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

    public Object getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(Object pageOrder) {
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

    public List<QuestionsListModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionsListModel> questions) {
        this.questions = questions;
    }
}
