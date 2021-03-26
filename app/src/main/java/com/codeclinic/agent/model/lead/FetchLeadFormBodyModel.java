package com.codeclinic.agent.model.lead;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchLeadFormBodyModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("surveyTheme")
    @Expose
    private String surveyTheme;
    @SerializedName("allowMultipleSubmissions")
    @Expose
    private Object allowMultipleSubmissions;
    @SerializedName("emailInvitationTemplate")
    @Expose
    private Object emailInvitationTemplate;
    @SerializedName("isPublic")
    @Expose
    private Object isPublic;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("allowAnonymousSubmissions")
    @Expose
    private Object allowAnonymousSubmissions;
    @SerializedName("surveyVersion")
    @Expose
    private Integer surveyVersion;

    @SerializedName("surveyDefinitionPages")
    @Expose
    private List<LeadSurveyDefinitionPageModel> surveyDefinitionPages = null;

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

    public Object getAllowMultipleSubmissions() {
        return allowMultipleSubmissions;
    }

    public void setAllowMultipleSubmissions(Object allowMultipleSubmissions) {
        this.allowMultipleSubmissions = allowMultipleSubmissions;
    }

    public Object getEmailInvitationTemplate() {
        return emailInvitationTemplate;
    }

    public void setEmailInvitationTemplate(Object emailInvitationTemplate) {
        this.emailInvitationTemplate = emailInvitationTemplate;
    }

    public Object getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Object isPublic) {
        this.isPublic = isPublic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getAllowAnonymousSubmissions() {
        return allowAnonymousSubmissions;
    }

    public void setAllowAnonymousSubmissions(Object allowAnonymousSubmissions) {
        this.allowAnonymousSubmissions = allowAnonymousSubmissions;
    }

    public Integer getSurveyVersion() {
        return surveyVersion;
    }

    public void setSurveyVersion(Integer surveyVersion) {
        this.surveyVersion = surveyVersion;
    }

    public List<LeadSurveyDefinitionPageModel> getSurveyDefinitionPages() {
        return surveyDefinitionPages;
    }

    public void setSurveyDefinitionPages(List<LeadSurveyDefinitionPageModel> surveyDefinitionPages) {
        this.surveyDefinitionPages = surveyDefinitionPages;
    }


}
