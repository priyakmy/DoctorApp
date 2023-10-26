package com.mcura.mcurapharmacy.adpater;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mcura.mcurapharmacy.Helper.Helper;
import com.mcura.mcurapharmacy.Interface.LabInterface;
import com.mcura.mcurapharmacy.Interface.PharmacyInterface;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.activity.LabOrderActivity;
import com.mcura.mcurapharmacy.activity.PharmacyActivity;
import com.mcura.mcurapharmacy.model.LabDatum;
import com.mcura.mcurapharmacy.model.LabOrderDetailModel;
import com.mcura.mcurapharmacy.model.LabPharmacyPostResponseModel;
import com.mcura.mcurapharmacy.model.PostPaymentModel;
import com.mcura.mcurapharmacy.view.CustomExpListView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mCURA1 on 7/17/2017.
 */

public class LabOrderDetailExpandableAdapter extends BaseExpandableListAdapter {

    private final SharedPreferences mSharedPreference;
    private Context mContext;
    private LabDatum labDatum;
    private ArrayList<LabOrderDetailModel> labOrderDetailModels;
    private boolean isAllChecked;
    private SecondLevelAdapter secondLevelAdapter;
    private CheckBox cb_select_row_level_first;
    private MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    TextView tv_total_amount;
    double totalAmount = 0.0;
    AlertDialog mainLabDialog;
    private String paymentMode = "1";
    private String payableAmount;
    private int appNatureId;
    private String paymentDescription;
    private int frontOfficeUserRoleId;
    private JsonArray objectKeyArray;
    private WebView myWebView;
    private String subTanantName;
    private String subTanantAddress;
    private String subTanantContact;
    private String completeDate;
    private String time;
    private int orderStatus;
    private static final int ORDER_ONLY = 1;
    private static final int ORDER_AND_PAY = 2;

