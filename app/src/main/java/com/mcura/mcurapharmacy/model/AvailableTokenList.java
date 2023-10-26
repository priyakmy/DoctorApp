package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableTokenList {

    @SerializedName("MRNo")
    @Expose
    private Object MRNo;
    @SerializedName("PatName")
    @Expose
    private Object PatName;
    @SerializedName("TokenNo")
    @Expose
    private Integer TokenNo;
    @SerializedName("TokenStatus")
    @Expose
    private Object TokenStatus;

    /**
     *
     * @return
     *     The MRNo
     */
    public Object getMRNo() {
        return MRNo;
    }

    /**
     *
     * @param MRNo
     *     The MRNo
     */
    public void setMRNo(Object MRNo) {
        this.MRNo = MRNo;
    }

    /**
     *
     * @return
     *     The PatName
     */
    public Object getPatName() {
        return PatName;
    }

    /**
     *
     * @param PatName
     *     The PatName
     */
    public void setPatName(Object PatName) {
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
    public Object getTokenStatus() {
        return TokenStatus;
    }

    /**
     *
     * @param TokenStatus
     *     The TokenStatus
     */
    public void setTokenStatus(Object TokenStatus) {
        this.TokenStatus = TokenStatus;
    }

}
