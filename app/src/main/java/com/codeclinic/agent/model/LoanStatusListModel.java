package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoanStatusListModel {

    public LoanStatusListModel(String name) {
        this.name = name;
    }

    public LoanStatusListModel() {
    }

    @SerializedName("dateTimeCreated")
    @Expose
    private Object dateTimeCreated;
    @SerializedName("ipAddress")
    @Expose
    private Object ipAddress;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private Object description;

    public Object getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Object dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Object getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Object ipAddress) {
        this.ipAddress = ipAddress;
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

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
