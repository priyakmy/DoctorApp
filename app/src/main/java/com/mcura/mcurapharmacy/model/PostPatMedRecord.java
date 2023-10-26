package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by mCURA1 on 1/3/2017.
 */
public class PostPatMedRecord {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("RowsAdded")
    @Expose
    private String rowsAdded;

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
     *     The rowsAdded
     */
    public String getRowsAdded() {
        return rowsAdded;
    }

    /**
     *
     * @param rowsAdded
     *     The RowsAdded
     */
    public void setRowsAdded(String rowsAdded) {
        this.rowsAdded = rowsAdded;
    }

}