package com.mcura.mcurapharmacy.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueueStatus {
    @SerializedName("Count")
    @Expose
    private int count;
    @SerializedName("AppId")
    @Expose
    private Integer AppId;
    @SerializedName("MRNo")
    @Expose
    private Integer MRNo;
    @SerializedName("PatName")
    @Expose
    private String PatName;
    @SerializedName("TokenNo")
    @Expose
    private Integer TokenNo;
    @SerializedName("TokenStatus")
    @Expose
    private String TokenStatus;

    @SerializedName("AppNature")
    @Expose
    private String appNatureName;

    @SerializedName("AppNatureId")
    @Expose
    private int AppNatureId;

    @SerializedName("fromTime")
    @Expose
    private String fromTime;

    @SerializedName("scheduleDate")
    @Expose
    private String scheduleDate;

    @SerializedName("scheduleName")
    @Expose
    private String scheduleName;

    @SerializedName("toTime")
    @Expose
    private String toTime;


    public String getAppNatureName() {
        return appNatureName;
    }

    public void setAppNatureName(String appNatureName) {
        this.appNatureName = appNatureName;
    }

    public int getAppNatureId() {
        return AppNatureId;
    }

    public void setAppNatureId(int appNatureId) {
        AppNatureId = appNatureId;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getAppId() {
        return AppId;
    }

    public void setAppId(Integer appId) {
        AppId = appId;
    }

    /**
     *
     * @return
     *     The MRNo
     */
    public Integer getMRNo() {
        return MRNo;
    }

    /**
     *
     * @param MRNo
     *     The MRNo
     */
    public void setMRNo(Integer MRNo) {
        this.MRNo = MRNo;
    }

    /**
     *
     * @return
     *     The PatName
     */
    public Object getPatName() {
        return PatName;
    }

    /**
     *
     * @param PatName
     *     The PatName
     */
    public void setPatName(String PatName) {
        this.PatName = PatName;
    }

    /**
     *
     * @return
     *     The TokenNo
     */
    public Integer getTokenNo() {
        return TokenNo;
    }

    /**
     *
     * @param TokenNo
     *     The TokenNo
     */
    public void setTokenNo(Integer TokenNo) {
        this.TokenNo = TokenNo;
    }

    /**
     *
     * @return
     *     The TokenStatus
     */
    public String getTokenStatus() {
        return TokenStatus;
    }

    /**
     *
     * @param TokenStatus
     *     The TokenStatus
     */
    public void setTokenStatus(String TokenStatus) {
        this.TokenStatus = TokenStatus;
    }

}