package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mCURA1 on 5/2/2017.
 */

public class LabPharmacyPostResponseModel {
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("filepath")
    @Expose
    private String filepath;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("rpOrderId")
    @Expose
    private String rpOrderId;

    public String getRpOrderId() {
        return rpOrderId;
    }

    public void setRpOrderId(String rpOrderId) {
        this.rpOrderId = rpOrderId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