    public LabOrderDetailExpandableAdapter(Context context, final LabDatum labDatum, final ArrayList<LabOrderDetailModel> labOrderDetailModel, TextView btn_paynow, TextView tv_total_amount, AlertDialog mainLabDialog,TextView btn_ordernow) {
        this.mContext = context;
        this.labDatum = labDatum;
        this.mainLabDialog = mainLabDialog;
        this.labOrderDetailModels = labOrderDetailModel;
        this.tv_total_amount = tv_total_amount;
        objectKeyArray = new JsonArray();
        mSharedPreference = context.getSharedPreferences(context.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        frontOfficeUserRoleId = mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0);
        btn_ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = ORDER_ONLY;
                postLabOrderDetailsApi();
            }
        });
        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = ORDER_AND_PAY;
                postLabOrderDetailsApi();
            }

        });
    }
    private void postLabOrderDetailsApi(){
        JsonObject obj = new JsonObject();
        obj.addProperty("labOrderId", labDatum.getLabOrderId());
        obj.addProperty("subTenantId", labDatum.getSub_tenant_id());
        JsonArray jsonArray = new JsonArray();
        JsonObject ordDetailObj = null;
        for (int i = 0; i < labOrderDetailModels.size(); i++) {
            if (labOrderDetailModels.get(i).getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
                if (labOrderDetailModels.get(i).isSelected()) {
                    ordDetailObj = new JsonObject();
                    ordDetailObj.addProperty("packageId", 0);
                    ordDetailObj.addProperty("grpId", 0);
                    ordDetailObj.addProperty("testId", labOrderDetailModels.get(i).getLabTestId());
                    ordDetailObj.addProperty("cost", labOrderDetailModels.get(i).getLabTestCost());
                    jsonArray.add(ordDetailObj);
                }
            } else if (labOrderDetailModels.get(i).getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
                if (labOrderDetailModels.get(i).isSelected()) {
                    ordDetailObj = new JsonObject();
                    ordDetailObj.addProperty("packageId", 0);
                    ordDetailObj.addProperty("grpId", labOrderDetailModels.get(i).getLabTestId());
                    ordDetailObj.addProperty("testId", 0);
                    ordDetailObj.addProperty("cost", labOrderDetailModels.get(i).getLabTestCost());
                    jsonArray.add(ordDetailObj);
                }
            } else if (labOrderDetailModels.get(i).getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage) {
                if (labOrderDetailModels.get(i).isSelected()) {
                    ordDetailObj = new JsonObject();
                    ordDetailObj.addProperty("packageId", labOrderDetailModels.get(i).getLabTestId());
                    ordDetailObj.addProperty("grpId", 0);
                    ordDetailObj.addProperty("testId", 0);
                    ordDetailObj.addProperty("cost", labOrderDetailModels.get(i).getLabTestCost());
                    jsonArray.add(ordDetailObj);
                }
            }
        }
        obj.add("labordDetail", jsonArray);
        Log.d("labordDetail", obj.toString());
        if (jsonArray.size() != 0) {
            postLabData(obj);
        } else {
            Toast.makeText(mContext, "Please choose test.", Toast.LENGTH_LONG).show();
        }

    }
    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(mContext);
            progress.setCancelable(false);
            progress.setMessage(mContext.getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    private void postLabData(JsonObject obj) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.postLabOrderdetails_v2(obj, new Callback<LabPharmacyPostResponseModel>() {
            @Override
            public void success(LabPharmacyPostResponseModel labPharmacyPostResponseModel, Response response) {
                if (labPharmacyPostResponseModel.isStatus() == true) {
                    if (orderStatus == ORDER_ONLY) {
                        Toast.makeText(mContext, labPharmacyPostResponseModel.getMsg(), Toast.LENGTH_LONG).show();
                        mainLabDialog.dismiss();
                        ((LabInterface) mContext).reloadLabDetail();
                    }else if(orderStatus == ORDER_AND_PAY){
                        mainLabDialog.dismiss();
                        ((LabInterface) mContext).reloadLabDetail();
                        objectKeyArray.add(new JsonPrimitive(labPharmacyPostResponseModel.getId().toString()));
                        showLabBillPayDialog();
                        Toast.makeText(mContext, labPharmacyPostResponseModel.getMsg(), Toast.LENGTH_LONG).show();
                    }


                   /* mainLabDialog.dismiss();
                    ((LabInterface) mContext).reloadLabDetail();
                    objectKeyArray.add(new JsonPrimitive(labPharmacyPostResponseModel.getId().toString()));
                    showLabBillPayDialog();
                    Toast.makeText(mContext, labPharmacyPostResponseModel.getMsg(), Toast.LENGTH_LONG).show();*/
                } else if (labPharmacyPostResponseModel.isStatus() == false) {
                    Toast.makeText(mContext, labPharmacyPostResponseModel.getMsg(), Toast.LENGTH_LONG).show();
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showLabBillPayDialog() {
        final Dialog PaymentDialog = new Dialog(mContext);
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

        tv_consultant_name.setText(labDatum.getDoctorName().toString());
        tv_pat_name.setText(labDatum.getPatname().toString());
        tv_pat_contact.setText(labDatum.getMobile().toString());
        tv_ord_id.setText("Ord Id: " + labDatum.getLabOrderId().toString());
        String patDob = labDatum.getDob().toString();
        int patGender = labDatum.getGenderId();
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

        activity_bill_payment_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(bill_payable_amount.getText().toString())) {
                    PaymentDialog.dismiss();
                    payableAmount = bill_payable_amount.getText().toString();
                    payableAmount = payableAmount.replace("₹ ", "");
                    appNatureId = 0;
                    paymentDescription = "Lab Amount";

                    if (paymentMode.equals("1")) {
                        callPaymentAPiforLabKims(bill_payment_activity_hIs_no.getText().toString(), labDatum);
                    } else if (paymentMode.equals("2")) {
                        callPaymentAPiforLabKims(bill_payment_activity_hIs_no.getText().toString(), labDatum);
                    } else if (paymentMode.equals("3")) {
                        callPaymentAPiforLabKims(bill_payment_activity_hIs_no.getText().toString(), labDatum);
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

    private void callPaymentAPiforLabKims(String his, LabDatum labDatum) {
        String hId;
        if (labDatum.getHospitalNo().equals("")) {
            hId = "0";
        } else {
            hId = labDatum.getHospitalNo();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("SubtenantId", labDatum.getOrderBySubTenantId());
        obj.addProperty("HospitalNo", hId);
        obj.addProperty("AppnatureId", 0);
        obj.addProperty("UserRoleId", labDatum.getUserRoleId());
        obj.addProperty("Description", "Lab");
        obj.addProperty("PaymentMode", paymentMode);
        obj.addProperty("BillAmount", payableAmount);
        obj.addProperty("CollectedBy", frontOfficeUserRoleId);
        obj.addProperty("ServiceType", 4);
        obj.addProperty("Mrno", labDatum.getMrno());
        obj.addProperty("HIS_BillNo", his);
        obj.addProperty("MobileNo", labDatum.getMobile().toString().trim());
        //obj.addProperty("LabOrderID", 0);
        //obj.addProperty("PharmacyOrderID", pharmacyModels.getPresOrderId());
        obj.addProperty("ScheduleId", 0);
        obj.addProperty("AppId", 0);
        obj.addProperty("orderId", labDatum.getLabOrderId());
        obj.add("OrdTxnIds",objectKeyArray);
        Log.d("postpaymentv3",obj.toString());
        if (paymentMode.equals("1")) {
            postPaymentForLabCash(obj, labDatum);
        }
        if (paymentMode.equals("2")) {
            postPaymentForLabCard(obj, labDatum);
        }
        if (paymentMode.equals("3")) {
            postPaymentForLabCash(obj, labDatum);
        }
    }

    private void postPaymentForLabCard(JsonObject obj, final LabDatum labDatum) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentLabFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {

                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(mContext, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, labDatum);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(mContext, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, labDatum);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }

                /*Toast.makeText(mContext, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                if (postPaymentModel.getMsg().equalsIgnoreCase("Balance is less than Bill Amount. Please Add Amount")) {
                    showErrorDialog(postPaymentModel.getMsg());
                } else if (postPaymentModel.getMsg().equalsIgnoreCase("Payment Successfull") || postPaymentModel.getMsg().equalsIgnoreCase("Payment is already done.")) {
                    Toast.makeText(mContext, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    showSuccessDialog(postPaymentModel.getMsg(),data,labDatum);
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }
    private void showSuccessDialog(String msg, final String data[], final LabDatum labDatum) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((LabInterface)mContext).reloadLabDetail();
                printBill(data, labDatum);
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }
    private void showErrorDialog(String msg) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.app.AlertDialog dialog = builder.show();
    }

    private void postPaymentForLabCash(JsonObject obj, final LabDatum labDatum) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.PostPaymentLabFee(obj, new Callback<PostPaymentModel>() {
            @Override
            public void success(PostPaymentModel postPaymentModel, Response response) {

                if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentSuccessfull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentDone.getStatusId()) {
                    Toast.makeText(mContext, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, labDatum);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentALreadyDone.getStatusId()) {
                    Toast.makeText(mContext, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //reloadLabDetail();
                    //printBill(data, group);
                    showSuccessDialog(postPaymentModel.getMsg(), data, labDatum);
                } else if (postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentNotDone.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mErrorInPayment.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mPaymentModeNotCorrect.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mBlankHospitalNo.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mOrderTransactionIdNull.getStatusId() ||
                        postPaymentModel.getStatusId() == EnumType.PaymentStatusId.mFillCashCard.getStatusId()) {
                    showErrorDialog(postPaymentModel.getMsg());
                }


                /*Toast.makeText(mContext, postPaymentModel.getMsg(), Toast.LENGTH_LONG).show();
                if (postPaymentModel.getMsg().equalsIgnoreCase("Payment successfull") || postPaymentModel.getMsg().equalsIgnoreCase("Payment is already done.")) {
                    String id = postPaymentModel.getID();
                    String data[] = id.split("-");
                    //((PharmacyInterface)mContext).reloadPharmacyDetail();
                    //getPharmacyPrescOrdersUpdateBillingDone(pharmacyModels);
                    showSuccessDialog(postPaymentModel.getMsg(),data,labDatum);
                    //printBill(data, labDatum);
                }*/
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {

                dismissLoadingDialog();
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) mContext
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = mContext.getString(R.string.app_name) +
                " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }

    private void printBill(String[] data, LabDatum labDatum) {
        String paymentAmount[] = payableAmount.split(".");
        Calendar now = Calendar.getInstance();
        completeDate = now.get(Calendar.DATE) + "/" + now.get(Calendar.MONTH) + 1 + "/" + now.get(Calendar.YEAR);
        time = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

        if (labDatum.getSub_tenant_id() == 500) {
            subTanantName = "BLK Hospital";
            subTanantAddress = "Pusa Road, Rajinder Nagar, New Delhi, Delhi 110005";
            subTanantContact = "Tel:+91-11-30403040";
        } else if (labDatum.getSub_tenant_id() == 243) {
            subTanantName = "Sir Ganga Ram Hospital";
            subTanantAddress = "Rajinder Nagar, New Delhi-60";
            subTanantContact = "Tel:+91-11-25750000, 42254000, Fax:+91-1142251755";
        } else if (labDatum.getSub_tenant_id() == 525) {
            subTanantName = "U K Nursing Home";
            subTanantAddress = "M-1, Main Road, Vikas Puri, Delhi, 110018";
            subTanantContact = "Tel:+91-11-40955555";
        } else if (labDatum.getSub_tenant_id() == 528 || labDatum.getSub_tenant_id() == 515 || labDatum.getSub_tenant_id() == 547) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        } else if (labDatum.getSub_tenant_id() == 529) {
            subTanantName = "KIMS Hospitals";
            subTanantAddress = "1-112 / 86, Survey No 55/ EE, Kondapur Village, Serilingampally Mandal, Hyderabad, Telangana 500084";
            subTanantContact = "Tel:+91-88973 14141";
        }
        WebView webView = new WebView(mContext);
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
                "        <div class=\"consultantValue\"><input type=\"text\" class=\"valueInput\" value=\"" + labDatum.getDoctorName() + "\"></div></div>\n" +
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
                "   \t    <div class=\"patientnameValue\"><input type=\"text\" class=\"valueInput\" value=\"" + labDatum.getPatname() + "\"></div></div> \n" +
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
                "    <div class=\"amountwordsValue\"><input type=\"text\" class=\"valueInput\" value = \"" + Helper.convert((int)Double.parseDouble(payableAmount)) + " Only\"></div>\n" +
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

    public void selectAll(LabOrderDetailModel headerTitle) {
        headerTitle.setSelected(true);
        for (int i = 0; i < headerTitle.getChildren().size(); i++) {
            headerTitle.getChildren().get(i).setSelected(true);
        }
        notifyDataSetChanged();

    }

    public void unselectAll(LabOrderDetailModel headerTitle) {
        headerTitle.setSelected(false);
        for (int i = 0; i < headerTitle.getChildren().size(); i++) {
            headerTitle.getChildren().get(i).setSelected(false);
        }
        notifyDataSetChanged();
    }


    // This Function used to inflate parent rows view
    public void OnIndicatorClick(boolean isExpanded, int position) {

    }

    public void OnTextClick() {

    }

    @Override
    public int getGroupCount() {
        return labOrderDetailModels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.d("getChildrenCount", labOrderDetailModels.get(groupPosition).getChildren().size() + "");
        return 1;
    }

    @Override
    public LabOrderDetailModel getGroup(int groupPosition) {
        return labOrderDetailModels.get(groupPosition);
    }

    @Override
    public LabOrderDetailModel getChild(int groupPosition, int childPosition) {
        return labOrderDetailModels.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
        final LabOrderDetailModel headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_group, parent, false);
        }
        cb_select_row_level_first = (CheckBox) convertView.findViewById(R.id.cb_select_row_level_first);
        ImageButton indicator = (ImageButton) convertView.findViewById(R.id.indicator);
        int imageResourceId = isExpanded ? R.mipmap.minus
                : R.mipmap.plus;

        if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage || headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
            indicator.setVisibility(View.VISIBLE);
            indicator.setImageResource(imageResourceId);
        } else if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
            indicator.setVisibility(View.GONE);
        }

        indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnIndicatorClick(isExpanded, groupPosition);
            }
        });
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        TextView tv_test_cost = (TextView) convertView.findViewById(R.id.tv_test_cost);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getLabTestName());
        tv_test_cost.setText(headerTitle.getLabTestCost().toString());

        cb_select_row_level_first.setChecked(headerTitle.isSelected());
        cb_select_row_level_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cb_click", groupPosition + "");
                if (!headerTitle.isSelected()) {
                    selectAll(headerTitle);
                } else {
                    unselectAll(headerTitle);
                }
                totalAmount = 0.0;
                for (int i = 0; i < labOrderDetailModels.size(); i++) {
                    if (labOrderDetailModels.get(i).isSelected() == true) {
                        totalAmount += Double.parseDouble(labOrderDetailModels.get(i).getLabTestCost());
                    }
                }
                tv_total_amount.setText(totalAmount + "");
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d("status", "true");
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.mContext);
        ArrayList<LabOrderDetailModel> parentNode = labOrderDetailModels.get(groupPosition).getChildren();
        secondLevelAdapter = new SecondLevelAdapter(this.mContext, parentNode, cb_select_row_level_first, groupPosition) {
            @Override
            public void OnIndicatorClick(boolean isExpanded, int position) {
                if (isExpanded) {
                    secondLevelExpListView.collapseGroup(position);
                } else {
                    secondLevelExpListView.expandGroup(position);
                }
            }

            @Override
            public void OnTextClick() {
                //Do whatever you want to do on text click
            }
        };
        secondLevelExpListView.setAdapter(secondLevelAdapter);
        secondLevelExpListView.setGroupIndicator(null);
