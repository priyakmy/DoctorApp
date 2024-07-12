package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostPaymentModel {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("filepath")
    @Expose
    private Object filepath;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("statusId")
    @Expose
    private int statusId;
    @SerializedName("rpOrderId")
    @Expose
    private Object rpOrderId;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    /**
     *
     * @return
     *     The iD
     */
    public String getID() {
        return iD;
    }

    /**
     *
     * @param iD
     *     The ID
     */
    public void setID(String iD) {
        this.iD = iD;
    }

    /**
     *
     * @return
     *     The filepath
     */
    public Object getFilepath() {
        return filepath;
    }

    /**
     *
     * @param filepath
     *     The filepath
     */
    public void setFilepath(Object filepath) {
        this.filepath = filepath;
    }

    /**
     *
     * @return
     *     The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     *
     * @param msg
     *     The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return The rpOrderId
     */
    public String getRpOrderId() {
        return (String) rpOrderId;
    }

    /**
     *
     * @param rpOrderId
     *     The rpOrderId
     */
    public void setRpOrderId(Object rpOrderId) {
        this.rpOrderId = rpOrderId;
    }

}
