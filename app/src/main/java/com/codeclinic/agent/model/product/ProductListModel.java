package com.codeclinic.agent.model.product;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListModel {
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("surveyActions")
    @Expose
    private List<SurveyActionListModel> surveyActions = null;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<SurveyActionListModel> getSurveyActions() {
        return surveyActions;
    }

    public void setSurveyActions(List<SurveyActionListModel> surveyActions) {
        this.surveyActions = surveyActions;
    }


    @Override
    public String toString() {
        return productName;
    }
}
