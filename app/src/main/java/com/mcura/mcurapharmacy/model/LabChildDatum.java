package com.mcura.mcurapharmacy.model;

/**
 * Created by mCURA1 on 8/16/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabChildDatum {

    @SerializedName("LabOrderId")
    @Expose
    private Integer labOrderId;
    @SerializedName("OrderedAmount")
    @Expose
    private Integer orderedAmount;
    @SerializedName("OrderedDate")
    @Expose
    private String orderedDate;
    @SerializedName("OrderTransactionId")
    @Expose
    private Integer orderTransactionId;
    @SerializedName("OrderStatus")
    @Expose
    private Integer orderStatus;
    @SerializedName("connection")
    @Expose
    private Object connection;
    @SerializedName("DeliveredDate")
    @Expose
    private String deliveredDate;
    public Integer getLabOrderId() {
        return labOrderId;
    }

    public void setLabOrderId(Integer labOrderId) {
        this.labOrderId = labOrderId;
    }

    public Integer getOrderedAmount() {
        return orderedAmount;
    }

    public void setOrderedAmount(Integer orderedAmount) {
        this.orderedAmount = orderedAmount;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Integer getOrderTransactionId() {
        return orderTransactionId;
    }

    public void setOrderTransactionId(Integer orderTransactionId) {
        this.orderTransactionId = orderTransactionId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getConnection() {
        return connection;
    }

    public void setConnection(Object connection) {
        this.connection = connection;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }
}