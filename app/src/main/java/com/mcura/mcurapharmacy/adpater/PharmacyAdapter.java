/*
package com.mcura.mcurapharmacy.adpater;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mcura.mcurapharmacy.BuildConfig;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.Utils.Constants;
import com.mcura.mcurapharmacy.Utils.EnumType;
import com.mcura.mcurapharmacy.activity.PharmacyActivity;
import com.mcura.mcurapharmacy.activity.VisitSummaryActivity;
import com.mcura.mcurapharmacy.model.PharmacyModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


*/
/**
 * Created by mCURA1 on 11/15/2016.
 *//*

public class PharmacyAdapter extends BaseAdapter implements Filterable {
    AlertDialog.Builder alertBuilder;
    AlertDialog ad;
    private final int userRoleId;
    private SharedPreferences mSharedPreference;
    ArrayList<PharmacyModel> values;
    ArrayList<PharmacyModel> orig;
    //PharmacyModel[] pharmacyModels;
    private Context mContext;
    private LayoutInflater mInflater = null;
    private ValueFilter valueFilter;
    private String paymentMode="cash";
    private MCuraApplication mCuraApplication;
    private ProgressDialog progress;
    private ImageView imageData;
    private WebView lastVisitSummary_webview;

    public PharmacyAdapter(Context context, PharmacyModel[] models) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.values = new ArrayList<>(Arrays.asList(models));
        this.orig = new ArrayList<>(Arrays.asList(models));
        mSharedPreference = mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE);
        userRoleId = mSharedPreference.getInt(Constants.USER_ROLE_ID_KEY, 0);
        Log.d("models_size",models.length+"");
    }

    @Override
    public int getCount() {
        return orig.size();
    }

    @Override
    public PharmacyModel getItem(int position) {
        return orig.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.pharmacy_pending_order_row_layout, null);
            holder.ll_main = (LinearLayout) convertView.findViewById(R.id.ll_main);
            holder.tv_order_id = (TextView) convertView.findViewById(R.id.tv_order_id);
            holder.tv_doc_name = (TextView) convertView.findViewById(R.id.tv_doc_name);
            holder.tv_patient_name = (TextView) convertView.findViewById(R.id.tv_patient_name);
            //holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            //holder.tv_payment_status = (TextView) convertView.findViewById(R.id.tv_payment_status);
            holder.tv_patient_contact = (TextView) convertView.findViewById(R.id.tv_patient_contact);
            holder.iv_viewrecord = (Button) convertView.findViewById(R.id.iv_view_record);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PharmacyModel pharmacyModels = orig.get(position);
        holder.tv_order_id.setText(pharmacyModels.getPrescriptionId().toString());
        holder.tv_doc_name.setText(pharmacyModels.getDoctorName().toString());
        holder.tv_patient_name.setText(pharmacyModels.getPatname().toString());
        holder.tv_patient_contact.setText(pharmacyModels.getMobile().toString().trim());
        */
/*int PaymentStatus = pharmacyModels.getPaidStatus();
        if(PaymentStatus==0){
            holder.tv_payment_status.setText("Unpaid");
        }else if(PaymentStatus==1){
            holder.tv_payment_status.setText("Paid");
        }*//*

        holder.iv_viewrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("pharmacyModels", pharmacyModels);
                mContext.startActivity(new Intent(mContext, VisitSummaryActivity.class).putExtras(bundle));
            }
        });

       */
/* if (pharmacyModels.getStatusId() == 1) {
            holder.tv_status.setText("Order Pending");
            convertView.setBackgroundResource(R.drawable.border_side_color_fanta_layout);
        } else if (pharmacyModels.getStatusId() == 2) {
            holder.tv_status.setText("Complete");
        } else if (pharmacyModels.getStatusId() == 3) {
            holder.tv_status.setText("Order Requested");
        } else if (pharmacyModels.getStatusId() == 4) {
            holder.tv_status.setText("Billing Done");
        } else if (pharmacyModels.getStatusId() == 5) {
            holder.tv_status.setText("Sample Collection");
        } else if (pharmacyModels.getStatusId() == 6) {
            holder.tv_status.setText("Report Collection");
        }else if (pharmacyModels.getStatusId() == 7) {
            holder.tv_status.setText("Delivered");
            convertView.setBackgroundResource(R.drawable.border_side_color_green_layout);
        } else if (pharmacyModels.getStatusId() == 8) {
            holder.tv_status.setText("Delivery Pending");
            convertView.setBackgroundResource(R.drawable.border_side_color_blue_layout);
        } else if (pharmacyModels.getStatusId() == 9) {
            holder.tv_status.setText("Order Pending From Patient");
            convertView.setBackgroundResource(R.drawable.border_side_color_gray_layout);
        }*//*

        */
