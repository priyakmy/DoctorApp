package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TxnDetail {

    @SerializedName("txnId")
    @Expose
    private Integer txnId;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("testId")
    @Expose
    private Integer testId;
    @SerializedName("grpId")
    @Expose
    private Integer grpId;
    @SerializedName("pkgId")
    @Expose
    private Integer pkgId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("subTenantId")
    @Expose
    private Integer subTenantId;
    @SerializedName("CategoryName")
    @Expose
    private Integer categoryName;
    @SerializedName("applicableDiscount")
    @Expose
    private Integer applicableDiscount;
    @SerializedName("applicableAmount")
    @Expose
    private Integer applicableAmount;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("billAmount")
    @Expose
    private Integer billAmount;
    @SerializedName("payableAmount")
    @Expose
    private Integer payableAmount;
    @SerializedName("itemType")
    @Expose
    private Integer itemType;
    @SerializedName("subItems")
    @Expose
    private List<Object> subItems;

    public Integer getTxnId() {
        return txnId;
    }

    public void setTxnId(Integer txnId) {
        this.txnId = txnId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Integer getGrpId() {
        return grpId;
    }

    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }

    public Integer getPkgId() {
        return pkgId;
    }

    public void setPkgId(Integer pkgId) {
        this.pkgId = pkgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    public Integer getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Integer categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getApplicableDiscount() {
        return applicableDiscount;
    }

    public void setApplicableDiscount(Integer applicableDiscount) {
        this.applicableDiscount = applicableDiscount;
    }

    public Integer getApplicableAmount() {
        return applicableAmount;
    }

    public void setApplicableAmount(Integer applicableAmount) {
        this.applicableAmount = applicableAmount;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Integer billAmount) {
        this.billAmount = billAmount;
    }

    public Integer getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(Integer payableAmount) {
        this.payableAmount = payableAmount;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public List<Object> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<Object> subItems) {
        this.subItems = subItems;
    }

}
