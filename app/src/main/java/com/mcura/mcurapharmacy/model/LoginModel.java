package com.mcura.mcurapharmacy.model;

/**
 * Created by GauraVachhani on 28/12/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("CurrentStatusId")
    @Expose
    private Integer CurrentStatusId;
    @SerializedName("Domain")
    @Expose
    private String Domain;
    @SerializedName("LoginId")
    @Expose
    private Integer LoginId;
    @SerializedName("LoginName")
    @Expose
    private String LoginName;
    @SerializedName("Pin")
    @Expose
    private Integer Pin;
    @SerializedName("Pwd")
    @Expose
    private String Pwd;
    @SerializedName("UserRoleId")
    @Expose
    private Integer UserRoleId;

    @SerializedName("RoleId")
    @Expose
    private Integer RoleId;

    public Integer getRoleId() {
        return RoleId;
    }

    public void setRoleId(Integer roleId) {
        RoleId = roleId;
    }

    /**
     *
     * @return
     * The CurrentStatusId
     */
    public Integer getCurrentStatusId() {
        return CurrentStatusId;
    }

    /**
     *
     * @param CurrentStatusId
     * The CurrentStatusId
     */
    public void setCurrentStatusId(Integer CurrentStatusId) {
        this.CurrentStatusId = CurrentStatusId;
    }

    /**
     *
     * @return
     * The Domain
     */
    public String getDomain() {
        return Domain;
    }

    /**
     *
     * @param Domain
     * The Domain
     */
    public void setDomain(String Domain) {
        this.Domain = Domain;
    }

    /**
     *
     * @return
     * The LoginId
     */
    public Integer getLoginId() {
        return LoginId;
    }

    /**
     *
     * @param LoginId
     * The LoginId
     */
    public void setLoginId(Integer LoginId) {
        this.LoginId = LoginId;
    }

    /**
     *
     * @return
     * The LoginName
     */
    public String getLoginName() {
        return LoginName;
    }

    /**
     *
     * @param LoginName
     * The LoginName
     */
    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }

    /**
     *
     * @return
     * The Pin
     */
    public Integer getPin() {
        return Pin;
    }

    /**
     *
     * @param Pin
     * The Pin
     */
    public void setPin(Integer Pin) {
        this.Pin = Pin;
    }

    /**
     *
     * @return
     * The Pwd
     */
    public String getPwd() {
        return Pwd;
    }

    /**
     *
     * @param Pwd
     * The Pwd
     */
    public void setPwd(String Pwd) {
        this.Pwd = Pwd;
    }

    /**
     *
     * @return
     * The UserRoleId
     */
    public Integer getUserRoleId() {
        return UserRoleId;
    }

    /**
     *
     * @param UserRoleId
     * The UserRoleId
     */
    public void setUserRoleId(Integer UserRoleId) {
        this.UserRoleId = UserRoleId;
    }

}