//        secondLevelExpListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int previousGroup = -1;
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (groupPosition != previousGroup)
//                    secondLevelExpListView.collapseGroup(previousGroup);
//                previousGroup = groupPosition;
//            }
//        });
        /*secondLevelExpListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(mContext, secondLevelAdapter.getGroup(groupPosition).getLabTestName() + "", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
        return secondLevelExpListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class SecondLevelAdapter extends BaseExpandableListAdapter {
        private final Context context;
        ArrayList<LabOrderDetailModel> parentNode;
        public int gp;
        public CheckBox cb_select_row_level_first;

        public SecondLevelAdapter(Context context, ArrayList<LabOrderDetailModel> parentNode, CheckBox cb_select_row_level_first, int gp) {

            this.context = context;
            this.parentNode = parentNode;
            this.gp = gp;
            this.cb_select_row_level_first = cb_select_row_level_first;
        }

        @Override
        public LabOrderDetailModel getChild(int groupPosition, int childPosition) {
            return parentNode.get(groupPosition).getChildren().get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            LabOrderDetailModel childText = getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.drawer_list_item, parent, false);
            }
            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);
            txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            txtListChild.setText(childText.getLabTestName());
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            try {
                return parentNode.get(groupPosition).getChildren().size();
            } catch (Exception e) {
                return 0;
            }
        }

        @Override
        public LabOrderDetailModel getGroup(int groupPosition) {
            return parentNode.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return parentNode.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        // This Function used to inflate parent rows view
        public void OnIndicatorClick(boolean isExpanded, int position) {

        }

        public void OnTextClick() {

        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            final LabOrderDetailModel headerTitle = getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.drawer_list_group_second, parent, false);
            }
            CheckBox cb_select_row_level_second = (CheckBox) convertView.findViewById(R.id.cb_select_row_level_second);
            ImageButton indicator = (ImageButton) convertView.findViewById(R.id.indicator);
            int imageResourceId = isExpanded ? R.mipmap.minus
                    : R.mipmap.plus;

            if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNaturePackage || headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureGroup) {
                indicator.setVisibility(View.VISIBLE);
                indicator.setImageResource(imageResourceId);
            } else if (headerTitle.getLabTestNature() == EnumType.LabObjNature.kLabObjNatureTest) {
                indicator.setVisibility(View.GONE);
            }

            indicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnIndicatorClick(isExpanded, groupPosition);
                }
            });
            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            TextView tv_test_cost_second = (TextView) convertView.findViewById(R.id.tv_test_cost_second);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle.getLabTestName());
            tv_test_cost_second.setText(headerTitle.getLabTestCost().toString());
            cb_select_row_level_second.setChecked(headerTitle.isSelected());
            cb_select_row_level_second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (headerTitle.isSelected() == true) {
                        headerTitle.setSelected(false);
                        LabOrderDetailExpandableAdapter.this.labOrderDetailModels.get(gp).setSelected(false);
                    } else {
                        headerTitle.setSelected(true);
                        for (int i = 0; i < parentNode.size(); i++) {
                            if (parentNode.get(i).isSelected() == false) {
                                break;
                            } else {
                                if ((parentNode.size() - 1) == i) {
                                    LabOrderDetailExpandableAdapter.this.labOrderDetailModels.get(gp).setSelected(true);
                                }
                            }
                        }
                    }
                    LabOrderDetailExpandableAdapter.this.notifyDataSetChanged();
                }
            });

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headerTitle.isSelected()) {
                    headerTitle.setSelected(false);
                } else {
                    headerTitle.setSelected(true);
                }
            }
        });*/

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
