
package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchHospital {

    @SerializedName("AddressID")
    @Expose
    private Integer AddressID;
    @SerializedName("ContactID")
    @Expose
    private Integer ContactID;
    @SerializedName("SubTenantId")
    @Expose
    private Integer SubTenantId;
    @SerializedName("SubTenantName")
    @Expose
    private String SubTenantName;

    /**
     * 
     * @return
     *     The AddressID
     */
    public Integer getAddressID() {
        return AddressID;
    }

    /**
     * 
     * @param AddressID
     *     The AddressID
     */
    public void setAddressID(Integer AddressID) {
        this.AddressID = AddressID;
    }

    /**
     * 
     * @return
     *     The ContactID
     */
    public Integer getContactID() {
        return ContactID;
    }

    /**
     * 
     * @param ContactID
     *     The ContactID
     */
    public void setContactID(Integer ContactID) {
        this.ContactID = ContactID;
    }

    /**
     * 
     * @return
     *     The SubTenantId
     */
    public Integer getSubTenantId() {
        return SubTenantId;
    }

    /**
     * 
     * @param SubTenantId
     *     The SubTenantId
     */
    public void setSubTenantId(Integer SubTenantId) {
        this.SubTenantId = SubTenantId;
    }

    /**
     * 
     * @return
     *     The SubTenantName
     */
    public String getSubTenantName() {
        return SubTenantName;
    }

    /**
     * 
     * @param SubTenantName
     *     The SubTenantName
     */
    public void setSubTenantName(String SubTenantName) {
        this.SubTenantName = SubTenantName;
    }

}
