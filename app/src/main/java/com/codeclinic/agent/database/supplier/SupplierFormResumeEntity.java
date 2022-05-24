package com.codeclinic.agent.database.supplier;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "SupplierFormResume")
public class SupplierFormResumeEntity {
    @ColumnInfo(name = "mainId")
    @PrimaryKey(autoGenerate = false)
    @Expose
    private long mainId;

    @ColumnInfo(name = "surveyQue")
    @Expose
    private String surveyQuestions;

    @ColumnInfo(name = "surveyName")
    @Expose
    private String surveyName;

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

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getOptionQuestions() {
        return optionQuestions;
    }

    public void setOptionQuestions(String optionQuestions) {
        this.optionQuestions = optionQuestions;
    }
}
