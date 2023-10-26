
package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("cashCardId")
    @Expose
    private Integer cashCardId;
    @SerializedName("mrNo")
    @Expose
    private Integer mrNo;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("hospitalNo")
    @Expose
    private String hospitalNo;
    @SerializedName("age")
    @Expose
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCashCardId() {
        return cashCardId;
    }

    public void setCashCardId(Integer cashCardId) {
        this.cashCardId = cashCardId;
    }

    public Integer getMrNo() {
        return mrNo;
    }

    public void setMrNo(Integer mrNo) {
        this.mrNo = mrNo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
