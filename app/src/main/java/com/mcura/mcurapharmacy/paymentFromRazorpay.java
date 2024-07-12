package com.mcura.mcurapharmacy;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.JsonObject;
import com.mcura.mcurapharmacy.activity.LabOrderActivity;
import com.mcura.mcurapharmacy.activity.PharmacyActivity;
import com.mcura.mcurapharmacy.adpater.Helper;
import com.mcura.mcurapharmacy.model.CreatePaymentOrderResponseModel;
import com.mcura.mcurapharmacy.model.Datum;
import com.razorpay.Checkout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class paymentFromRazorpay {
    Context mContext;
    JsonObject obj;
    private AlertDialog paymentErrordialog;
    private Datum selectedPatientData;
    private String appNature = "";


    public paymentFromRazorpay(Context context, JsonObject obj) {
        this.obj = obj;
        this.mContext = context;
        createPaymentOrder(obj);
    }



    public void createPaymentOrder(JsonObject obj) {
        if (!Helper.isInternetConnected(mContext)) {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        final AlertDialog loadingDialog = AlertUtil.setProgressDialog(mContext, "Loading...");
        loadingDialog.show();


        Call<CreatePaymentOrderResponseModel> callCreatePaymentOrderResponseModel = MCuraApplication.getInstance().mCuraConsumerEndPoint.createPaymentOrder(obj);
        callCreatePaymentOrderResponseModel.enqueue(new Callback<CreatePaymentOrderResponseModel>() {
            @Override
            public void onResponse(Call<CreatePaymentOrderResponseModel> call, Response<CreatePaymentOrderResponseModel> response) {
                CreatePaymentOrderResponseModel createPaymentOrderResponse = response.body();
                if (createPaymentOrderResponse.getStatus() == 1) {
                    startPayment(createPaymentOrderResponse);
                } else {
                    showErrorDialog("Something went wrong while initializing payment,please try again");
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CreatePaymentOrderResponseModel> call, Throwable t) {
                loadingDialog.dismiss();
            }
        });
    }

    private void showErrorDialog(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                paymentErrordialog.dismiss();
            }
        });
        paymentErrordialog = builder.show();
    }
    public void startPayment(CreatePaymentOrderResponseModel createPaymentOrderResponseModel) {
        final Activity activity ;
        if(obj.get("Description").toString().equals("Pharmacy")) {
            activity = (PharmacyActivity) mContext;

            Checkout checkout = new Checkout();
            //checkout.setKeyID();
            checkout.setImage(R.drawable.logo);
            try {
                JSONObject options = new JSONObject();
                //  implementation 'com.razorpay:checkout:1.5.12'
                options.put("name", "mCURA Mobile Health");
                options.put("description", appNature);
                options.put("image", "https://www.mcura.com/images/logo.png");
                options.put("order_id", createPaymentOrderResponseModel.getData().getAttributes().getId());//from response of step 3.
                options.put("theme.color", "#f37254");
                options.put("currency", "INR");
                options.put("payment_capture", "1");
                options.put("amount", createPaymentOrderResponseModel.getData()
                        .getAttributes().getAmount());//pass amount in currency subunits
                options.put("prefill.email", selectedPatientData.getEmailId());
                options.put("prefill.contact",selectedPatientData.getMobileNo());
                JSONObject retryObj = new JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                options.put("retry", retryObj);

                checkout.open(activity, options);

            } catch(Exception e) {
                Log.e(TAG, "Error in starting Razorpay Checkout", e);
            }
        }
        else if(obj.get("Description").toString().equals("Lab")) {
            activity = (LabOrderActivity) mContext;


            Checkout checkout = new Checkout();
            //checkout.setKeyID();
            checkout.setImage(R.drawable.logo);
            try {
                JSONObject options = new JSONObject();
                //  implementation 'com.razorpay:checkout:1.5.12'
                options.put("name", "mCURA Mobile Health");
                options.put("description", appNature);
                options.put("image", "https://www.mcura.com/images/logo.png");
                options.put("order_id", createPaymentOrderResponseModel.getData().getAttributes().getId());//from response of step 3.
                options.put("theme.color", "#f37254");
                options.put("currency", "INR");
                options.put("payment_capture", "1");
                options.put("amount", createPaymentOrderResponseModel.getData()
                        .getAttributes().getAmount());//pass amount in currency subunits
                options.put("prefill.email", selectedPatientData.getEmailId());
                options.put("prefill.contact",selectedPatientData.getMobileNo());
                JSONObject retryObj = new JSONObject();
                retryObj.put("enabled", true);
                retryObj.put("max_count", 4);
                options.put("retry", retryObj);

                checkout.open(activity, options);

            } catch(Exception e) {
                Log.e(TAG, "Error in starting Razorpay Checkout", e);
            }
        }

    }

}
