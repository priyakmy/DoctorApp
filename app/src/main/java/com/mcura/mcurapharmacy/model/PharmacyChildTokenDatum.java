package com.mcura.mcurapharmacy.model;

/**
 * Created by mCURA1 on 8/30/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class PharmacyChildTokenDatum {
    @SerializedName("Pres_order_id")
    @Expose
    private Integer presOrderId;
    @SerializedName("tokenNo")
    @Expose
    private Integer tokenNo;
    @SerializedName("counterId")
    @Expose
    private Integer counterId;
    @SerializedName("counterName")
    @Expose
    private String counterName;

    public Integer getPresOrderId() {
        return presOrderId;
    }

    public void setPresOrderId(Integer presOrderId) {
        this.presOrderId = presOrderId;
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
}
