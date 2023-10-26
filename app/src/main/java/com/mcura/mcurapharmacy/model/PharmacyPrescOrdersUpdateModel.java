
package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyPrescOrdersUpdateModel {

    @SerializedName("Date")
    @Expose
    private Object date;
    @SerializedName("PrescriptionId")
    @Expose
    private Object prescriptionId;
    @SerializedName("StatusId")
    @Expose
    private Object statusId;
    @SerializedName("SubTenantId")
    @Expose
    private Object subTenantId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Object prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Object getStatusId() {
        return statusId;
    }

    public void setStatusId(Object statusId) {
        this.statusId = statusId;
    }

    public Object getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Object subTenantId) {
        this.subTenantId = subTenantId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
