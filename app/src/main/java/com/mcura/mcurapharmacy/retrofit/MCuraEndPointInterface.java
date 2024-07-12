package com.mcura.mcurapharmacy.retrofit;

import com.google.gson.JsonObject;
import com.mcura.mcurapharmacy.model.CounterDataModel;
import com.mcura.mcurapharmacy.model.CounterModel;
import com.mcura.mcurapharmacy.model.CreatePaymentOrderResponseModel;
import com.mcura.mcurapharmacy.model.CurrentTokenModel;
import com.mcura.mcurapharmacy.model.GenerateTokenResultModel;
import com.mcura.mcurapharmacy.model.LabOrderModel;
import com.mcura.mcurapharmacy.model.LabPharmacyPostResponseModel;
import com.mcura.mcurapharmacy.model.LoginModel;
import com.mcura.mcurapharmacy.model.PharmacyDatum;
import com.mcura.mcurapharmacy.model.PharmacyModel;
import com.mcura.mcurapharmacy.model.PharmacyOrderDetailModel;
import com.mcura.mcurapharmacy.model.PharmacyPrescOrdersUpdateModel;
import com.mcura.mcurapharmacy.model.PostPatMedRecord;
import com.mcura.mcurapharmacy.model.PostPaymentModel;
import com.mcura.mcurapharmacy.model.QueueStatus;
import com.mcura.mcurapharmacy.model.ScheduleModel;
import com.mcura.mcurapharmacy.model.SearchHospital;
import com.mcura.mcurapharmacy.model.SubtenantSearchByLoginId;
import com.mcura.mcurapharmacy.model.TokenStatusModel;
import com.mcura.mcurapharmacy.model.UpdateLabPharmacyModel;
import com.mcura.mcurapharmacy.model.UserInfoModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit2.Call;

/**
 * Created by mcura on 11/5/2015.
 */
public interface MCuraEndPointInterface {
    @GET("/GetLogin")
    void getLogin(@Query("UseraName") String username, @Query("PWD") String password, Callback<LoginModel>
            restCallback);

    @GET("/GetUser")
    void getUserInfo(@Query("UserRoleID") int userRoleId, Callback<UserInfoModel> restCallback);

    @GET("/GetRecord")
    void getRecord(@Query("UserRoleID") int userRoleId, @Query("RecordId") int recordId, Callback<String> restCallback);

    @GET("/getSubTenant")
    void getHospital(@Query("userroleid") int userroleid, Callback<SearchHospital[]> resCallback);

    @GET("/PharmacyOrderSearchList")
    void pharmacyOrderSearchList(@Query("SubTenantId") int subtanentId, @Query("Statusid") int Statusid, @Query("userroleid") int userroleid, @Query("dateFrom") String dateFrom, @Query("dateto") String dateto, Callback<PharmacyModel[]> resCallback);

    //PharmacyOrderSearchList_v3
    //PharmacyOrderSearchList_v2
    //PharmacyOrderSearchList_v1
    @GET("/PharmacyOrderSearchList_v3")
    void pharmacyOrderSearchList_v1(@Query("SubTenantId") int subtanentId, @Query("userroleid") int userroleid, @Query("dateFrom") String dateFrom, @Query("dateto") String dateto, Callback<PharmacyDatum> resCallback);

    //GetLabOrdersList_v3
    //GetLabOrdersList_v4
    //GetLabOrdersList_v5
    //GetLabOrdersList_v7
    //getLabOrdersList_v10
    @GET("/getLabOrdersList_v11")
    void getLabOrdersList_v2(@Query("SubTenantId") int subtanentId, @Query("userroleid") int userroleid, @Query("dateFrom") String dateFrom, @Query("dateto") String dateto, Callback<LabOrderModel> resCallback);

    @GET("/SubtenantSearchByLoginId")
    void subtenantSearchByLoginId(@Query("UserRoleID") int userroleid, @Query("loginID") int loginID, @Query("FacilityTypeId") int facilityTypeId, Callback<SubtenantSearchByLoginId[]> resCallback);

    //http://test.tn.mcura.com/health_dev.svc/Json/getSubTenantForFacility?facilitySubtenantId=514
    @GET("/getSubTenantForFacility")
    void getSubTenantForFacility(@Query("facilitySubtenantId") int userroleid, Callback<SubtenantLoginId[]> resCallback);


    @GET("/getCounterList")
    void getCounterList(@Query("SubTenantId") int subtanentId, @Query("serviceRoleIId") int serviceRoleIId, Callback<CounterDataModel[]> resCallback);

