package com.mcura.mcurapharmacy.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubtenantLoginId {
    @SerializedName("AddressID")
    @Expose
    private Object addressID;
    @SerializedName("ContactID")
    @Expose
    private Object contactID;
    @SerializedName("FacilityTypeId")
    @Expose
    private Integer facilityTypeId;
    @SerializedName("FacilityTypeName")
    @Expose
    private String facilityTypeName;
    @SerializedName("PatientAccess")
    @Expose
    private Object patientAccess;
    @SerializedName("SubTenantId")
    @Expose
    private Integer subTenantId;
    @SerializedName("SubTenantName")
    @Expose
    private String subTenantName;

    public Object getAddressID() {
        return addressID;
    }

    public void setAddressID(Object addressID) {
        this.addressID = addressID;
    }

    public Object getContactID() {
        return contactID;
    }

    public void setContactID(Object contactID) {
        this.contactID = contactID;
    }

    public Integer getFacilityTypeId() {
        return facilityTypeId;
    }

    public void setFacilityTypeId(Integer facilityTypeId) {
        this.facilityTypeId = facilityTypeId;
    }

    public String getFacilityTypeName() {
        return facilityTypeName;
    }

    public void setFacilityTypeName(String facilityTypeName) {
        this.facilityTypeName = facilityTypeName;
    }

    public Object getPatientAccess() {
        return patientAccess;
    }

    public void setPatientAccess(Object patientAccess) {
        this.patientAccess = patientAccess;
    }

    public Integer getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    public String getSubTenantName() {
        return subTenantName;
    }

    public void setSubTenantName(String subTenantName) {
        this.subTenantName = subTenantName;
    }

}
