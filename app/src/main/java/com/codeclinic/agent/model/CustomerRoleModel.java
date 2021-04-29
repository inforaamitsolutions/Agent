package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerRoleModel {

    @SerializedName("dateTimeCreated")
    @Expose
    private String dateTimeCreated;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("customerAttributes")
    @Expose
    private List<Object> customerAttributes = null;
    @SerializedName("initialCustomerStatus")
    @Expose
    private Object initialCustomerStatus;
    @SerializedName("autoCreateAuthentication")
    @Expose
    private Boolean autoCreateAuthentication;
    @SerializedName("defaultChannel")
    @Expose
    private Object defaultChannel;
    @SerializedName("allowedChannels")
    @Expose
    private List<Object> allowedChannels = null;
    @SerializedName("systemGenerated")
    @Expose
    private Boolean systemGenerated;

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getCustomerAttributes() {
        return customerAttributes;
    }

    public void setCustomerAttributes(List<Object> customerAttributes) {
        this.customerAttributes = customerAttributes;
    }

    public Object getInitialCustomerStatus() {
        return initialCustomerStatus;
    }

    public void setInitialCustomerStatus(Object initialCustomerStatus) {
        this.initialCustomerStatus = initialCustomerStatus;
    }

    public Boolean getAutoCreateAuthentication() {
        return autoCreateAuthentication;
    }

    public void setAutoCreateAuthentication(Boolean autoCreateAuthentication) {
        this.autoCreateAuthentication = autoCreateAuthentication;
    }

    public Object getDefaultChannel() {
        return defaultChannel;
    }

    public void setDefaultChannel(Object defaultChannel) {
        this.defaultChannel = defaultChannel;
    }

    public List<Object> getAllowedChannels() {
        return allowedChannels;
    }

    public void setAllowedChannels(List<Object> allowedChannels) {
        this.allowedChannels = allowedChannels;
    }

    public Boolean getSystemGenerated() {
        return systemGenerated;
    }

    public void setSystemGenerated(Boolean systemGenerated) {
        this.systemGenerated = systemGenerated;
    }
}
