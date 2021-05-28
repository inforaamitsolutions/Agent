package com.codeclinic.agent.database.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "CustomerFormResume")
public class CustomerFormResumeEntity {
    @ColumnInfo(name = "mainId")
    @PrimaryKey(autoGenerate = false)
    @Expose
    private long mainId;

    @ColumnInfo(name = "name")
    @Expose
    private String name;

    @ColumnInfo(name = "number")
    @Expose
    private String number;

    @ColumnInfo(name = "id_number")
    @Expose
    private String idNumber;

    @ColumnInfo(name = "birthDate")
    @Expose
    private String birthDate;

    @ColumnInfo(name = "age")
    @Expose
    private String age;

    @ColumnInfo(name = "exist")
    @Expose
    private String exist;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
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
