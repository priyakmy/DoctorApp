
package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyOrderDetailModel {

    @SerializedName("AmountofPrescribedPackUnit")
    @Expose
    private double amountofPrescribedPackUnit;
    @SerializedName("MinbillableUnit")
    @Expose
    private int minbillableUnit;
    @SerializedName("OrderedUnit")
    @Expose
    private int orderedUnit;
    @SerializedName("OrderedUnitAmount")
    @Expose
    private double orderedUnitAmount;
    @SerializedName("PrescOrdId")
    @Expose
    private int prescOrdId;
    @SerializedName("PrescribedUnit")
    @Expose
    private int prescribedUnit;
    @SerializedName("Regime_id")
    @Expose
    private int regimeId;
    @SerializedName("dosage")
    @Expose
    private String dosage;
    @SerializedName("dosageId")
    @Expose
    private int dosageId;
    @SerializedName("dosageInsId")
    @Expose
    private int dosageInsId;
    @SerializedName("dosageInst")
    @Expose
    private String dosageInst;
    @SerializedName("drug")
    @Expose
    private Drug drug;
    @SerializedName("followupDurationUnit")
    @Expose
    private String followupDurationUnit;
    @SerializedName("followupDurationno")
    @Expose
    private int followupDurationno;
    @SerializedName("followupId")
    @Expose
    private int followupId;
    @SerializedName("medId")
    @Expose
    private int medId;
    @SerializedName("prescribedPack")
    @Expose
    private int prescribedPack;
    @SerializedName("prescriptionId")
    @Expose
    private int prescriptionId;
    @SerializedName("subTenantId")
    @Expose
    private int subTenantId;
    @SerializedName("unitPrice")
    @Expose
    private double unitPrice;

    private double BillableAmount;
    private int BillableUnit;
    private String selectedFollowUpId;

    @SerializedName("orderedFollowupDurationUnit")
    @Expose
    private String orderedFollowupDurationUnit;
    @SerializedName("orderedFollowupDurationno")
    @Expose
    private int orderedFollowupDurationno;
    @SerializedName("orderedFollowupId")
    @Expose
    private int orderedFollowupId;

    public double getAmountofPrescribedPackUnit() {
        return amountofPrescribedPackUnit;
    }

    public void setAmountofPrescribedPackUnit(double amountofPrescribedPackUnit) {
        this.amountofPrescribedPackUnit = amountofPrescribedPackUnit;
    }

    public int getMinbillableUnit() {
        return minbillableUnit;
    }

    public void setMinbillableUnit(int minbillableUnit) {
        this.minbillableUnit = minbillableUnit;
    }

    public int getOrderedUnit() {
        return orderedUnit;
    }

    public void setOrderedUnit(int orderedUnit) {
        this.orderedUnit = orderedUnit;
    }

    public double getOrderedUnitAmount() {
        return orderedUnitAmount;
    }

    public void setOrderedUnitAmount(double orderedUnitAmount) {
        this.orderedUnitAmount = orderedUnitAmount;
    }

    public int getPrescOrdId() {
        return prescOrdId;
    }

    public void setPrescOrdId(int prescOrdId) {
        this.prescOrdId = prescOrdId;
    }

    public int getPrescribedUnit() {
        return prescribedUnit;
    }

    public void setPrescribedUnit(int prescribedUnit) {
        this.prescribedUnit = prescribedUnit;
    }

    public int getRegimeId() {
        return regimeId;
    }

    public void setRegimeId(int regimeId) {
        this.regimeId = regimeId;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public int getDosageId() {
        return dosageId;
    }

    public void setDosageId(int dosageId) {
        this.dosageId = dosageId;
    }

    public int getDosageInsId() {
        return dosageInsId;
    }

    public void setDosageInsId(int dosageInsId) {
        this.dosageInsId = dosageInsId;
    }

    public String getDosageInst() {
        return dosageInst;
    }

    public void setDosageInst(String dosageInst) {
        this.dosageInst = dosageInst;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public String getFollowupDurationUnit() {
        return followupDurationUnit;
    }

    public void setFollowupDurationUnit(String followupDurationUnit) {
        this.followupDurationUnit = followupDurationUnit;
    }

    public int getFollowupDurationno() {
        return followupDurationno;
    }

    public void setFollowupDurationno(int followupDurationno) {
        this.followupDurationno = followupDurationno;
    }

    public int getFollowupId() {
        return followupId;
    }

    public void setFollowupId(int followupId) {
        this.followupId = followupId;
    }

    public int getMedId() {
        return medId;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }

    public int getPrescribedPack() {
        return prescribedPack;
    }

    public void setPrescribedPack(int prescribedPack) {
        this.prescribedPack = prescribedPack;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(int subTenantId) {
        this.subTenantId = subTenantId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getBillableAmount() {
        return BillableAmount;
    }

    public void setBillableAmount(double billableAmount) {
        BillableAmount = billableAmount;
    }

    public int getBillableUnit() {
        return BillableUnit;
    }

    public void setBillableUnit(int billableUnit) {
        BillableUnit = billableUnit;
    }

    public String getSelectedFollowUpId() {
        return selectedFollowUpId;
    }

    public void setSelectedFollowUpId(String selectedFollowUpId) {
        this.selectedFollowUpId = selectedFollowUpId;
    }

    public String getOrderedFollowupDurationUnit() {
        return orderedFollowupDurationUnit;
    }

    public void setOrderedFollowupDurationUnit(String orderedFollowupDurationUnit) {
        this.orderedFollowupDurationUnit = orderedFollowupDurationUnit;
    }

    public int getOrderedFollowupDurationno() {
        return orderedFollowupDurationno;
    }

    public void setOrderedFollowupDurationno(int orderedFollowupDurationno) {
        this.orderedFollowupDurationno = orderedFollowupDurationno;
    }

    public int getOrderedFollowupId() {
        return orderedFollowupId;
    }

    public void setOrderedFollowupId(int orderedFollowupId) {
        this.orderedFollowupId = orderedFollowupId;
    }
}
