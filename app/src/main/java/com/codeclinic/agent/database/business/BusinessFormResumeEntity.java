package com.codeclinic.agent.database.business;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "BusinessFormResume")
public class BusinessFormResumeEntity {
    @ColumnInfo(name = "mainId")
    @PrimaryKey(autoGenerate = false)
    @Expose
    private long mainId;

    @ColumnInfo(name = "surveyQue")
    @Expose
    private String surveyQuestions;

    @ColumnInfo(name = "optionQue")
    @Expose
    private String optionQuestions;

    public long getMainId() {
        return mainId;
    }

    public void setMainId(long mainId) {
        this.mainId = mainId;
    }

    public String getSurveyQuestions() {
        return surveyQuestions;
    }

    public void setSurveyQuestions(String surveyQuestions) {
        this.surveyQuestions = surveyQuestions;
    }

    public String getOptionQuestions() {
        return optionQuestions;
    }

    public void setOptionQuestions(String optionQuestions) {
        this.optionQuestions = optionQuestions;
    }
}
