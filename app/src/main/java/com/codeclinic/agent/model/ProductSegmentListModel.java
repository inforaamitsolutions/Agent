package com.codeclinic.agent.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductSegmentListModel {
    @SerializedName("dateTimeCreated")
    @Expose
    private String dateTimeCreated;
    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("productStatus")
    @Expose
    private String productStatus;
    @SerializedName("productCode")
    @Expose
    private String productCode;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("allowDecimal")
    @Expose
    private Boolean allowDecimal;
    @SerializedName("interestRate")
    @Expose
    private Double interestRate;
    @SerializedName("loanTerm")
    @Expose
    private Integer loanTerm;
    @SerializedName("minLoanTerm")
    @Expose
    private Integer minLoanTerm;
    @SerializedName("maxLoanTerm")
    @Expose
    private Integer maxLoanTerm;
    @SerializedName("loanTermFrequency")
    @Expose
    private String loanTermFrequency;
    @SerializedName("fixedInterestFlag")
    @Expose
    private Boolean fixedInterestFlag;
    @SerializedName("fixedInterestAmount")
    @Expose
    private Double fixedInterestAmount;
    @SerializedName("interestType")
    @Expose
    private String interestType;
    @SerializedName("allowTopUp")
    @Expose
    private Boolean allowTopUp;
    @SerializedName("orderOfRecovery")
    @Expose
    private String orderOfRecovery;
    @SerializedName("interestRecoveredUpfront")
    @Expose
    private Boolean interestRecoveredUpfront;
    @SerializedName("taxesEnabled")
    @Expose
    private Boolean taxesEnabled;
    @SerializedName("actualDaysOfMonth")
    @Expose
    private Boolean actualDaysOfMonth;
    @SerializedName("daysOfYear")
    @Expose
    private String daysOfYear;
    @SerializedName("daysPeriodicInterval")
    @Expose
    private Integer daysPeriodicInterval;
    @SerializedName("checkCrb")
    @Expose
    private Boolean checkCrb;
    @SerializedName("minLoanAmount")
    @Expose
    private Double minLoanAmount;
    @SerializedName("maxLoanAmount")
    @Expose
    private Double maxLoanAmount;
    @SerializedName("allowMultipleInstallments")
    @Expose
    private Boolean allowMultipleInstallments;
    @SerializedName("restrictBorrowingTime")
    @Expose
    private Boolean restrictBorrowingTime;
    @SerializedName("defaultInstallments")
    @Expose
    private Integer defaultInstallments;
    @SerializedName("minInstallments")
    @Expose
    private Integer minInstallments;
    @SerializedName("maxInstallments")
    @Expose
    private Integer maxInstallments;
    @SerializedName("restrictDestinationAccount")
    @Expose
    private Boolean restrictDestinationAccount;
    @SerializedName("productLoanLimit")
    @Expose
    private Double productLoanLimit;
    @SerializedName("cappingPercentage")
    @Expose
    private Double cappingPercentage;
    @SerializedName("allowMultipleLoans")
    @Expose
    private Boolean allowMultipleLoans;
    @SerializedName("maximumAllowedLoans")
    @Expose
    private Integer maximumAllowedLoans;
    @SerializedName("allowRollOver")
    @Expose
    private Boolean allowRollOver;
    @SerializedName("defaultExactTime")
    @Expose
    private Boolean defaultExactTime;
    @SerializedName("partnerBased")
    @Expose
    private Boolean partnerBased;
    @SerializedName("disbursedToPartner")
    @Expose
    private Boolean disbursedToPartner;
    @SerializedName("limitIsPartnerBased")
    @Expose
    private Boolean limitIsPartnerBased;
    @SerializedName("automateRefund")
    @Expose
    private Boolean automateRefund;

    public String getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getAllowDecimal() {
        return allowDecimal;
    }

    public void setAllowDecimal(Boolean allowDecimal) {
        this.allowDecimal = allowDecimal;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Integer getMinLoanTerm() {
        return minLoanTerm;
    }

    public void setMinLoanTerm(Integer minLoanTerm) {
        this.minLoanTerm = minLoanTerm;
    }

    public Integer getMaxLoanTerm() {
        return maxLoanTerm;
    }

    public void setMaxLoanTerm(Integer maxLoanTerm) {
        this.maxLoanTerm = maxLoanTerm;
    }

    public String getLoanTermFrequency() {
        return loanTermFrequency;
    }

    public void setLoanTermFrequency(String loanTermFrequency) {
        this.loanTermFrequency = loanTermFrequency;
    }

    public Boolean getFixedInterestFlag() {
        return fixedInterestFlag;
    }

    public void setFixedInterestFlag(Boolean fixedInterestFlag) {
        this.fixedInterestFlag = fixedInterestFlag;
    }

    public Double getFixedInterestAmount() {
        return fixedInterestAmount;
    }

    public void setFixedInterestAmount(Double fixedInterestAmount) {
        this.fixedInterestAmount = fixedInterestAmount;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public Boolean getAllowTopUp() {
        return allowTopUp;
    }

    public void setAllowTopUp(Boolean allowTopUp) {
        this.allowTopUp = allowTopUp;
    }

    public String getOrderOfRecovery() {
        return orderOfRecovery;
    }

    public void setOrderOfRecovery(String orderOfRecovery) {
        this.orderOfRecovery = orderOfRecovery;
    }

    public Boolean getInterestRecoveredUpfront() {
        return interestRecoveredUpfront;
    }

    public void setInterestRecoveredUpfront(Boolean interestRecoveredUpfront) {
        this.interestRecoveredUpfront = interestRecoveredUpfront;
    }

    public Boolean getTaxesEnabled() {
        return taxesEnabled;
    }

    public void setTaxesEnabled(Boolean taxesEnabled) {
        this.taxesEnabled = taxesEnabled;
    }

    public Boolean getActualDaysOfMonth() {
        return actualDaysOfMonth;
    }

    public void setActualDaysOfMonth(Boolean actualDaysOfMonth) {
        this.actualDaysOfMonth = actualDaysOfMonth;
    }

    public String getDaysOfYear() {
        return daysOfYear;
    }

    public void setDaysOfYear(String daysOfYear) {
        this.daysOfYear = daysOfYear;
    }

    public Integer getDaysPeriodicInterval() {
        return daysPeriodicInterval;
    }

    public void setDaysPeriodicInterval(Integer daysPeriodicInterval) {
        this.daysPeriodicInterval = daysPeriodicInterval;
    }

    public Boolean getCheckCrb() {
        return checkCrb;
    }

    public void setCheckCrb(Boolean checkCrb) {
        this.checkCrb = checkCrb;
    }

    public Double getMinLoanAmount() {
        return minLoanAmount;
    }

    public void setMinLoanAmount(Double minLoanAmount) {
        this.minLoanAmount = minLoanAmount;
    }

    public Double getMaxLoanAmount() {
        return maxLoanAmount;
    }

    public void setMaxLoanAmount(Double maxLoanAmount) {
        this.maxLoanAmount = maxLoanAmount;
    }

    public Boolean getAllowMultipleInstallments() {
        return allowMultipleInstallments;
    }

    public void setAllowMultipleInstallments(Boolean allowMultipleInstallments) {
        this.allowMultipleInstallments = allowMultipleInstallments;
    }

    public Boolean getRestrictBorrowingTime() {
        return restrictBorrowingTime;
    }

    public void setRestrictBorrowingTime(Boolean restrictBorrowingTime) {
        this.restrictBorrowingTime = restrictBorrowingTime;
    }

    public Integer getDefaultInstallments() {
        return defaultInstallments;
    }

    public void setDefaultInstallments(Integer defaultInstallments) {
        this.defaultInstallments = defaultInstallments;
    }

    public Integer getMinInstallments() {
        return minInstallments;
    }

    public void setMinInstallments(Integer minInstallments) {
        this.minInstallments = minInstallments;
    }

    public Integer getMaxInstallments() {
        return maxInstallments;
    }

    public void setMaxInstallments(Integer maxInstallments) {
        this.maxInstallments = maxInstallments;
    }

    public Boolean getRestrictDestinationAccount() {
        return restrictDestinationAccount;
    }

    public void setRestrictDestinationAccount(Boolean restrictDestinationAccount) {
        this.restrictDestinationAccount = restrictDestinationAccount;
    }

    public Double getProductLoanLimit() {
        return productLoanLimit;
    }

    public void setProductLoanLimit(Double productLoanLimit) {
        this.productLoanLimit = productLoanLimit;
    }

    public Double getCappingPercentage() {
        return cappingPercentage;
    }

    public void setCappingPercentage(Double cappingPercentage) {
        this.cappingPercentage = cappingPercentage;
    }

    public Boolean getAllowMultipleLoans() {
        return allowMultipleLoans;
    }

    public void setAllowMultipleLoans(Boolean allowMultipleLoans) {
        this.allowMultipleLoans = allowMultipleLoans;
    }

    public Integer getMaximumAllowedLoans() {
        return maximumAllowedLoans;
    }

    public void setMaximumAllowedLoans(Integer maximumAllowedLoans) {
        this.maximumAllowedLoans = maximumAllowedLoans;
    }

    public Boolean getAllowRollOver() {
        return allowRollOver;
    }

    public void setAllowRollOver(Boolean allowRollOver) {
        this.allowRollOver = allowRollOver;
    }

    public Boolean getDefaultExactTime() {
        return defaultExactTime;
    }

    public void setDefaultExactTime(Boolean defaultExactTime) {
        this.defaultExactTime = defaultExactTime;
    }

    public Boolean getPartnerBased() {
        return partnerBased;
    }

    public void setPartnerBased(Boolean partnerBased) {
        this.partnerBased = partnerBased;
    }

    public Boolean getDisbursedToPartner() {
        return disbursedToPartner;
    }

    public void setDisbursedToPartner(Boolean disbursedToPartner) {
        this.disbursedToPartner = disbursedToPartner;
    }

    public Boolean getLimitIsPartnerBased() {
        return limitIsPartnerBased;
    }

    public void setLimitIsPartnerBased(Boolean limitIsPartnerBased) {
        this.limitIsPartnerBased = limitIsPartnerBased;
    }

    public Boolean getAutomateRefund() {
        return automateRefund;
    }

    public void setAutomateRefund(Boolean automateRefund) {
        this.automateRefund = automateRefund;
    }

    @Override
    public String toString() {
        return productName;
    }
}
