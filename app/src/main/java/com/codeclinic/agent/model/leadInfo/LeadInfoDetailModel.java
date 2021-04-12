package com.codeclinic.agent.model.leadInfo;

import com.codeclinic.agent.model.CustomerAttributesListModel;
import com.codeclinic.agent.model.CustomerHasRoleListModel;
import com.codeclinic.agent.model.CustomerStatusListModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeadInfoDetailModel {
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("dateTimeCreated")
    @Expose
    private Object dateTimeCreated;
    @SerializedName("lastModifiedBy")
    @Expose
    private Object lastModifiedBy;
    @SerializedName("lastModifiedDate")
    @Expose
    private Object lastModifiedDate;
    @SerializedName("ipAddress")
    @Expose
    private Object ipAddress;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("customerRole")
    @Expose
    private String customerRole;
    @SerializedName("customerType")
    @Expose
    private String customerType;
    @SerializedName("documentNumber")
    @Expose
    private Object documentNumber;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("externalId")
    @Expose
    private Object externalId;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("physicalAddress")
    @Expose
    private Object physicalAddress;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("relationType")
    @Expose
    private Object relationType;
    @SerializedName("relatedCustomerId")
    @Expose
    private Object relatedCustomerId;
    @SerializedName("attributeValues")
    @Expose
    private List<CustomerAttributesListModel> attributeValues = null;
    @SerializedName("customerHasRoles")
    @Expose
    private List<CustomerHasRoleListModel> customerHasRoles = null;
    @SerializedName("customerFlags")
    @Expose
    private List<Object> customerFlags = null;
    @SerializedName("customerHasGroups")
    @Expose
    private List<Object> customerHasGroups = null;
    @SerializedName("customerStatuses")
    @Expose
    private List<CustomerStatusListModel> customerStatuses = null;
    @SerializedName("customerRelations")
    @Expose
    private List<Object> customerRelations = null;

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Object dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Object getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Object lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Object getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Object lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Object getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Object ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCustomerRole() {
        return customerRole;
    }

    public void setCustomerRole(String customerRole) {
        this.customerRole = customerRole;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public Object getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Object documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getExternalId() {
        return externalId;
    }

    public void setExternalId(Object externalId) {
        this.externalId = externalId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(Object physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getRelationType() {
        return relationType;
    }

    public void setRelationType(Object relationType) {
        this.relationType = relationType;
    }

    public Object getRelatedCustomerId() {
        return relatedCustomerId;
    }

    public void setRelatedCustomerId(Object relatedCustomerId) {
        this.relatedCustomerId = relatedCustomerId;
    }


    public List<Object> getCustomerFlags() {
        return customerFlags;
    }

    public void setCustomerFlags(List<Object> customerFlags) {
        this.customerFlags = customerFlags;
    }

    public List<Object> getCustomerHasGroups() {
        return customerHasGroups;
    }

    public void setCustomerHasGroups(List<Object> customerHasGroups) {
        this.customerHasGroups = customerHasGroups;
    }


    public List<Object> getCustomerRelations() {
        return customerRelations;
    }

    public void setCustomerRelations(List<Object> customerRelations) {
        this.customerRelations = customerRelations;
    }

    public List<CustomerAttributesListModel> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<CustomerAttributesListModel> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public List<CustomerHasRoleListModel> getCustomerHasRoles() {
        return customerHasRoles;
    }

    public void setCustomerHasRoles(List<CustomerHasRoleListModel> customerHasRoles) {
        this.customerHasRoles = customerHasRoles;
    }

    public List<CustomerStatusListModel> getCustomerStatuses() {
        return customerStatuses;
    }

    public void setCustomerStatuses(List<CustomerStatusListModel> customerStatuses) {
        this.customerStatuses = customerStatuses;
    }
}
