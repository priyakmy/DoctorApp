package com.mcura.mcurapharmacy.model;

/**
 * Created by GauraVachhani on 29/12/15.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoModel {

    @SerializedName("AddressId")
    @Expose
    private Integer AddressId;
    @SerializedName("ContactId")
    @Expose
    private Integer ContactId;
    @SerializedName("CurrentStatusId")
    @Expose
    private Integer CurrentStatusId;
    @SerializedName("Dob")
    @Expose
    private String Dob;
    @SerializedName("GenderId")
    @Expose
    private Integer GenderId;
    @SerializedName("Uname")
    @Expose
    private String Uname;
    @SerializedName("UserId")
    @Expose
    private Integer UserId;

    /**
     *
     * @return
     * The AddressId
     */
    public Integer getAddressId() {
        return AddressId;
    }

    /**
     *
     * @param AddressId
     * The AddressId
     */
    public void setAddressId(Integer AddressId) {
        this.AddressId = AddressId;
    }

    /**
     *
     * @return
     * The ContactId
     */
    public Integer getContactId() {
        return ContactId;
    }

    /**
     *
     * @param ContactId
     * The ContactId
     */
    public void setContactId(Integer ContactId) {
        this.ContactId = ContactId;
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
     * The Dob
     */
    public String getDob() {
        return Dob;
    }

    /**
     *
     * @param Dob
     * The Dob
     */
    public void setDob(String Dob) {
        this.Dob = Dob;
    }

    /**
     *
     * @return
     * The GenderId
     */
    public Integer getGenderId() {
        return GenderId;
    }

    /**
     *
     * @param GenderId
     * The GenderId
     */
    public void setGenderId(Integer GenderId) {
        this.GenderId = GenderId;
    }

    /**
     *
     * @return
     * The Uname
     */
    public String getUname() {
        return Uname;
    }

    /**
     *
     * @param Uname
     * The Uname
     */
    public void setUname(String Uname) {
        this.Uname = Uname;
    }

    /**
     *
     * @return
     * The UserId
     */
    public Integer getUserId() {
        return UserId;
    }

    /**
     *
     * @param UserId
     * The UserId
     */
    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

}
