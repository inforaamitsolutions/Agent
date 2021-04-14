package com.codeclinic.agent.model.customerInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerSocialDataModel {
    @SerializedName("RefereeOneRelationShip")
    @Expose
    private String refereeOneRelationShip;
    @SerializedName("refereeTwoNumber")
    @Expose
    private String refereeTwoNumber;
    @SerializedName("nextOfKinNumber")
    @Expose
    private String nextOfKinNumber;
    @SerializedName("nextOfKinName")
    @Expose
    private String nextOfKinName;
    @SerializedName("refereeOneNumber")
    @Expose
    private String refereeOneNumber;
    @SerializedName("refereeTwoName")
    @Expose
    private String refereeTwoName;
    @SerializedName("refereeOneName")
    @Expose
    private String refereeOneName;
    @SerializedName("refereeTwoRelationship")
    @Expose
    private String refereeTwoRelationship;
    @SerializedName("nextOfKinRelationship")
    @Expose
    private String nextOfKinRelationship;

    public String getRefereeOneRelationShip() {
        return refereeOneRelationShip;
    }

    public void setRefereeOneRelationShip(String refereeOneRelationShip) {
        this.refereeOneRelationShip = refereeOneRelationShip;
    }

    public String getRefereeTwoNumber() {
        return refereeTwoNumber;
    }

    public void setRefereeTwoNumber(String refereeTwoNumber) {
        this.refereeTwoNumber = refereeTwoNumber;
    }

    public String getNextOfKinNumber() {
        return nextOfKinNumber;
    }

    public void setNextOfKinNumber(String nextOfKinNumber) {
        this.nextOfKinNumber = nextOfKinNumber;
    }

    public String getNextOfKinName() {
        return nextOfKinName;
    }

    public void setNextOfKinName(String nextOfKinName) {
        this.nextOfKinName = nextOfKinName;
    }

    public String getRefereeOneNumber() {
        return refereeOneNumber;
    }

    public void setRefereeOneNumber(String refereeOneNumber) {
        this.refereeOneNumber = refereeOneNumber;
    }

    public String getRefereeTwoName() {
        return refereeTwoName;
    }

    public void setRefereeTwoName(String refereeTwoName) {
        this.refereeTwoName = refereeTwoName;
    }

    public String getRefereeOneName() {
        return refereeOneName;
    }

    public void setRefereeOneName(String refereeOneName) {
        this.refereeOneName = refereeOneName;
    }

    public String getRefereeTwoRelationship() {
        return refereeTwoRelationship;
    }

    public void setRefereeTwoRelationship(String refereeTwoRelationship) {
        this.refereeTwoRelationship = refereeTwoRelationship;
    }

    public String getNextOfKinRelationship() {
        return nextOfKinRelationship;
    }

    public void setNextOfKinRelationship(String nextOfKinRelationship) {
        this.nextOfKinRelationship = nextOfKinRelationship;
    }

}
