package com.codeclinic.agent.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRoleModel {
    @SerializedName("dateTimeCreated")
    @Expose
    private long dateTimeCreated;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("tenant")
    @Expose
    private UserTenetModel tenant;
    @SerializedName("id")
    @Expose
    private Integer id;

    public long getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(long dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserTenetModel getTenant() {
        return tenant;
    }

    public void setTenant(UserTenetModel tenant) {
        this.tenant = tenant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
