package com.codeclinic.agent.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRealm {
    @SerializedName("dateTimeCreated")
    @Expose
    private Integer dateTimeCreated;
    @SerializedName("realmId")
    @Expose
    private String realmId;
    @SerializedName("realmClientId")
    @Expose
    private String realmClientId;
    @SerializedName("realmClientSecret")
    @Expose
    private String realmClientSecret;

    public Integer getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Integer dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getRealmClientId() {
        return realmClientId;
    }

    public void setRealmClientId(String realmClientId) {
        this.realmClientId = realmClientId;
    }

    public String getRealmClientSecret() {
        return realmClientSecret;
    }

    public void setRealmClientSecret(String realmClientSecret) {
        this.realmClientSecret = realmClientSecret;
    }
}
