
package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patsubtenant {

    @SerializedName("Hospitalno")
    @Expose
    private Object hospitalno;
    @SerializedName("Mrno")
    @Expose
    private Integer mrno;
    @SerializedName("PatDemoid")
    @Expose
    private Integer patDemoid;
    @SerializedName("SubTenantId")
    @Expose
    private Integer subTenantId;

    public Object getHospitalno() {
        return hospitalno;
    }

    public void setHospitalno(Object hospitalno) {
        this.hospitalno = hospitalno;
    }

    public Integer getMrno() {
        return mrno;
    }

    public void setMrno(Integer mrno) {
        this.mrno = mrno;
    }

    public Integer getPatDemoid() {
        return patDemoid;
    }

    public void setPatDemoid(Integer patDemoid) {
        this.patDemoid = patDemoid;
    }

    public Integer getSubTenantId() {
        return subTenantId;
    }

    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

}
