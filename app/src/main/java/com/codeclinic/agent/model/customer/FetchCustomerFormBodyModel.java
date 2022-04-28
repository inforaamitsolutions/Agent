package com.codeclinic.agent.model.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.codeclinic.agent.typeConverters.customer.CustomerSurveyPageTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "CustomerFormBody")
public class FetchCustomerFormBodyModel {

    @ColumnInfo(name = "mainId")
    @PrimaryKey(autoGenerate = false)
    @Expose
    private long mainId;

    @ColumnInfo(name = "bodyId")
    @SerializedName("id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @ColumnInfo(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @ColumnInfo(name = "surveyTheme")
    @SerializedName("surveyTheme")
    @Expose
    private String surveyTheme;

    @ColumnInfo(name = "allowMultipleSubmissions")
    @SerializedName("allowMultipleSubmissions")
    @Expose
    private boolean allowMultipleSubmissions;

    @ColumnInfo(name = "emailInvitationTemplate")
    @SerializedName("emailInvitationTemplate")
    @Expose
    private String emailInvitationTemplate;

    @ColumnInfo(name = "isPublic")
    @SerializedName("isPublic")
    @Expose
    private boolean isPublic;


    @ColumnInfo(name = "status")
    @SerializedName("status")
    @Expose
    private String status;

    @ColumnInfo(name = "allowAnonymousSubmissions")
    @SerializedName("allowAnonymousSubmissions")
    @Expose
    private boolean allowAnonymousSubmissions;

    @ColumnInfo(name = "surveyVersion")
    @SerializedName("surveyVersion")
    @Expose
    private Integer surveyVersion;

    @SerializedName("surveyDefinitionPages")
    @Expose
    @TypeConverters(CustomerSurveyPageTypeConverter.class)
    private List<CustomerSurveyDefinitionPageModel> surveyDefinitionPages = null;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSurveyTheme() {
        return surveyTheme;
    }

    public void setSurveyTheme(String surveyTheme) {
        this.surveyTheme = surveyTheme;
    }

    public boolean getAllowMultipleSubmissions() {
        return allowMultipleSubmissions;
    }

    public void setAllowMultipleSubmissions(boolean allowMultipleSubmissions) {
        this.allowMultipleSubmissions = allowMultipleSubmissions;
    }

    public String getEmailInvitationTemplate() {
        return emailInvitationTemplate;
    }

    public void setEmailInvitationTemplate(String emailInvitationTemplate) {
        this.emailInvitationTemplate = emailInvitationTemplate;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getAllowAnonymousSubmissions() {
        return allowAnonymousSubmissions;
    }

    public void setAllowAnonymousSubmissions(boolean allowAnonymousSubmissions) {
        this.allowAnonymousSubmissions = allowAnonymousSubmissions;
    }

    public Integer getSurveyVersion() {
        return surveyVersion;
    }

    public void setSurveyVersion(Integer surveyVersion) {
        this.surveyVersion = surveyVersion;
    }

    public List<CustomerSurveyDefinitionPageModel> getSurveyDefinitionPages() {
        return surveyDefinitionPages;
    }

    public void setSurveyDefinitionPages(List<CustomerSurveyDefinitionPageModel> surveyDefinitionPages) {
        this.surveyDefinitionPages = surveyDefinitionPages;
    }


}
