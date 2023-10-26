package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppointmentModel implements Serializable {

    @SerializedName("AppId")
    @Expose
    private Integer AppId;
    @SerializedName("AvlStatusId")
    @Expose
    private Integer AvlStatusId;
    @SerializedName("FromTime")
    @Expose
    private String FromTime;
    @SerializedName("TimeTableId")
    @Expose
    private Integer TimeTableId;
    @SerializedName("ToTime")
    @Expose
    private String ToTime;
    @SerializedName("appbookings")
    @Expose
    private Object appbookings;
    @SerializedName("patdemographics")
    @Expose
    private Object patdemographics;

    /**
     *
     * @return
     * The AppId
     */
    public Integer getAppId() {
        return AppId;
    }

    /**
     *
     * @param AppId
     * The AppId
     */
    public void setAppId(Integer AppId) {
        this.AppId = AppId;
    }

    /**
     *
     * @return
     * The AvlStatusId
     */
    public Integer getAvlStatusId() {
        return AvlStatusId;
    }

    /**
     *
     * @param AvlStatusId
     * The AvlStatusId
     */
    public void setAvlStatusId(Integer AvlStatusId) {
        this.AvlStatusId = AvlStatusId;
    }

    /**
     *
     * @return
     * The FromTime
     */
    public String getFromTime() {
        return FromTime;
    }

    /**
     *
     * @param FromTime
     * The FromTime
     */
    public void setFromTime(String FromTime) {
        this.FromTime = FromTime;
    }

    /**
     *
     * @return
     * The TimeTableId
     */
    public Integer getTimeTableId() {
        return TimeTableId;
    }

    /**
     *
     * @param TimeTableId
     * The TimeTableId
     */
    public void setTimeTableId(Integer TimeTableId) {
        this.TimeTableId = TimeTableId;
    }

    /**
     *
     * @return
     * The ToTime
     */
    public String getToTime() {
        return ToTime;
    }

    /**
     *
     * @param ToTime
     * The ToTime
     */
    public void setToTime(String ToTime) {
        this.ToTime = ToTime;
    }

    /**
     *
     * @return
     * The appbookings
     */
    public Object getAppbookings() {
        return appbookings;
    }

    /**
     *
     * @param appbookings
     * The appbookings
     */
    public void setAppbookings(Object appbookings) {
        this.appbookings = appbookings;
    }

    public Object getPatDemoGraphicses() {
        return patdemographics;
    }

    public void setPatDemoGraphicses(Object patDemoGraphicses) {
        this.patdemographics = patDemoGraphicses;
    }
}