/*else if(status[position].equals("Delivered")){
            holder.tv_status.setTextColor(ContextCompat.getColor(mContext, R.color.green));
        }*//*


        */
/*holder.btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext, "Click", Toast.LENGTH_SHORT).show();
            }
        });*//*

        String dobEncode = pharmacyModels.getDob();
        String timestamp = dobEncode.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date(Long.parseLong(timestamp));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(createdOn);
        //holder.tv_datetime.setText(formattedDate);
        return convertView;
    }


    private class ViewHolder {
        TextView tv_order_id, tv_doc_name, tv_patient_name, tv_status, tv_datetime,tv_payment_status,tv_patient_contact;
        TextView tv_est_id, tv_days, tv_amount;
        Button btn_pay;
        Button iv_viewrecord;
        LinearLayout ll_main, ll_bottom_layout;
    }

    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                ArrayList<PharmacyModel> filterList = new ArrayList<PharmacyModel>();

                for (int i = 0; i < values.size(); i++) {
                    if (PharmacyActivity.searchBy.equals("status")) {
                        if (values.get(i).getStatusId().toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filterList.add(values.get(i));


                        }
                    } else if (PharmacyActivity.searchBy.equals("patient")) {
                        if (values.get(i).getPatname().toLowerCase().startsWith(constraint.toString().toLowerCase())) {

                            filterList.add(values.get(i));

                        }
                    }
                    */
