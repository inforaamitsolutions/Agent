package com.codeclinic.agent.model.customerInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerBioDataModel {
    @SerializedName("passportPhoto")
    @Expose
    private String passportPhoto;
    @SerializedName("customerAge")
    @Expose
    private String customerAge;
    @SerializedName("aliasName")
    @Expose
    private String aliasName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("newExisting")
    @Expose
    private String newExisting;
    @SerializedName("alternativeNumber")
    @Expose
    private String alternativeNumber;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("idFront")
    @Expose
    private String idFront;
    @SerializedName("idNumber")
    @Expose
    private String idNumber;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("IdBack")
    @Expose
    private String idBack;
    @SerializedName("ownHome")
    @Expose
    private String ownHome;
    @SerializedName("childrenSchooling")
    @Expose
    private String childrenSchooling;
    @SerializedName("rentAmount")
    @Expose
    private String rentAmount;
    @SerializedName("schoolFees")
    @Expose
    private String schoolFees;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("homeAddress")
    @Expose
    private String homeAddress;

    public String getPassportPhoto() {
        return passportPhoto;
    }

    public void setPassportPhoto(String passportPhoto) {
        this.passportPhoto = passportPhoto;
    }

    public String getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(String customerAge) {
        this.customerAge = customerAge;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNewExisting() {
        return newExisting;
    }

    public void setNewExisting(String newExisting) {
        this.newExisting = newExisting;
    }

    public String getAlternativeNumber() {
        return alternativeNumber;
    }

    public void setAlternativeNumber(String alternativeNumber) {
        this.alternativeNumber = alternativeNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdFront() {
        return idFront;
    }

    public void setIdFront(String idFront) {
        this.idFront = idFront;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdBack() {
        return idBack;
    }

    public void setIdBack(String idBack) {
        this.idBack = idBack;
    }

    public String getOwnHome() {
        return ownHome;
    }

    public void setOwnHome(String ownHome) {
        this.ownHome = ownHome;
    }

    public String getChildrenSchooling() {
        return childrenSchooling;
    }

    public void setChildrenSchooling(String childrenSchooling) {
        this.childrenSchooling = childrenSchooling;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getSchoolFees() {
        return schoolFees;
    }

    public void setSchoolFees(String schoolFees) {
        this.schoolFees = schoolFees;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }
}
