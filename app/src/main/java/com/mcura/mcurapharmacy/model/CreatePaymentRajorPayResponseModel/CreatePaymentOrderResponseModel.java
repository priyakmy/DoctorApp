
package com.mcura.mcurapharmacy.model.CreatePaymentRajorPayResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreatePaymentOrderResponseModel {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
