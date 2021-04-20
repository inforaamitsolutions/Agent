package com.codeclinic.agent.model.businesDataUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchBusinessDataFormModel {

    @SerializedName("successStatus")
    @Expose
    private String successStatus;

    @SerializedName("body")
    @Expose
    private FetchBusinessDataFormBodyModel body;


    @SerializedName("responseCode")
    @Expose
    private Integer responseCode;


    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;


    @SerializedName("httpStatus")
    @Expose
    private String httpStatus;


    public String getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(String successStatus) {
        this.successStatus = successStatus;
    }

    public FetchBusinessDataFormBodyModel getBody() {
        return body;
    }

    public void setBody(FetchBusinessDataFormBodyModel body) {
        this.body = body;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

}

