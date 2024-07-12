package com.mcura.mcurapharmacy.adpater;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mcura.mcurapharmacy.Helper.Helper;
import com.mcura.mcurapharmacy.Interface.PharmacyInterface;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.activity.PharmacyActivity;
import com.mcura.mcurapharmacy.model.FollowUpModel;
import com.mcura.mcurapharmacy.model.LabDatum;
import com.mcura.mcurapharmacy.model.LabPharmacyPostResponseModel;
import com.mcura.mcurapharmacy.model.PharmacyModel;
import com.mcura.mcurapharmacy.model.PharmacyOrderDetailModel;
import com.mcura.mcurapharmacy.model.PostPaymentModel;
import com.mcura.mcurapharmacy.paymentFromRazorpay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mCURA1 on 4/13/2017.
 */

public class PharmacyOrderDialogAdapter extends BaseAdapter {
    private AlertDialog dialog;
    private TextView btn_ordernow;
    private String completeDate;
    private String time;
    private String subTanantName;
    private String subTanantAddress;
    private String subTanantContact;
    private String hId;
    private String patContact;
    private int frontOfficeUserRoleId;
    private int subTanentId;
    private int scheduleId;
    private SharedPreferences mSharedPreference;
    private LinkedHashMap<Integer, JsonObject> jsonObjectHashMap;
    private LayoutInflater mInflater;
    Context context;
    public MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    private boolean[] isChecked;
    Map<Integer, Integer> myMap = new HashMap<Integer, Integer>();
    ArrayList<FollowUpModel> followUpModelArrayList;
    PharmacyOrderDetailModel[] pharmacyOrderDetailModels;
    TextView tv_total_amount;
    int noOfDays = 0, selectedDays = 0;
    TextView btn_paynow;
    private double totalAmount = 0.0;
    private String paymentMode = "1";
    String hospitalId;
    int userRoleId, mrno;
    ImageView tv_check_uncheck;
    private boolean isAllChecked;
    private ViewHolder holder;
    private JsonObject ordDetailObj;
    Integer prescriptionId;
    private int orderKey;
    Integer prescriptionOrderId;
    private String payableAmount;
    private int appNatureId;
    private String paymentDescription;
    private WebView myWebView;
    private static final int ORDER_ONLY = 1;
    private static final int ORDER_AND_PAY = 2;
    private int orderStatus;
    private JsonArray objectKeyArray;

    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(context);
            progress.setCancelable(false);
            progress.setMessage(context.getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    PharmacyModel model;

    public PharmacyOrderDialogAdapter(final Context context, PharmacyModel model, ArrayList<FollowUpModel> followUpModelArrayList, PharmacyOrderDetailModel[] pharmacyOrderDetailModelArray, TextView tv_total_amount, TextView btn_paynow, final ImageView tv_check_uncheck, TextView btn_ordernow, final AlertDialog dialog) {
        this.context = context;
        this.model = model;
        this.prescriptionId = model.getPrescriptionId();
        this.tv_check_uncheck = tv_check_uncheck;
        this.dialog = dialog;
        this.btn_paynow = btn_paynow;
        this.btn_ordernow = btn_ordernow;
        this.userRoleId = model.getUserRoleId();
        this.hospitalId = model.getHospitalNo();
        this.prescriptionOrderId = model.getPresOrderId();
        if (hospitalId.equals("")) {
            hId = "0";
        } else {
            hId = hospitalId;
        }
        this.patContact = model.getMobile();
        this.mrno = model.getMrno();
        this.tv_total_amount = tv_total_amount;
        this.followUpModelArrayList = followUpModelArrayList;
        this.pharmacyOrderDetailModels = pharmacyOrderDetailModelArray;
        isChecked = new boolean[pharmacyOrderDetailModels.length];
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        jsonObjectHashMap = new LinkedHashMap<Integer, JsonObject>();
        mSharedPreference = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        objectKeyArray = new JsonArray();
        String subtanentImagePath = mSharedPreference.getString(Constants.SUB_TANENT_IMAGE_PATH, "default");
        String docProfilePic = mSharedPreference.getString(Constants.USER_PROFILE_PIC, "default");
        scheduleId = mSharedPreference.getInt(Constants.SCHEDULE_ID, 0);
        subTanentId = mSharedPreference.getInt(Constants.SUB_TANENT_ID_KEY, 0);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0);

        for(int i=0;i<pharmacyOrderDetailModels.length;i++){
            //this.pharmacyOrderDetailModels[i].setOrderedFollowupDurationno(this.pharmacyOrderDetailModels[i].getFollowupDurationno());
            //this.pharmacyOrderDetailModels[i].setOrderedFollowupId(this.pharmacyOrderDetailModels[i].getFollowupId());
            //this.pharmacyOrderDetailModels[i].setOrderedFollowupDurationUnit(this.pharmacyOrderDetailModels[i].getFollowupDurationUnit());
            this.pharmacyOrderDetailModels[i].setSelectedFollowUpId(this.pharmacyOrderDetailModels[i].getFollowupId()+"");
        }

        selectAll();
        this.btn_ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = ORDER_ONLY;
                postPharmacyOrderDetailsApi();
            }
        });
        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = ORDER_AND_PAY;
                System.out.println("jsonObjectHashMap===>" + jsonObjectHashMap.toString());
                postPharmacyOrderDetailsApi();
            }
        });
        tv_check_uncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllChecked) {
                    tv_check_uncheck.setImageResource(R.mipmap.all_uncheck);
                    unselectAll();
                    isAllChecked = false;
                } else {
                    tv_check_uncheck.setImageResource(R.mipmap.all_check);
                    selectAll();
                    isAllChecked = true;
                }
            }
        });

    }

    public void selectAll() {
        jsonObjectHashMap.clear();
        tv_check_uncheck.setImageResource(R.mipmap.all_check);
        PharmacyActivity.totalAmount = 0.0;
        for (int i = 0; i < isChecked.length; i++) {
            isChecked[i] = true;
        }
        for (int i = 0; i < pharmacyOrderDetailModels.length; i++) {
            ordDetailObj = new JsonObject();
            //totalAmount += pharmacyOrderDetailModels[i].getBillableAmount();
            ordDetailObj.addProperty("MedId", pharmacyOrderDetailModels[i].getDrug().getMedId());
            ordDetailObj.addProperty("orderedUnit", pharmacyOrderDetailModels[i].getBillableUnit());
            ordDetailObj.addProperty("orderedAmount", pharmacyOrderDetailModels[i].getBillableAmount());
            ordDetailObj.addProperty("followUpId", pharmacyOrderDetailModels[i].getSelectedFollowUpId());
            ordDetailObj.addProperty("regimeId", pharmacyOrderDetailModels[i].getRegimeId());
            jsonObjectHashMap.put(i, ordDetailObj);
        }
        Log.d("jsonObjectHashMap",jsonObjectHashMap.toString());
        //tv_total_amount.setText("₹ " + PharmacyActivity.totalAmount);
        notifyDataSetChanged();
    }

    public void unselectAll() {
        for (int i = 0; i < isChecked.length; i++) {
            isChecked[i] = false;
            jsonObjectHashMap.remove(i);
        }
        tv_total_amount.setText("₹ " + (Math.round(PharmacyActivity.totalAmount * 100.0) / 100.0));
        notifyDataSetChanged();
    }

    private void postPharmacyOrderDetailsApi() {
        JsonObject obj = new JsonObject();
        obj.addProperty("prescriptionId", prescriptionId);
        obj.addProperty("presOrderId", prescriptionOrderId);
        obj.addProperty("subTenantId", subTanentId);
        final JsonArray jsonArray = new JsonArray();

        Iterator myVeryOwnIterator = jsonObjectHashMap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            Integer key = (Integer) myVeryOwnIterator.next();
            JsonObject value = (JsonObject) jsonObjectHashMap.get(key);
            Log.d("key", value.get("orderedAmount") + "");
            jsonArray.add(value);
        }
        obj.add("ordDetail", jsonArray);
        System.out.println("jsonObject====>" + obj.toString());
        if (jsonArray.size() != 0) {
            showLoadingDialog();
            mCuraApplication.getInstance().mCuraEndPoint.postPharmacyOrderDetails(obj, new Callback<LabPharmacyPostResponseModel>() {
                @Override
                public void success(LabPharmacyPostResponseModel s, Response response) {
                    if (s.isStatus() == true) {
                        if (orderStatus == ORDER_ONLY) {
                            dialog.dismiss();
                            ((PharmacyInterface) context).reloadPharmacyDetail();
                            Toast.makeText(context, s.getMsg(), Toast.LENGTH_LONG).show();
                        } else if (orderStatus == ORDER_AND_PAY) {
                            Toast.makeText(context, s.getMsg(), Toast.LENGTH_LONG).show();
                            objectKeyArray.add(new JsonPrimitive(s.getId().toString()));
                            dialog.dismiss();
                            showPharmacyBillPayDialog();
                        }
                    } else if (s.isStatus() == false) {
                        Toast.makeText(context, s.getMsg(), Toast.LENGTH_LONG).show();
                    }
                    dismissLoadingDialog();
                }

                @Override
                public void failure(RetrofitError error) {
                    dismissLoadingDialog();
                }
            });
        } else {
            Toast.makeText(context, "Select any order", Toast.LENGTH_LONG).show();
        }
    }

    private void showPharmacyBillPayDialog() {
        final Dialog PaymentDialog = new Dialog(context);
        PaymentDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        PaymentDialog.getWindow().setDimAmount(0.5f);
        PaymentDialog.setCancelable(false);
        Window dialogWindow = PaymentDialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        PaymentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        PaymentDialog.setContentView(R.layout.pharmacy_payment_dialog);
        PaymentDialog.setCancelable(false);
        PaymentDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                // Prevent dialog close on back press button
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        final EditText bill_payment_activity_hIs_no = (EditText) PaymentDialog.findViewById(R.id.bill_payment_activity_hIs_no);
        final TextView bill_payable_amount = (TextView) PaymentDialog.findViewById(R.id.bill_payable_amount);
        bill_payable_amount.setText(tv_total_amount.getText() + "");
        //cbRefund = (CheckBox) dialog.findViewById(R.id.cb_refund);
        RadioGroup payment_mode = (RadioGroup) PaymentDialog.findViewById(R.id.payment_mode);
        TextView activity_bill_payment_paynow = (TextView) PaymentDialog.findViewById(R.id.activity_bill_payment_paynow);
        TextView tv_close_dialog = (TextView) PaymentDialog.findViewById(R.id.tv_close_dialog);
        TextView tv_consultant_name = (TextView) PaymentDialog.findViewById(R.id.tv_consultant_name);
        TextView tv_ord_id = (TextView) PaymentDialog.findViewById(R.id.tv_ord_id);
        TextView tv_pat_name = (TextView) PaymentDialog.findViewById(R.id.tv_pat_name);
        TextView tv_pat_dob_age = (TextView) PaymentDialog.findViewById(R.id.tv_pat_dob_age);
        TextView tv_pat_contact = (TextView) PaymentDialog.findViewById(R.id.tv_pat_contact);
        RadioButton online = PaymentDialog.findViewById(R.id.online);
        online.setVisibility(View.VISIBLE);


        tv_consultant_name.setText(model.getDoctorName().toString());
        tv_pat_name.setText(model.getPatname().toString());
        tv_pat_contact.setText(model.getMobile().toString());
        tv_ord_id.setText("Ord Id: " + model.getPresOrderId().toString());
        String patDob = model.getDob().toString();
        int patGender = model.getGenderId();
        String timestamp = patDob.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date();
        try {
            createdOn  = new Date(Long.parseLong(timestamp));
        }catch (NumberFormatException  numberFormatException){
        }         SimpleDateFormat sdf = new SimpleDateFormat("MM,dd,yyyy");
        String formattedDate = sdf.format(createdOn);
        System.out.println("formattedDate-->" + formattedDate);
        String[] out = formattedDate.split(",");

        int year = Integer.parseInt(out[2]);
        int month = Integer.parseInt(out[0]);
        int day = Integer.parseInt(out[1]);
        String patAgeGender;
        if (patGender == 1) {
            patAgeGender = Helper.getAge(year, month, day) + "/M";
        } else {
            patAgeGender = Helper.getAge(year, month, day) + "/F";
        }
        tv_pat_dob_age.setText(patAgeGender);


        tv_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentDialog.dismiss();
            }
        });
        /*cbRefund.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    appNatureId = 25;
                    paymentDescription = "Refund Amount";
                }else{
                    appNatureId = 0;
                    paymentDescription = "Pharmacy Amount";
                }
            }
        });*/
        activity_bill_payment_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(bill_payable_amount.getText().toString())) {
                    PaymentDialog.dismiss();
                    payableAmount = bill_payable_amount.getText().toString();
                    payableAmount = payableAmount.replace("₹ ", "");
                    appNatureId = 0;
                    paymentDescription = "Pharmacy Amount";
                        /*if(cbRefund.isChecked()){
                            appNatureId = 25;
                            paymentDescription = "Refund Amount";
                        }else{

                        }*/
                    if (paymentMode.equals("1")) {
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), model);
                    } else if (paymentMode.equals("2")) {
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), model);
                    } else if (paymentMode.equals("3")) {
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), model);
                    }
                    if(paymentMode.equals("4")){
                        callPaymentAPiforPharmacyKims(bill_payment_activity_hIs_no.getText().toString(), model);
                    }
                } else {
                    bill_payable_amount.setError("Enter Amount to be paid.");
                }
            }
        });
        payment_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cash:
                        paymentMode = "1";
                        break;
                    case R.id.card:
                        paymentMode = "2";
                        break;
                    case R.id.cheque:
                        paymentMode = "3";
                        break;
                    case R.id.online:
                        paymentMode = "4";
                        break;
                }
            }
        });
        PaymentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        PaymentDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.alpha = 1.0f; // Transparency
        dialogWindow.setAttributes(lp);
        PaymentDialog.show();
    }

    private void callPaymentAPiforPharmacyKims(String his, PharmacyModel pharmacyModels) {
        String hId;
        if (pharmacyModels.getHospitalNo().equals("")) {
            hId = "0";
        } else {
            hId = pharmacyModels.getHospitalNo();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", pharmacyModels.getOrderBySubTenantId());
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", pharmacyModels.getUserRoleId());
        obj.addProperty("Description", "Pharmacy");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", payableAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 5);
        obj.addProperty("Mrno", pharmacyModels.getMrno());
        obj.addProperty("HIS_BillNo", his);
        obj.addProperty("MobileNo", pharmacyModels.getMobile().toString().trim());
        //obj.addProperty("LabOrderID", 0);
        //obj.addProperty("PharmacyOrderID", pharmacyModels.getPresOrderId());
        obj.addProperty("ScheduleId", 0);
        obj.addProperty("AppId", 0);
        obj.addProperty("orderId", pharmacyModels.getPresOrderId());
        obj.add("OrdTxnIds", objectKeyArray);
        Log.d("postpaymentv3", obj.toString());
        if (paymentMode.equals("1")) {
            postPaymentForPharmacyCash(obj, pharmacyModels);
        }
        if (paymentMode.equals("2")) {
            postPaymentForPharmacyCard(obj, pharmacyModels);
        }
        if (paymentMode.equals("3")) {
            postPaymentForPharmacyCash(obj, pharmacyModels);
        }
      /*  if(paymentMode.equals("4")){
            new paymentFromRazorpay(context,obj);
        }*/
    }

    private void postPaymentForPharmacyCash(JsonObject obj, final PharmacyModel pharmacyModels) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentPharmacyFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {


                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(context, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, pharmacyModels);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(context, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, pharmacyModels);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }

                /*Toast.makeText(context, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                if (postPaymentModel.getMsg().equalsIgnoreCase("Payment successfull") || postPaymentModel.getMsg().equalsIgnoreCase("Payment is already done.")) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //((PharmacyInterface)context).reloadPharmacyDetail();
                    //getPharmacyPrescOrdersUpdateBillingDone(pharmacyModels);
                    //printBill(data, pharmacyModels);
                    showSuccessDialog(postPaymentModel.getMsg(),data,pharmacyModels);
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {

                dismissLoadingDialog();
            }
        });
    }

    private void postPaymentForPharmacyCard(JsonObject obj, final PharmacyModel pharmacyModels) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentPharmacyFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {

                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(context, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, pharmacyModels);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(context, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, pharmacyModels);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }

                /*Toast.makeText(context, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                if (postPaymentModel.getMsg().equals("Balance is less than Bill Amount. Please Add Amount")) {
                    showErrorDialog(postPaymentModel.getMsg());
                } else if (postPaymentModel.getMsg().equalsIgnoreCase("Payment Successfull") || postPaymentModel.getMsg().equalsIgnoreCase("Payment is already done.")) {
                    Toast.makeText(context, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    showSuccessDialog(postPaymentModel.getMsg(),data,pharmacyModels);
                    //printBill(data, pharmacyModels);
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showSuccessDialog(String msg, final String data[], final PharmacyModel pharmacyModels) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((PharmacyInterface) context).reloadPharmacyDetail();
                printBill(data, pharmacyModels);
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }
    /*private void postPaymentAPI_V1() {
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", subTanentId);
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", userRoleId);
        obj.addProperty("Description", "");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", totalAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 6);
        obj.addProperty("Mrno", mrno);
        obj.addProperty("HIS_BillNo", 0);
        obj.addProperty("MobileNo", patContact);
        obj.addProperty("LabOrderID", 0);
        obj.addProperty("PharmacyOrderID", prescriptionOrderId);
        obj.addProperty("ScheduleId", 0);
        obj.addProperty("AppId", 0);
        //obj.addProperty("OrderKey", orderKey);
        if (paymentMode.equals("card")) {
            postPaymentAPIForCard(obj);
        }
    }*/

    private void postPaymentAPIForCard(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPayment_v2(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {
                if (postPaymentModel.getMsg().equals("Balance is less than Bill Amount. Please Add Amount")) {
                    showErrorDialog(postPaymentModel.getMsg());
                } else {
                    Toast.makeText(context, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showErrorDialog(String msg) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }

    @Override
    public int getCount() {
        return pharmacyOrderDetailModels.length;
    }

    @Override
    public PharmacyOrderDetailModel getItem(int position) {
        return pharmacyOrderDetailModels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PharmacyOrderDetailModel pharmacyOrderDetailModel = pharmacyOrderDetailModels[position];
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.pharmacy_order_dialog_row_layout, null);
            holder = new ViewHolder();
            holder.ll_medicine_detail = (LinearLayout) convertView.findViewById(R.id.ll_medicine_detail);
            holder.cb_select_row = (CheckBox) convertView.findViewById(R.id.cb_select_row);
            holder.spinner_duration = (Spinner) convertView.findViewById(R.id.spinner_duration);
            holder.medicine_name = (TextView) convertView.findViewById(R.id.medicine_name);
            //holder.tv_prescribed_unit = (TextView) convertView.findViewById(R.id.tv_prescribed_unit);
            //holder.tv_minimum_unit = (TextView) convertView.findViewById(R.id.tv_minimum_unit);
            holder.tv_billabe_unit = (TextView) convertView.findViewById(R.id.tv_billabe_unit);
            holder.tv_billabe_amount = (TextView) convertView.findViewById(R.id.tv_billabe_amount);
            holder.tv_price_per_unit = (TextView) convertView.findViewById(R.id.tv_price_per_unit);
            holder.genric_name = (TextView) convertView.findViewById(R.id.genric_name);
            holder.tv_med_form = (TextView) convertView.findViewById(R.id.tv_med_form);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }            //viewHolder.text.setText(list.get(position).getName());
        /*if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#f6f6f6"));
        }*/
        holder.medicine_name.setText(pharmacyOrderDetailModel.getDrug().getBrandName().toString());
        holder.genric_name.setText(pharmacyOrderDetailModel.getDrug().getGenericName().toString());
        //holder.tv_prescribed_unit.setText(pharmacyOrderDetailModel.getPrescribedUnit().toString());
        //holder.tv_minimum_unit.setText(pharmacyOrderDetailModel.getDrug().getMinimumUnit().toString());
        String medicineForm = pharmacyOrderDetailModel.getDrug().getForm().toString();
        String medForm = "";
        if (!medicineForm.equals("")) {
            String medicineFormArray[] = medicineForm.split("/");

            for (int i = 0; i < medicineFormArray.length; i++) {
                medForm += medicineFormArray[i].substring(0, 3);
                if (i != medicineFormArray.length - 1) {
                    medForm += "/";
                }
            }
        }

        Log.d("medForm", medForm);
        holder.tv_med_form.setText("(" + medForm + ")");
        holder.tv_price_per_unit.setText("₹ " + pharmacyOrderDetailModel.getDrug().getUnitPrice() + "/unit");
        holder.ll_medicine_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMedicineDetailDialog(pharmacyOrderDetailModel);
            }
        });
        final ViewHolder finalHolder = holder;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonObjectHashMap.clear();
                totalAmount = 0.0;
                if (!finalHolder.cb_select_row.isChecked()) {
                    isChecked[position] = true;
                    //totalAmount += Integer.parseInt(finalHolder.tv_billabe_amount.getText().toString());
                    finalHolder.cb_select_row.setChecked(isChecked[position]);

                    for (int i = 0; i < isChecked.length; i++) {
                        if (isChecked[i]) {
                            tv_check_uncheck.setImageResource(R.mipmap.all_check);
                            isAllChecked = true;
                        } else {
                            tv_check_uncheck.setImageResource(R.mipmap.all_uncheck);
                            isAllChecked = false;
                            break;
                        }
                    }
                } else {
                    isChecked[position] = false;
                    //totalAmount -= Integer.parseInt(finalHolder.tv_billabe_amount.getText().toString());
                    finalHolder.cb_select_row.setChecked(isChecked[position]);
                    jsonObjectHashMap.remove(position);
                    for (int i = 0; i < isChecked.length; i++) {
                        if (isChecked[i]) {
                            tv_check_uncheck.setImageResource(R.mipmap.all_check);
                            isAllChecked = true;
                        } else {
                            tv_check_uncheck.setImageResource(R.mipmap.all_uncheck);
                            isAllChecked = false;
                            break;
                        }
                    }
                }
                for (int i = 0; i < pharmacyOrderDetailModels.length; i++) {
                    ordDetailObj = new JsonObject();
                    if (isChecked[i]) {
                        totalAmount += pharmacyOrderDetailModels[i].getBillableAmount();
                        ordDetailObj.addProperty("MedId", pharmacyOrderDetailModels[i].getDrug().getMedId());
                        ordDetailObj.addProperty("orderedUnit", pharmacyOrderDetailModels[i].getBillableUnit());
                        ordDetailObj.addProperty("orderedAmount", pharmacyOrderDetailModels[i].getBillableAmount());
                        ordDetailObj.addProperty("followUpId", pharmacyOrderDetailModels[i].getSelectedFollowUpId());
                        ordDetailObj.addProperty("regimeId", pharmacyOrderDetailModels[i].getRegimeId());
                        jsonObjectHashMap.put(i, ordDetailObj);
                    }
                }
                tv_total_amount.setText("₹ " + (Math.round(totalAmount * 100.0) / 100.0));
            }
        });
        holder.cb_select_row.setChecked(isChecked[position]);

        final FollowUpAdapter followUpAdapter = new FollowUpAdapter(context, followUpModelArrayList);
        holder.spinner_duration.setAdapter(followUpAdapter);
        holder.spinner_duration.setSelection(getIndex(pharmacyOrderDetailModel.getFollowupId()));
        if (myMap.containsKey(position)) {
            holder.spinner_duration.setSelection(myMap.get(position));
        }
        final ViewHolder finalHolder1 = holder;
        holder.spinner_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int selectedIndex, long id) {
                totalAmount = 0.0;
                myMap.put(position, selectedIndex);
                if (followUpAdapter.getItem(selectedIndex).getFollowupUnit().startsWith("Day")) {
                    selectedDays = Integer.parseInt(followUpAdapter.getItem(selectedIndex).getFollowupNumber());
                    Log.d("selectedDays", selectedDays + "");
                } else if (followUpAdapter.getItem(selectedIndex).getFollowupUnit().startsWith("Week")) {
                    selectedDays = Integer.parseInt(followUpAdapter.getItem(selectedIndex).getFollowupNumber()) * 7;
                    Log.d("selectedDays", selectedDays + "");
                } else if (followUpAdapter.getItem(selectedIndex).getFollowupUnit().startsWith("Month")) {
                    selectedDays = Integer.parseInt(followUpAdapter.getItem(selectedIndex).getFollowupNumber()) * 30;
                    Log.d("selectedDays", selectedDays + "");
                }
                pharmacyOrderDetailModel.setSelectedFollowUpId(followUpAdapter.getItem(selectedIndex).getFollowupId());
                if (pharmacyOrderDetailModel.getFollowupDurationUnit().startsWith("Day")) {
                    noOfDays = pharmacyOrderDetailModel.getFollowupDurationno();
                } else if (pharmacyOrderDetailModel.getFollowupDurationUnit().startsWith("Week")) {
                    noOfDays = pharmacyOrderDetailModel.getFollowupDurationno() * 7;
                } else if (pharmacyOrderDetailModel.getFollowupDurationUnit().startsWith("Month")) {
                    noOfDays = pharmacyOrderDetailModel.getFollowupDurationno() * 30;
                }
                Log.d("noOfDays", noOfDays + "");
                double prescribedUnit = (double) (pharmacyOrderDetailModel.getPrescribedPack() * pharmacyOrderDetailModel.getDrug().getUnitsPerMinPack());
                Log.d("prescribedUnit", prescribedUnit + "");
                int noOfUnit = getNoOfUnitForDays(selectedDays, noOfDays, prescribedUnit);
                /*if (noOfUnit < pharmacyOrderDetailModel.getDrug().getMinimumUnit()) {
                    noOfUnit = pharmacyOrderDetailModel.getDrug().getMinimumUnit();
                }*/
                int minBillableUnit;
                Log.d("noOfUnit", noOfUnit + "");
                if (pharmacyOrderDetailModel.getMinbillableUnit() == 0) {
                    minBillableUnit = 1;
                } else {
                    minBillableUnit = pharmacyOrderDetailModel.getMinbillableUnit();
                }
                Log.d("minBillableUnit", minBillableUnit + "");
                int extraPack = noOfUnit % minBillableUnit;
                Log.d("extraPack", extraPack + "");
                if (extraPack > 0) {
                    extraPack = 1;
                } else {
                    extraPack = 0;
                }
                Log.d("extraPack", extraPack + "");
                int noOfPacks = (noOfUnit / minBillableUnit) + extraPack;
                Log.d("noOfPacks", noOfPacks + "");
                int billableUnit = noOfPacks * pharmacyOrderDetailModel.getDrug().getUnitsPerMinPack();
                Log.d("billableUnit", billableUnit + "");
                double cost = pharmacyOrderDetailModel.getDrug().getCostOfMinPack() * noOfPacks;
                Log.d("cost", cost + "");
                pharmacyOrderDetailModels[position].setBillableAmount((Math.round(cost * 100.0) / 100.0));
                pharmacyOrderDetailModels[position].setBillableUnit(billableUnit);
                finalHolder1.tv_billabe_unit.setText(billableUnit + " ");
                finalHolder1.tv_billabe_amount.setText("₹ " + (Math.round(cost * 100.0) / 100.0) + "");
                for (int i = 0; i < pharmacyOrderDetailModels.length; i++) {
                    ordDetailObj = new JsonObject();
                    if (isChecked[i]) {
                        if (pharmacyOrderDetailModels[i].getBillableAmount() != 0.0) {
                            totalAmount += pharmacyOrderDetailModels[i].getBillableAmount();
                        }
                        ordDetailObj.addProperty("MedId", pharmacyOrderDetailModels[i].getDrug().getMedId());
                        ordDetailObj.addProperty("orderedUnit", pharmacyOrderDetailModels[i].getBillableUnit());
                        ordDetailObj.addProperty("orderedAmount", pharmacyOrderDetailModels[i].getBillableAmount());
                        ordDetailObj.addProperty("followUpId", pharmacyOrderDetailModels[i].getSelectedFollowUpId());
                        ordDetailObj.addProperty("regimeId", pharmacyOrderDetailModels[i].getRegimeId());
                        jsonObjectHashMap.put(i, ordDetailObj);
                    }
                }
                tv_total_amount.setText("₹ " + (Math.round(totalAmount * 100.0) / 100.0));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return convertView;
    }

    private void showMedicineDetailDialog(PharmacyOrderDetailModel pharmacyOrderDetailModel) {
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setDimAmount(0.5f);
        Window dialogWindow = dialog.getWindow();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.medicine_detail_dialog);
        TextView medicine_name = (TextView) dialog.findViewById(R.id.medicine_name);
        medicine_name.setText(pharmacyOrderDetailModel.getDrug().getBrandName().toString());
        TextView medicine_strength_form = (TextView) dialog.findViewById(R.id.medicine_strength_form);
        medicine_strength_form.setText(pharmacyOrderDetailModel.getDrug().getStrength().toString() + " | " + pharmacyOrderDetailModel.getDrug().getForm().toString());
        TextView genric_name = (TextView) dialog.findViewById(R.id.genric_name);
        genric_name.setText(pharmacyOrderDetailModel.getDrug().getGenericName().toString());
        ImageButton close_dialog = (ImageButton) dialog.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (width * 0.9);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1f; // Transparency
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private class ViewHolder {
        CheckBox cb_select_row;
        Spinner spinner_duration;
        LinearLayout ll_medicine_detail;
        TextView medicine_name, tv_med_form, tv_prescribed_unit, tv_minimum_unit, tv_billabe_unit, tv_billabe_amount, tv_price_per_unit, genric_name;
    }

    public int getIndex(int followUpId) {
        for (int i = 0; i < followUpModelArrayList.size(); i++) {
            FollowUpModel auction = followUpModelArrayList.get(i);
            if ((followUpId + "").equals(auction.getFollowupId())) {
                return i;
            }
        }
        return -1;
    }

    private int getNoOfUnitForDays(int selectedDays, int days, double prescribedUnit) {
        Log.d("prescribedUnit", prescribedUnit + "");
        Log.d("selectedDays", selectedDays + "");
        Log.d("days", days + "");
        int noOfUnit = (int) (((prescribedUnit * selectedDays) / days) + 0.9);
        Log.d("noOfUnit", noOfUnit + "");
        return noOfUnit;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) context
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = context.getString(R.string.app_name) +
                " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    public void printBill(String data[], PharmacyModel pharmacyModels) {
        Calendar now = Calendar.getInstance();
        completeDate = now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + 1 + "/" + now.get(Calendar.YEAR);
        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

        String printAmount[] = payableAmount.split("\\.");
        Log.d("amt",printAmount[0]);
        Log.d("amt",printAmount[1]);
        System.out.println("Amount in word--->" + Helper.convert(Integer.parseInt(printAmount[0])) + " and " + Helper.convert(Integer.parseInt(printAmount[1])) + "paise Only");
        if (pharmacyModels.getSubTenantId() == 500) {
            subTanantName = "BLK Hospital";
            subTanantAddress = "Pusa Road, Rajinder Nagar, New Delhi, Delhi 110005";
            subTanantContact = "Tel:+91-11-30403040";
        } else if (pharmacyModels.getSubTenantId() == 243) {
            subTanantName = "Sir Ganga Ram Hospital";
            subTanantAddress = "Rajinder Nagar, New Delhi-60";
            subTanantContact = "Tel:+91-11-25750000, 42254000, Fax:+91-1142251755";
        } else if (pharmacyModels.getSubTenantId() == 525) {
            subTanantName = "U K Nursing Home";
            subTanantAddress = "M-1, Main Road, Vikas Puri, Delhi, 110018";
            subTanantContact = "Tel:+91-11-40955555";
        } else if (pharmacyModels.getSubTenantId() == 528 || pharmacyModels.getSubTenantId() == 515 || pharmacyModels.getSubTenantId() == 547) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        } else if (pharmacyModels.getSubTenantId() == 529) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        }
        WebView webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(view);
                myWebView = null;
            }
        });
        String htmlDocument = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<title>Pay Slip</title>\n" +
                "<style type=\"text/css\">\n" +
                "*{ margin:0px; padding:0px;}\n" +
                ".payslipOuter{ margin:10px; padding:0px; width:700px; float:left; font-family:Arial, Helvetica, sans-serif; border:1px #ccc solid; }\n" +
                "table>tr>td{ border:none; outline:none; }\n" +
                "strong{ font-weight:normal;}\n" +
                ".invoiceTable > tbody > tr > th{ font-weight:normal;}\n" +
                ".payslipOuter h4{ font-weight:normal; font-size:15px; text-align:center; margin:3px 0px; font-weight:normal; }\n" +
                ".payslipOuter p{ text-align:center; font-size:13px; line-height:20px;}\n" +
                ".payslipOuter h3{ font-size:14px;  text-align:center; text-transform:uppercase; text-decoration:underline; line-height:22px; font-weight:normal;}\n" +
                ".receivedwith{ width:153px; font-size:14px; float:left; padding-top:12px; font-weight:bold}\n" +
                ".receivedwithValue{ width:100px; float:left;  }\n" +
                ".valueInput{ width:100%; border:1px dotted #aaa;  outline:none; border-top-color: #fff; border-left-color: #fff; border-right-color: #fff; height:20px; }\n" +
                "\n" +
                ".receivedwithValue, .sumrepeesValue, .towordsValue, .paymodeValue, .rupeesValue, .billnoValue, .dateValue, .patientnameValue, .cashcardValue, .hospitalIdValue, .rsinwordsValue, .amountwordsValue, .consultantValue{ float:left; margin-top:5px; }\n" +
                "\n" +
                ".receivedwith, .sumrepees, .towords, .paymode, .rupees, .signature, .billno, .date, .patientname, .cashcard, .hospitalId, .rsinwords, .amountWords{ font-size:14px; float:left; padding-top:12px; font-weight:normal; height:30px;}\n" +
                "\n" +
                "\n" +
                ".billno, .date, .patientname, .cashcard, .hospitalId, .consultant{ height:25px; font-size:13px; float:left; padding-top:12px; font-weight:normal; }\n" +
                "\n" +
                ".department{ text-align:center; width:100%; margin:0 auto; font-size:13px; font-weight:normal;}\n" +
                ".department .valueInput{ text-align:center;  font-weight:normal; border-bottom:0px; margin-top:5px;}\n" +
                "\n" +
                ".rupesOuter{ float:left; width:160px;}\n" +
                ".signatureOuter{ float:right; width:310px; text-align:center;} \n" +
                "\n" +
                ".signature{ width:155px; float:right}\n" +
                ".signatureValue{ width:155px; float:left;}\n" +
                "\n" +
                ".rsinwords{ width:1px;}\n" +
                ".sumrepees{ width:70px;}\n" +
                ".sumrepeesValue{ width:183px}\n" +
                ".towords{ width:72px;}\n" +
                ".towordsValue{ width:298px}\n" +
                ".paymode{ width:25px;}\n" +
                ".paymodeValue{width:125px;}\n" +
                ".rupees{ width:28px;}\n" +
                ".rupeesValue{width:120px;}\n" +
                ".rsinwordsValue{ width:535px;}\n" +
                ".consultant{ width:100%;}\n" +
                ".consultantValue{width:100%;}\n" +
                "\n" +
                "\n" +
                ".amountWords{ width:130px;}\n" +
                ".amountwordsValue{ width:546px;}\n" +
                "\n" +
                "\n" +
                ".billnoValue{ width:80px;}\n" +
                ".dateValue{ width:150px;} \n" +
                ".patientnameValue{ width:200px;}\n" +
                ".cashcardValue{ width:98px;}\n" +
                ".hospitalIdValue{ width:105px;}\n" +
                "\n" +
                ".billno{ width:50px;}\n" +
                ".date{ width:45px;}\n" +
                ".patientname{ width:95px;} \n" +
                ".cashcard{ width:77px}\n" +
                ".hospitalId{ width:75px;}\n" +
                "\n" +
                "\n" +
                "\n" +
                ".receivedwithOuter{ width:260px; float:left;}\n" +
                ".sumrepeesOuter{ width:253px; float:right;}\n" +
                ".paymodeOuter{ width:160px; float:left;}\n" +
                ".towordsOuter{ width:370px; float:right;}\n" +
                ".amoutnwordsOuter{ width:100%;}\n" +
                ".consultantOuter{ width:300px; margin:0px auto;}\n" +
                ".departmentOuter{ width:200px; margin:5px auto;}\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                ".billnoOuter{ width:200px; float:left;}\n" +
                ".dateOuter{ width:200px; float:right;}\n" +
                ".patientnameOuter{ width:310px; float:left;}\n" +
                ".cashcardOuter{ width:175px; float:right;}\n" +
                ".hospitalIdOuter{ width:200px; float:left;}\n" +
                "\n" +
                ".headpart{ background-color:#f2f2f2;}\n" +
                "\n" +
                ".borderLine{ width:100%; margin-top:15px; border-bottom:1px #333 dashed;}\n" +
                ".bordervLine{ border-left:1px #333 solid; height:100%; width:1px; height:250px;}\n" +
                "\n" +
                ".invoiceTable{ width:100%; font-size:14px;}\n" +
                ".invoiceTable > tbody > tr > td{ padding:7px 10px; text-align:left; }\n" +
                ".invoiceTable > tbody > tr > th{ padding:10px 10px; text-align:left; }\n" +
                "\n" +
                ".consultantValue .valueInput{ border-bottom:none; font-size:20px; text-align:center;}\n" +
                "</style>\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"payslipOuter\">\n" +
                "<table width=\"100%\" style=\"padding:5px;\">\n" +
                "\t<tr>\n" +
                "\t\t<td colspan=\"2\"><div class=\"consultantOuter\">\n" +
                "        <div class=\"consultantValue\"><input type=\"text\" class=\"valueInput\" value=\"" + pharmacyModels.getDoctorName() + "\"></div></div>\n" +
                "        \n" +
                "        <div class=\"departmentOuter\">\n" +
                "        <div class=\"department\"><input type=\"text\" class=\"valueInput\" value=\"" + data[2] + "\"></div></div>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "  <tr>\n" +
                "    <td colspan=\"2\"><h4>" + subTanantName + "</h4>\n" +
                "\t<p> " + subTanantAddress + "</br>\n" +
                "    " + subTanantContact + "</br></p>\n" +
                "  <h3 style=\"margin-bottom:20px;\"> Pharmacy Receipt</h3>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    \n" +
                " <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"billnoOuter\"><div class=\"billno\">Bill No.</div>\n" +
                "   \t    <div class=\"billnoValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[1] + "\"></div></div> <div class=\"dateOuter\"><div class=\"date\">Date</div> <div class=\"dateValue\"><input type=\"text\" class=\"valueInput\" value=\"" + completeDate + " | " + time + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "      <tr>\n" +
                "    \t<td colspan=\"2\"><div class=\"patientnameOuter\"><div class=\"patientname\">Patient Name</div>\n" +
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + pharmacyModels.getPatname() + "\"></div></div> \n" +
                "        \n" +
                /*"        <div class=\"hospitalIdOuter\"><div class=\"hospitalId\">Hospital ID</div>\n" +
                "        <div class=\"hospitalIdValue\"><input type=\"text\" class=\"valueInput\" value=\"" + tvHospitalId.getText().toString() + "\"></div></div>\n" +*/
                "        \n" +
                "        <div class=\"cashcardOuter\"><div class=\"cashcard\">E-Wallet ID</div> <div class=\"cashcardValue\"><input type=\"text\" class=\"valueInput\" value = \"" + data[0] + "\"></div></div></td>\n" +
                "    </tr>\n" +
                "   \n" +
                "</table>\n" +
                "<div style=\"border-top:1px #333 solid; height:1px;\"></div>\n" +
                "<table width=\"100%\" class=\"invoiceTable\">    \n" +
                "  <tr style=\"border-bottom:1px #333 solid\">\n" +
                "  \t<th>Description</th>\n" +
                "    <th>&nbsp;</th>\n" +
                "    <th style=\"width:120px;\">Amount</th>\n" +
                "  </tr>\n" +
                " </table>\n" +
                " <div style=\"border-top:1px #333 solid; height:1px;\"></div>\n" +
                " <table width=\"100%\" class=\"invoiceTable\">\n" +
                "  <tr>\n" +
                "  \t<td>" + paymentDescription + "</td>\n" +
                "     <td rowspan=\"3\" style=\"width:1px; border-left:1px #333 solid; padding:7px 0px;\">&nbsp;</td>\n" +
                "    <td style=\"width:120px;\">Rs. " + payableAmount + "</td>\n" +
                "  </tr>\n" +
                "\n" +
                "\n" +
                "    <tr>\n" +
                "    <td>&nbsp; </td>\n" +
                "    <td>&nbsp;</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top:1px #333 solid; text-align:right; padding:7px 10px;\"><strong>Total Amount</strong></td>\n" +
                "    <td style=\"border-top:1px #333 solid; padding:7px 10px;\"><strong>Rs. " + payableAmount + "</strong></td>\n" +
                "  </tr>\n" +
                "   \n" +
                "    <tr>\n" +
                "    <td colspan=\"3\"><div class=\"amoutnwordsOuter\">\n" +
                "    <div class=\"amountWords\">Amount in words:</div>\n" +
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + Helper.convert(Integer.parseInt(printAmount[0])) + " and " + Helper.convert(Integer.parseInt(printAmount[1])) + " Paise Only\"></div>\n" +
                "     </div></td>\n" +
                "    </tr>\n" +
                "    \n" +
                "     \n" +
                "  </table>\n" +
                "  \n" +
                " <!-- <div style=\"border-top:1px #333 solid; height:1px;\"></div>-->\n" +
                "  \n" +
                "  <table width=\"100%\" style=\"padding:5px;\">\n" +
                "     <tr>     \n" +
                "    <td><div class=\"signatureOuter\">\n" +
                "      <div class=\"signature\">Authorized Signatory</br>\n" +
                "      </div>\n" +
                "     <!-- <div class=\"signatureValue\"><input type=\"text\" class=\"valueInput\"></div>--></div></td>\n" +
                "    </tr>\n" +
                "   \n" +
                "\n" +
                "  \n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
        Log.d("htmlDocument", htmlDocument);
        webView.loadDataWithBaseURL(null, htmlDocument,
                "text/html", "UTF-8", null);

        myWebView = webView;
    }
}
