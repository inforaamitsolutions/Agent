package com.codeclinic.agent.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyActionListModel {

    @SerializedName("surveyName")
    @Expose
    private String surveyName;
    @SerializedName("action")
    @Expose
    private String action;

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
