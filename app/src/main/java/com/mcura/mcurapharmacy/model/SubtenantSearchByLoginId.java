
package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubtenantSearchByLoginId {

    @SerializedName("AddressID")
    @Expose
    private Integer addressID;
    @SerializedName("ContactID")
    @Expose
    private Integer contactID;
    @SerializedName("SubTenantId")
    @Expose
    private Integer subTenantId;
    @SerializedName("SubTenantName")
    @Expose
    private String subTenantName;

    public Integer getAddressID() {
        return addressID;
    }

    public void setAddressID(Integer addressID) {
        this.addressID = addressID;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public Integer getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    public String getSubTenantName() {
        return subTenantName;
    }

    public void setSubTenantName(String subTenantName) {
        this.subTenantName = subTenantName;
    }

}
