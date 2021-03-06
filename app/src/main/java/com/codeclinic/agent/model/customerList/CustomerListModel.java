package com.codeclinic.agent.model.customerList;

import com.codeclinic.agent.model.CustomerAttributesListModel;
import com.codeclinic.agent.model.CustomerHasRoleListModel;
import com.codeclinic.agent.model.CustomerStatusListModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerListModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("documentNumber")
    @Expose
    private Object documentNumber;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("customerType")
    @Expose
    private String customerType;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("attributeValues")
    @Expose
    private List<CustomerAttributesListModel> attributeList = null;
    @SerializedName("customerRole")
    @Expose
    private String customerRole;
    @SerializedName("externalId")
    @Expose
    private Object externalId;
    @SerializedName("documentType")
    @Expose
    private String documentType;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("customerHasRoles")
    @Expose
    private List<CustomerHasRoleListModel> customerHasRolesList = null;
    @SerializedName("customerStatuses")
    @Expose
    private List<CustomerStatusListModel> customerStatuses = null;
    @SerializedName("customerHasGroups")
    @Expose
    private List<Object> customerHasGroups = null;
    @SerializedName("customerFlags")
    @Expose
    private List<Object> customerFlags = null;
    @SerializedName("customerRelations")
    @Expose
    private List<Object> customerRelations = null;
    @SerializedName("relationType")
    @Expose
    private Object relationType;
    @SerializedName("relatedCustomerId")
    @Expose
    private Object relatedCustomerId;

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

    public Object getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(Object documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getCustomerRole() {
        return customerRole;
    }

    public void setCustomerRole(String customerRole) {
        this.customerRole = customerRole;
    }

    public Object getExternalId() {
        return externalId;
    }

    public void setExternalId(Object externalId) {
        this.externalId = externalId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


    public List<Object> getCustomerHasGroups() {
        return customerHasGroups;
    }

    public void setCustomerHasGroups(List<Object> customerHasGroups) {
        this.customerHasGroups = customerHasGroups;
    }

    public List<Object> getCustomerFlags() {
        return customerFlags;
    }

    public void setCustomerFlags(List<Object> customerFlags) {
        this.customerFlags = customerFlags;
    }

    public List<Object> getCustomerRelations() {
        return customerRelations;
    }

    public void setCustomerRelations(List<Object> customerRelations) {
        this.customerRelations = customerRelations;
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

    public List<CustomerAttributesListModel> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<CustomerAttributesListModel> attributeList) {
        this.attributeList = attributeList;
    }

    public List<CustomerHasRoleListModel> getCustomerHasRolesList() {
        return customerHasRolesList;
    }

    public void setCustomerHasRolesList(List<CustomerHasRoleListModel> customerHasRolesList) {
        this.customerHasRolesList = customerHasRolesList;
    }

    public List<CustomerStatusListModel> getCustomerStatuses() {
        return customerStatuses;
    }

    public void setCustomerStatuses(List<CustomerStatusListModel> customerStatuses) {
        this.customerStatuses = customerStatuses;
    }
}
