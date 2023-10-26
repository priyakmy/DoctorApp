package com.mcura.mcurapharmacy.model;

/**
 * Created by mCURA1 on 8/29/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabChildTokenDatum {
    @SerializedName("LabOrderId")
    @Expose
    private Integer labOrderId;
    @SerializedName("tokenNo")
    @Expose
    private Integer tokenNo;
    @SerializedName("counterId")
    @Expose
    private Integer counterId;
    @SerializedName("counterName")
    @Expose
    private String counterName;
    @SerializedName("connection")
    @Expose
    private Object connection;

    public Integer getLabOrderId() {
        return labOrderId;
    }

    public void setLabOrderId(Integer labOrderId) {
        this.labOrderId = labOrderId;
    }

    public Integer getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(Integer tokenNo) {
        this.tokenNo = tokenNo;
    }

    public Integer getCounterId() {
        return counterId;
    }

    public void setCounterId(Integer counterId) {
        this.counterId = counterId;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public Object getConnection() {
        return connection;
    }

    public void setConnection(Object connection) {
        this.connection = connection;
    }
}
