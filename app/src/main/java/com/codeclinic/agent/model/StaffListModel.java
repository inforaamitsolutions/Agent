package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StaffListModel {
    public StaffListModel() {
    }

    public StaffListModel(String firstName) {
        this.firstName = firstName;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("otherName")
    @Expose
    private String otherName;
    @SerializedName("externalId")
    @Expose
    private String externalId;
    @SerializedName("emailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("documentNumber")
    @Expose
    private String documentNumber;
    @SerializedName("staffId")
    @Expose
    private String staffId;
    @SerializedName("joiningDate")
    @Expose
    private Object joiningDate;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("attributes")
    @Expose
    private Object attributes;
    @SerializedName("designations")
    @Expose
    private List<StaffDesignationModel> designations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Object getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Object joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<StaffDesignationModel> getDesignations() {
        return designations;
    }

    public void setDesignations(List<StaffDesignationModel> designations) {
        this.designations = designations;
    }

    @Override
    public String toString() {
        if (designations != null) {
            return firstName + lastName + otherName + designations.get(0).getDesignationName();
        } else {
            if (lastName == null && otherName == null) {
                return firstName;
            }
            return firstName + lastName + otherName;
        }
    }
}
