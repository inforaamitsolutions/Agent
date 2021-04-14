package com.codeclinic.agent.model.customerInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerLeadDataModel {
    @SerializedName("nextVisit")
    @Expose
    private String nextVisit;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("referenceMode")
    @Expose
    private String referenceMode;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("outcome")
    @Expose
    private String outcome;

    public String getNextVisit() {
        return nextVisit;
    }

    public void setNextVisit(String nextVisit) {
        this.nextVisit = nextVisit;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReferenceMode() {
        return referenceMode;
    }

    public void setReferenceMode(String referenceMode) {
        this.referenceMode = referenceMode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
