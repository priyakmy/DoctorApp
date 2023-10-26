package com.mcura.mcurapharmacy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleModel implements Serializable {
    @SerializedName("Priority_id")
    @Expose
    private Integer PriorityId;
    @SerializedName("app_duration")
    @Expose
    private Integer appDuration;
    @SerializedName("app_slot_id")
    @Expose
    private Integer appSlotId;
    @SerializedName("appointments")
    @Expose
    private List<AppointmentModel> appointments = new ArrayList<AppointmentModel>();
    @SerializedName("current_status")
    @Expose
    private String currentStatus;
    @SerializedName("current_status_id")
    @Expose
    private Integer currentStatusId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("day_id")
    @Expose
    private Integer dayId;
    @SerializedName("facility_type_id")
    @Expose
    private Integer facilityTypeId;
    @SerializedName("from_time")
    @Expose
    private String fromTime;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("schedule_id")
    @Expose
    private Integer scheduleId;
    @SerializedName("schedule_name")
    @Expose
    private String scheduleName;
    @SerializedName("sub_tenant_id")
    @Expose
    private Integer subTenantId;
    @SerializedName("time_slot_id")
    @Expose
    private Integer timeSlotId;
    @SerializedName("time_table_id")
    @Expose
    private Integer timeTableId;
    @SerializedName("to_time")
    @Expose
    private String toTime;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_role_id")
    @Expose
    private Integer userRoleId;

    /**
     *
     * @return
     * The PriorityId
     */
    public Integer getPriorityId() {
        return PriorityId;
    }

    /**
     *
     * @param PriorityId
     * The Priority_id
     */
    public void setPriorityId(Integer PriorityId) {
        this.PriorityId = PriorityId;
    }

    /**
     *
     * @return
     * The appDuration
     */
    public Integer getAppDuration() {
        return appDuration;
    }

    /**
     *
     * @param appDuration
     * The app_duration
     */
    public void setAppDuration(Integer appDuration) {
        this.appDuration = appDuration;
    }

    /**
     *
     * @return
     * The appSlotId
     */
    public Integer getAppSlotId() {
        return appSlotId;
    }

    /**
     *
     * @param appSlotId
     * The app_slot_id
     */
    public void setAppSlotId(Integer appSlotId) {
        this.appSlotId = appSlotId;
    }

    /**
     *
     * @return
     * The appointments
     */
    public List<AppointmentModel> getAppointments() {
        return appointments;
    }

    /**
     *
     * @param appointments
     * The appointments
     */
    public void setAppointments(List<AppointmentModel> appointments) {
        this.appointments = appointments;
    }

    /**
     *
     * @return
     * The currentStatus
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     *
     * @param currentStatus
     * The current_status
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     *
     * @return
     * The currentStatusId
     */
    public Integer getCurrentStatusId() {
        return currentStatusId;
    }

    /**
     *
     * @param currentStatusId
     * The current_status_id
     */
    public void setCurrentStatusId(Integer currentStatusId) {
        this.currentStatusId = currentStatusId;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The day
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The dayId
     */
    public Integer getDayId() {
        return dayId;
    }

    /**
     *
     * @param dayId
     * The day_id
     */
    public void setDayId(Integer dayId) {
        this.dayId = dayId;
    }

    /**
     *
     * @return
     * The facilityTypeId
     */
    public Integer getFacilityTypeId() {
        return facilityTypeId;
    }

    /**
     *
     * @param facilityTypeId
     * The facility_type_id
     */
    public void setFacilityTypeId(Integer facilityTypeId) {
        this.facilityTypeId = facilityTypeId;
    }

    /**
     *
     * @return
     * The fromTime
     */
    public String getFromTime() {
        return fromTime;
    }

    /**
     *
     * @param fromTime
     * The from_time
     */
    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     *
     * @return
     * The priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     *
     * @param priority
     * The priority
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     *
     * @return
     * The roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     *
     * @param roleId
     * The role_id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     *
     * @return
     * The scheduleId
     */
    public Integer getScheduleId() {
        return scheduleId;
    }

    /**
     *
     * @param scheduleId
     * The schedule_id
     */
    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     *
     * @return
     * The scheduleName
     */
    public String getScheduleName() {
        return scheduleName;
    }

    /**
     *
     * @param scheduleName
     * The schedule_name
     */
    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    /**
     *
     * @return
     * The subTenantId
     */
    public Integer getSubTenantId() {
        return subTenantId;
    }

    /**
     *
     * @param subTenantId
     * The sub_tenant_id
     */
    public void setSubTenantId(Integer subTenantId) {
        this.subTenantId = subTenantId;
    }

    /**
     *
     * @return
     * The timeSlotId
     */
    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    /**
     *
     * @param timeSlotId
     * The time_slot_id
     */
    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    /**
     *
     * @return
     * The timeTableId
     */
    public Integer getTimeTableId() {
        return timeTableId;
    }

    /**
     *
     * @param timeTableId
     * The time_table_id
     */
    public void setTimeTableId(Integer timeTableId) {
        this.timeTableId = timeTableId;
    }

    /**
     *
     * @return
     * The toTime
     */
    public String getToTime() {
        return toTime;
    }

    /**
     *
     * @param toTime
     * The to_time
     */
    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The userRoleId
     */
    public Integer getUserRoleId() {
        return userRoleId;
    }

    /**
     *
     * @param userRoleId
     * The user_role_id
     */
    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

}