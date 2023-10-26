
package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentTokenModel {

    @SerializedName("MRNo")
    @Expose
    private int MRNo;
    @SerializedName("PatName")
    @Expose
    private String PatName;
    @SerializedName("TokenNo")
    @Expose
    private Integer TokenNo;
    @SerializedName("TokenStatus")
    @Expose
    private String TokenStatus;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("AppId")
    @Expose
    private int AppId;
    @SerializedName("avlstatusid")
    @Expose
    private String avlstatusid;

    public int getAppId() {
        return AppId;
    }

    public void setAppId(int appId) {
        AppId = appId;
    }

    public String getAvlstatusid() {
        return avlstatusid;
    }

    public void setAvlstatusid(String avlstatusid) {
        this.avlstatusid = avlstatusid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The MRNo
     */
    public int getMRNo() {
        return MRNo;
    }

    /**
     * 
     * @param MRNo
     *     The MRNo
     */
    public void setMRNo(int MRNo) {
        this.MRNo = MRNo;
    }

    /**
     * 
     * @return
     *     The PatName
     */
    public String getPatName() {
        return PatName;
    }

    /**
     * 
     * @param PatName
     *     The PatName
     */
    public void setPatName(String PatName) {
        this.PatName = PatName;
    }

    /**
     * 
     * @return
     *     The TokenNo
     */
    public Integer getTokenNo() {
        return TokenNo;
    }

    /**
     * 
     * @param TokenNo
     *     The TokenNo
     */
    public void setTokenNo(Integer TokenNo) {
        this.TokenNo = TokenNo;
    }

    /**
     * 
     * @return
     *     The TokenStatus
     */
    public String getTokenStatus() {
        return TokenStatus;
    }

    /**
     * 
     * @param TokenStatus
     *     The TokenStatus
     */
    public void setTokenStatus(String TokenStatus) {
        this.TokenStatus = TokenStatus;
    }

}
