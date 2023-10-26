
package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Drug {

    @SerializedName("Brand_Name")
    @Expose
    private String brandName;
    @SerializedName("Fav_id")
    @Expose
    private int favId;
    @SerializedName("Form")
    @Expose
    private String form;
    @SerializedName("Form_Pic_Path")
    @Expose
    private String formPicPath;
    @SerializedName("Generic_Name")
    @Expose
    private String genericName;
    @SerializedName("Med_Id")
    @Expose
    private int medId;
    @SerializedName("Strength")
    @Expose
    private String strength;
    @SerializedName("Subtenant_Id")
    @Expose
    private int subtenantId;
    @SerializedName("clipArt")
    @Expose
    private String clipArt;
    @SerializedName("costOfMinPack")
    @Expose
    private double costOfMinPack;
    @SerializedName("dosageUnit")
    @Expose
    private int dosageUnit;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("minimumUnit")
    @Expose
    private int minimumUnit;
    @SerializedName("unitOfMeasurement")
    @Expose
    private String unitOfMeasurement;
    @SerializedName("unitPrice")
    @Expose
    private double unitPrice;
    @SerializedName("unitsPerMinPack")
    @Expose
    private int unitsPerMinPack;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getFavId() {
        return favId;
    }

    public void setFavId(int favId) {
        this.favId = favId;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getFormPicPath() {
        return formPicPath;
    }

    public void setFormPicPath(String formPicPath) {
        this.formPicPath = formPicPath;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public int getSubtenantId() {
        return subtenantId;
    }

    public void setSubtenantId(int subtenantId) {
        this.subtenantId = subtenantId;
    }

    public String getClipArt() {
        return clipArt;
    }

    public void setClipArt(String clipArt) {
        this.clipArt = clipArt;
    }

    public double getCostOfMinPack() {
        return costOfMinPack;
    }

    public void setCostOfMinPack(double costOfMinPack) {
        this.costOfMinPack = costOfMinPack;
    }

    public int getDosageUnit() {
        return dosageUnit;
    }

    public void setDosageUnit(int dosageUnit) {
        this.dosageUnit = dosageUnit;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getMinimumUnit() {
        return minimumUnit;
    }

    public void setMinimumUnit(int minimumUnit) {
        this.minimumUnit = minimumUnit;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsPerMinPack() {
        return unitsPerMinPack;
    }

    public void setUnitsPerMinPack(int unitsPerMinPack) {
        this.unitsPerMinPack = unitsPerMinPack;
    }
}
