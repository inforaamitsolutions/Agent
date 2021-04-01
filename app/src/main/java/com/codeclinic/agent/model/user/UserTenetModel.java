package com.codeclinic.agent.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTenetModel {
    @SerializedName("dateTimeCreated")
    @Expose
    private long dateTimeCreated;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("custId")
    @Expose
    private String custId;
    @SerializedName("clientBaseUrl")
    @Expose
    private String clientBaseUrl;
    @SerializedName("appKey")
    @Expose
    private String appKey;
    @SerializedName("realm")
    @Expose
    private UserRealm realm;

    public long getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(long dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getClientBaseUrl() {
        return clientBaseUrl;
    }

    public void setClientBaseUrl(String clientBaseUrl) {
        this.clientBaseUrl = clientBaseUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public UserRealm getRealm() {
        return realm;
    }

    public void setRealm(UserRealm realm) {
        this.realm = realm;
    }
}
