
package com.mcura.mcurapharmacy.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabDatum {

    @SerializedName("Against_sub_tenant_id")
    @Expose
    private Integer againstSubTenantId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("HospitalNo")
    @Expose
    private String hospitalNo;
    @SerializedName("LabOrderId")
    @Expose
    private Integer labOrderId;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("Mrno")
    @Expose
    private Integer mrno;
    @SerializedName("PaidStatus")
    @Expose
    private Integer paidStatus;
    @SerializedName("Patname")
    @Expose
    private String patname;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("doctorName")
    @Expose
    private String doctorName;
    @SerializedName("gender_id")
    @Expose
    private Integer genderId;
    @SerializedName("orderBy_sub_tenant_id")
    @Expose
    private Integer orderBySubTenantId;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("user_role_id")
    @Expose
    private Integer userRoleId;
    @SerializedName("LabChildData")
    @Expose
    private List<LabChildDatum> labChildData = null;

    @SerializedName("sub_tenant_id")
    @Expose
    private Integer sub_tenant_id;

    @SerializedName("LabChildTokenData")
    @Expose
    private List<LabChildTokenDatum> labChildTokenData = null;

    @SerializedName("lastVisitSummaryPath")
    @Expose
    private String lastVisitSummaryPath;

    public String getLastVisitSummaryPath() {
        return lastVisitSummaryPath;
    }

    public void setLastVisitSummaryPath(String lastVisitSummaryPath) {
        this.lastVisitSummaryPath = lastVisitSummaryPath;
    }

    public List<LabChildTokenDatum> getLabChildTokenData() {
        return labChildTokenData;
    }

    public void setLabChildTokenData(List<LabChildTokenDatum> labChildTokenData) {
        this.labChildTokenData = labChildTokenData;
    }

    public Integer getSub_tenant_id() {
        return sub_tenant_id;
    }

    public void setSub_tenant_id(Integer sub_tenant_id) {
        this.sub_tenant_id = sub_tenant_id;
    }

    public Integer getAgainstSubTenantId() {
        return againstSubTenantId;
    }

    public void setAgainstSubTenantId(Integer againstSubTenantId) {
        this.againstSubTenantId = againstSubTenantId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public Integer getLabOrderId() {
        return labOrderId;
    }

    public void setLabOrderId(Integer labOrderId) {
        this.labOrderId = labOrderId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getMrno() {
        return mrno;
    }

    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    public Integer getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(Integer paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getPatname() {
        return patname;
    }

    public void setPatname(String patname) {
        this.patname = patname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getOrderBySubTenantId() {
        return orderBySubTenantId;
    }

    public void setOrderBySubTenantId(Integer orderBySubTenantId) {
        this.orderBySubTenantId = orderBySubTenantId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public List<LabChildDatum> getLabChildData() {
        return labChildData;
    }

    public void setLabChildData(List<LabChildDatum> labChildData) {
        this.labChildData = labChildData;
    }







    @SerializedName("recordId")
    @Expose
    private Integer recordId;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("entryTypeId")
    @Expose
    private Integer entryTypeId;
    @SerializedName("dataType")
    @Expose
    private Integer dataType;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Integer getEntryTypeId() {
        return entryTypeId;
    }

    public void setEntryTypeId(Integer entryTypeId) {
        this.entryTypeId = entryTypeId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
}