    /////////////
    @POST("/UploadOrderImage")
    void uploadOrderImage(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/PostPat_Med_Record")
    void postPat_Med_Record(@Body JsonObject mObj, Callback<PostPatMedRecord> restCallback);

    @POST("/FileUploadPDF")
    void fileUploadPDF(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/AddDrMessage")
    void addDrMessage(@Body JsonObject mObj, Callback<String> restCallback);

    @POST("/Generate_Token_Chart")
    void generate_Token_Chart(@Body JsonObject mObj, Callback<GenerateTokenResultModel> restCallback);

    @POST("/Patient_Check_In_WithOutPayment_v1")
    void patient_Check_In(@Body JsonObject mObj, Callback<GenerateTokenResultModel> restCallback);

    @POST("/updateTokenDetails")
    void updateTokenDetails(@Body JsonObject mObj, Callback<GenerateTokenResultModel> restCallback);

    @GET("/TokenView_Other")
    void tokenViewOther(@Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subtanentId, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<QueueStatus[]> restCallback);

    @POST("/postActivityTracker_v2")
    void postActivityTracker(@Body JsonObject jsonObject, Callback<PostActivityTrackerModel> restCallback);

    @GET("/Patient_Visit_Entry")
    void patient_Visit_Entry(@Query("MRNo") int mrno, @Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subtanentId, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<GenerateTokenResultModel> restCallback);

    @GET("/Cancel_Token_List")
    void cancel_Token_List(@Query("MRNo") int mrno, @Query("UserRoleId") int userRoleId, @Query("SubTenantId") int subtanentId, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<GenerateTokenResultModel> restCallback);

    @GET("/PharmacyPrescOrdersUpdate")
    void pharmacyPrescOrdersUpdate(@Query("UserRoleID") int userRoleID, @Query("presciptionID") int presciptionID, @Query("Statusid") int statusid,
                                   Callback<PharmacyPrescOrdersUpdateModel> restCallback);

    @GET("/Update_UpdateLabPharmacy")
    void update_UpdateLabPharmacy(@Query("UserRoleID") int userRoleID, @Query("Pres_order_id") int pres_order_id, @Query("EST_Billno") int EST_Billno, @Query("Days") int Days, @Query("Amount") Double Amount, @Query("LabPharmacyType") int LabPharmacyType,
                                  Callback<UpdateLabPharmacyModel> restCallback);

    //PostPayment_v4
    //PostPayment_v3
    //PostPayment_v2
    //PostPayment_v1
    @POST("/PostPayment_v4")
    void postPayment_v1(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    //PostPaymentPharmacyFee_v4
    //PostPaymentPharmacyFee_v5
    //PostPaymentPharmacyFee_v6
    @POST("/PostPaymentPharmacyFee_v6")
    void PostPaymentPharmacyFee(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    //PostPaymentLabFee_v4
    //PostPaymentLabFee_v5
    //PostPaymentLabFee_v6
    // PostPaymentLabFee_v6
    @POST("/Post_KH_PaymentLabFee_v4")
    void PostPaymentLabFee(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @GET("/VerifyOTP")
    void verifyOTP(@Query("Mrno") int mrno, @Query("otp") int otp, Callback<PostPaymentModel> restCallback);

    @POST("/InsertAppointments")
    void insertAppointments(@Body JsonObject mObj, Callback<String> restCallback);

    @GET("/Schedule_Status")
    void scheduleStatus(@Query("ScheduleId") int scheduleId, Callback<GenerateTokenResultModel> resCallback);

    @GET("/Token_Status")
    void token_Status(@Query("Mrno") int mrno, @Query("UserRoleId") int userRoleID, @Query("SubTenantId") int sub_tenant_id, @Query("ScheduleId") int scheduleId, @Query("Date") String date, Callback<TokenStatusModel[]> restCallback);

    @GET("/Next_Availabel_Token")
    void next_Availabel_Token(@Query("UserRoleId") int userRoleID, @Query("SubTenantId") int sub_tenant_id, @Query("ScheduleId") int scheduleId, @Query("Date") String date,
                              Callback<CurrentTokenModel[]> restCallback);

    @GET("/SavedPharmacyOrderdetail")
    void savedPharmacyOrderdetail(@Query("prescritionId") int prescritionId, @Query("UserRoleID") int UserRoleID, Callback<PharmacyOrderDetailModel[]> restCallback);

    //pharmacyOrderDetailByTxnId_v1
    //pharmacyOrderDetailByTxnId
    @GET("/pharmacyOrderDetailByTxnId_v1")
    void pharmacyOrderDetailByTxnId(@Query("prescriptionId") int prescritionId, @Query("UserRoleID") int UserRoleID, @Query("orderTransactionId") int orderTransactionId, Callback<PharmacyOrderDetailModel[]> restCallback);

    @POST("/postPharmacyOrderDetails")
    void postPharmacyOrderDetails(@Body JsonObject mObj, Callback<LabPharmacyPostResponseModel> restCallback);

    //PostPayment_v3
    @POST("/PostPayment_v3")
    void PostPayment_v2(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @GET("/PharmacyOrderdetail")
    void pharmacyOrderdetail(@Query("prescritionId") int prescritionId, @Query("UserRoleID") int UserRoleID, Callback<PharmacyOrderDetailModel[]> restCallback);

//    @GET("/GetLabOrderDetails_v1")
//    void getLabOrderDetails(@Query("UserRoleID") int userRoleID, @Query("LabOrderId") int labOrderId, Callback<GetLabOrderDetails> restCallback);

    @POST("/PharmacyPrescOrdersUpdateByTxnId")
    void pharmacyPrescOrdersUpdateBy_TxnId(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    @POST("/LabPrescOrdersUpdateByTxnId")
    void labPrescOrdersUpdateByTxnId(@Body JsonObject mObj, Callback<PostPaymentModel> restCallback);

    //PostLabOrderdetails_v2"
    @POST("/Post_KH_LabOrderdetails_v1")
    void postLabOrderdetails_v2(@Body JsonObject mObj, Callback<LabPharmacyPostResponseModel> restCallback);

    @GET("/getSchedule_Day_v1")
    void getSchedule_Day(@Query("userRoleId") int userRoleID, @Query("CurDate") String currDate, Callback<ScheduleModel[]> restCallback);

    @POST("/PostPaymentPartnerFee")
    Call<PostPaymentModel> postPaymentlabandpharmacyFee(@Body JsonObject mObj);


    @GET("/GetSavedLabOrderDetails_v1")
    void getSavedLabOrderDetails(@Query("UserRoleId") int userRoleId, @Query("LabOrderId") int labOrderId, @Query("orderTransactionId") int orderTransactionId, Callback<LabOrderModel> restCallback);

    @retrofit2.http.POST("CreatePaymentOrder_V2")
    Call<CreatePaymentOrderResponseModel> createPaymentOrder(@retrofit2.http.Body JsonObject mObj);
}

