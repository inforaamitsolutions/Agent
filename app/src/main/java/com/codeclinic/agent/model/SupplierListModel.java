package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupplierListModel {
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
    private String documentNumber;
    @SerializedName("email")
    @Expose
    private String email;
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
    private List<CustomerAttributesListModel> attributeValues = null;
    @SerializedName("customerRole")
    @Expose
    private CustomerRoleModel customerRole;
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
    private List<CustomerHasRoleListModel> customerHasRoles = null;
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

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

    public CustomerRoleModel getCustomerRole() {
        return customerRole;
    }

    public void setCustomerRole(CustomerRoleModel customerRole) {
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

    @Override
    public String toString() {
        return fullName;
    }
}
