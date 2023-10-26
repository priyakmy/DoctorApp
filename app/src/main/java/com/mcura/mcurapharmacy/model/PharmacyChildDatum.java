package com.mcura.mcurapharmacy.model;

/**
 * Created by mCURA1 on 6/14/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PharmacyChildDatum implements Serializable{

    @SerializedName("OrderStatus")
    @Expose
    private int orderStatus;
    @SerializedName("OrderTransactionId")
    @Expose
    private int orderTransactionId;
    @SerializedName("OrderedAmount")
    @Expose
    private double orderedAmount;
    @SerializedName("OrderedDate")
    @Expose
    private String orderedDate;
    @SerializedName("DeliveredDate")
    @Expose
    private String deliveredDate;
    @SerializedName("connection")
    @Expose
    private Object connection;

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderTransactionId() {
        return orderTransactionId;
    }

    public void setOrderTransactionId(int orderTransactionId) {
        this.orderTransactionId = orderTransactionId;
    }

    public double getOrderedAmount() {
        return orderedAmount;
    }

    public void setOrderedAmount(double orderedAmount) {
        this.orderedAmount = orderedAmount;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Object getConnection() {
        return connection;
    }

    public void setConnection(Object connection) {
        this.connection = connection;
    }

}
