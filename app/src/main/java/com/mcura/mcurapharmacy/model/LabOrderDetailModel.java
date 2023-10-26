package com.mcura.mcurapharmacy.model;

import com.mcura.mcurapharmacy.Utils.EnumType;

import java.util.ArrayList;

/**
 * Created by mCURA1 on 7/17/2017.
 */

public class LabOrderDetailModel {
    private EnumType.LabObjNature labTestNature;
    private String labTestName;
    private String labTestId;
    private String labTestCost;
    private String labTestInstId;
    private String labTestInstruction;
    private ArrayList<LabOrderDetailModel> children = new ArrayList<>();
    private boolean isSelected;

    public EnumType.LabObjNature getLabTestNature() {
        return labTestNature;
    }

    public void setLabTestNature(EnumType.LabObjNature labTestNature) {
        this.labTestNature = labTestNature;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    public String getLabTestId() {
        return labTestId;
    }

    public void setLabTestId(String labTestId) {
        this.labTestId = labTestId;
    }

    public String getLabTestCost() {
        return labTestCost;
    }

    public void setLabTestCost(String labTestCost) {
        this.labTestCost = labTestCost;
    }

    public String getLabTestInstId() {
        return labTestInstId;
    }

    public void setLabTestInstId(String labTestInstId) {
        this.labTestInstId = labTestInstId;
    }

    public String getLabTestInstruction() {
        return labTestInstruction;
    }

    public void setLabTestInstruction(String labTestInstruction) {
        this.labTestInstruction = labTestInstruction;
    }

    public ArrayList<LabOrderDetailModel> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<LabOrderDetailModel> children) {
        this.children = children;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
