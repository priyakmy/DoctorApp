package com.mcura.mcurapharmacy.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {

    @SerializedName("addressId")
    @Expose
    private Integer addressId;
    @SerializedName("contactId")
    @Expose
    private Integer contactId;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("emailId")
    @Expose
    private String emailId;
    @SerializedName("genderId")
    @Expose
    private Integer genderId;
    @SerializedName("hospitalNo")
    @Expose
    private String hospitalNo;
    @SerializedName("imagePath")
    @Expose
    private String imagePath;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("mrNo")
    @Expose
    private Integer mrNo;
    @SerializedName("patDemoId")
    @Expose
    private Integer patDemoId;
    @SerializedName("patName")
    @Expose
    private String patName;
    @SerializedName("recordCount")
    @Expose
    private Integer recordCount;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getMrNo() {
        return mrNo;
    }

    public void setMrNo(Integer mrNo) {
        this.mrNo = mrNo;
    }

    public Integer getPatDemoId() {
        return patDemoId;
    }

    public void setPatDemoId(Integer patDemoId) {
        this.patDemoId = patDemoId;
    }

    public String getPatName() {
        return patName;
    }

    public void setPatName(String patName) {
        this.patName = patName;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

}