/*else if (PharmacyActivity.searchBy.equals("payment_status")) {
                        if (values.get(i).getPaidStatus().toString().equals(constraint.toString().toLowerCase())) {
                            filterList.add(values.get(i));
                        }
                    }*//*


                }


                results.count = filterList.size();

                results.values = filterList;

            } else {

                results.count = values.size();

                results.values = values;

            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      Filter.FilterResults results) {

            orig = (ArrayList<PharmacyModel>) results.values;

            notifyDataSetChanged();


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
    private void getPatientRecord(PharmacyModel model) {
        Log.d("entryType", model.getEntryTypeId() + "");
        if (EnumType.EntryType.mData.getID() == model.getEntryTypeId()) {
            //Toast.makeText(PharmacyActivity.this, "Data", Toast.LENGTH_LONG).show();
        } else if (EnumType.EntryType.mHandwriting.getID() == model.getEntryTypeId()) {
            getDataFromAPI(model);
            //Toast.makeText(PharmacyActivity.this, "Handwriting", Toast.LENGTH_LONG).show();
        } else if (EnumType.EntryType.mVideo.getID() == model.getEntryTypeId()) {
            getDataFromAPI(model);
            //Toast.makeText(PharmacyActivity.this, "Video", Toast.LENGTH_LONG).show();
        } else if (EnumType.EntryType.mImage.getID() == model.getEntryTypeId()) {
            //Toast.makeText(PharmacyActivity.this, "Image", Toast.LENGTH_LONG).show();
            getDataFromAPI(model);
        } else if (EnumType.EntryType.mTextFile.getID() == model.getEntryTypeId()) {
            //Toast.makeText(PharmacyActivity.this, "TextFile", Toast.LENGTH_LONG).show();
            getDataFromAPI(model);
        }
    }

    private void getDataFromAPI(final PharmacyModel model) {
        showLoadingDialog();
        mCuraApplication.getInstance().mCuraEndPoint.getRecord(model.getUserRoleId(), model.getRecordId(), new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d("userRoleId", String.valueOf(model.getUserRoleId()));
                String ext = MimeTypeMap.getFileExtensionFromUrl(s);
                if (ext.equalsIgnoreCase("txt")) {
                    showDataDialog(s);
                } else if (ext.equalsIgnoreCase("jpg")) {
                    showImageDataDialog(s);
                } else if (ext.equalsIgnoreCase("mov")) {
                    showVideoDialog(s);
                } else if (ext.equalsIgnoreCase("pdf")) {
                    showHandwritingDialog(s);
                }
                dismissLoadingDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissLoadingDialog();
            }
        });
    }

    private void showDataDialog(final String response) {
        alertBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.show_text_data_dialog, null);
        alertBuilder.setView(convertView);
        final TextView textData = (TextView) convertView.findViewById(R.id.text_data);
        ImageView closeDialog = (ImageView) convertView.findViewById(R.id.close);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        ad = alertBuilder.show();
        new Thread() {
            @Override
            public void run() {
                String path = BuildConfig.BASE_URL+"/" + response;
                URL u = null;
                try {
                    u = new URL(path);
                    HttpURLConnection c = (HttpURLConnection) u.openConnection();
                    c.setRequestMethod("GET");
                    c.connect();
                    InputStream in = c.getInputStream();
                    final ByteArrayOutputStream bo = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    in.read(buffer); // Read from Buffer.
                    bo.write(buffer); // Write Into Buffer.

                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textData.setText(bo.toString());
                            try {
                                bo.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

    private void showImageDataDialog(final String response) {
        final String path = BuildConfig.BASE_URL+"/" + response;
        alertBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.show_image_data_dialog, null);
        alertBuilder.setCancelable(false);
        alertBuilder.setView(convertView);
        imageData = (ImageView) convertView.findViewById(R.id.image_data);
        ImageView closeDialog = (ImageView) convertView.findViewById(R.id.close);
        Picasso.with(mContext).load(path).into(imageData);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        ad = alertBuilder.show();
    }

    private void showVideoDialog(String response) {
        final String path = BuildConfig.BASE_URL+"/" + response;
        Log.d("path", path);
        alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.show_video_data_dailog, null);
        alertBuilder.setView(convertView);
        VideoView videoData = (VideoView) convertView.findViewById(R.id.video_data);
        Uri uri = Uri.parse(path);
        videoData.setVideoURI(uri);
        videoData.start();
        ImageView closeDialog = (ImageView) convertView.findViewById(R.id.close);
        //Picasso.with(PharmacyActivity.this).load(path).into(imageData);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        ad = alertBuilder.show();
    }

    private void showHandwritingDialog(String response) {
        Log.d("path", "showHandwritingDialog");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) ((int) displaymetrics.widthPixels * 0.9);
        int height = (int) ((int) displaymetrics.heightPixels * 0.7);
        final String path = BuildConfig.BASE_URL+ "/" + response;
        Log.d("path", path);
        alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.show_handwriting_data_dialog, null);

        alertBuilder.setView(convertView);
        WebView summary_webview = (WebView) convertView.findViewById(R.id.handwriting_data);
        summary_webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        summary_webview.getSettings().setBuiltInZoomControls(true);
        ImageView closeDialog = (ImageView) convertView.findViewById(R.id.close);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });

        summary_webview.setInitialScale(1);
        summary_webview.getSettings().setJavaScriptEnabled(true);
        summary_webview.getSettings().setLoadWithOverviewMode(true);
        summary_webview.getSettings().setUseWideViewPort(true);
        summary_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        summary_webview.setScrollbarFadingEnabled(false);
        summary_webview.setWebViewClient(new WebViewClient());
        Log.d("pdf", path);
        summary_webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + path);
        ad = alertBuilder.create();
        ad.show();
        ad.getWindow().setLayout(width, height);
    }

    private void lastVisitSummary(String response) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) ((int) displaymetrics.widthPixels * 1);
        int height = (int) ((int) displaymetrics.heightPixels * 1);
        final String path = BuildConfig.BASE_URL+ "/" + response;
        Log.d("path", path);
        alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setCancelable(false);
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        View convertView = inflater.inflate(R.layout.last_visit_summary_dialog, null);

        alertBuilder.setView(convertView);
        lastVisitSummary_webview = (WebView) convertView.findViewById(R.id.handwriting_data);
        lastVisitSummary_webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        lastVisitSummary_webview.getSettings().setBuiltInZoomControls(true);
        ImageView closeDialog = (ImageView) convertView.findViewById(R.id.close);
        ImageView print = (ImageView) convertView.findViewById(R.id.print);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWebPrintJob(lastVisitSummary_webview);
            }
        });

        lastVisitSummary_webview.setInitialScale(1);
        lastVisitSummary_webview.getSettings().setJavaScriptEnabled(true);
        lastVisitSummary_webview.getSettings().setLoadWithOverviewMode(true);
        lastVisitSummary_webview.getSettings().setUseWideViewPort(true);
        lastVisitSummary_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        lastVisitSummary_webview.setScrollbarFadingEnabled(false);
        lastVisitSummary_webview.setWebViewClient(new WebViewClient());
        Log.d("pdf", path);
        lastVisitSummary_webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + path);
        ad = alertBuilder.create();
        ad.show();
        ad.getWindow().setLayout(width, height);
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
}

*/
