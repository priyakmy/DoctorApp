
package com.mcura.mcurapharmacy.model.CreatePaymentRajorPayResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("Attributes")
    @Expose
    private Attributes attributes;

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}
