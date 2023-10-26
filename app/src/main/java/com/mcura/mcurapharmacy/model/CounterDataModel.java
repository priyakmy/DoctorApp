package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CounterDataModel {
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserRoleId")
    @Expose
    private Integer userRoleId;
    @SerializedName("coverPhoto")
    @Expose
    private Object coverPhoto;
    @SerializedName("departments")
    @Expose
    private Object departments;
    @SerializedName("deptId")
    @Expose
    private Integer deptId;
    @SerializedName("deptName")
    @Expose
    private String deptName;
    @SerializedName("deptProfilePath")
    @Expose
    private Object deptProfilePath;
    @SerializedName("docEducation")
    @Expose
    private Object docEducation;
    @SerializedName("docExperience")
    @Expose
    private Object docExperience;
    @SerializedName("docFee")
    @Expose
    private String docFee;
    @SerializedName("docProfilePath")
    @Expose
    private Object docProfilePath;
    @SerializedName("doctorFee")
    @Expose
    private Object doctorFee;
    @SerializedName("ipadPhoto")
    @Expose
    private Object ipadPhoto;
    @SerializedName("mobilePhoto")
    @Expose
    private Object mobilePhoto;
    @SerializedName("profilephoto")
    @Expose
    private Object profilephoto;
    @SerializedName("serviceRoleId")
    @Expose
    private Integer serviceRoleId;
    @SerializedName("serviceTypeId")
    @Expose
    private Integer serviceTypeId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Object getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(Object coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public Object getDepartments() {
        return departments;
    }

    public void setDepartments(Object departments) {
        this.departments = departments;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Object getDeptProfilePath() {
        return deptProfilePath;
    }

    public void setDeptProfilePath(Object deptProfilePath) {
        this.deptProfilePath = deptProfilePath;
    }

    public Object getDocEducation() {
        return docEducation;
    }

    public void setDocEducation(Object docEducation) {
        this.docEducation = docEducation;
    }

    public Object getDocExperience() {
        return docExperience;
    }

    public void setDocExperience(Object docExperience) {
        this.docExperience = docExperience;
    }

    public String getDocFee() {
        return docFee;
    }

    public void setDocFee(String docFee) {
        this.docFee = docFee;
    }

    public Object getDocProfilePath() {
        return docProfilePath;
    }

    public void setDocProfilePath(Object docProfilePath) {
        this.docProfilePath = docProfilePath;
    }

    public Object getDoctorFee() {
        return doctorFee;
    }

    public void setDoctorFee(Object doctorFee) {
        this.doctorFee = doctorFee;
    }

    public Object getIpadPhoto() {
        return ipadPhoto;
    }

    public void setIpadPhoto(Object ipadPhoto) {
        this.ipadPhoto = ipadPhoto;
    }

    public Object getMobilePhoto() {
        return mobilePhoto;
    }

    public void setMobilePhoto(Object mobilePhoto) {
        this.mobilePhoto = mobilePhoto;
    }

    public Object getProfilephoto() {
        return profilephoto;
    }

    public void setProfilephoto(Object profilephoto) {
        this.profilephoto = profilephoto;
    }

    public Integer getServiceRoleId() {
        return serviceRoleId;
    }

    public void setServiceRoleId(Integer serviceRoleId) {
        this.serviceRoleId = serviceRoleId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
