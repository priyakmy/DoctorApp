package com.mcura.mcurapharmacy.activity;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mcura.mcurapharmacy.BuildConfig;
import com.mcura.mcurapharmacy.MCuraApplication;
import com.mcura.mcurapharmacy.R;
import com.mcura.mcurapharmacy.service.DownloadService;

import java.io.File;


public class VisitSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progress;
    private MCuraApplication mCuraApplication;
    private WebView lastVisitSummary_webview;
    private Button btn_close, btn_print;
    private ProgressBar pbar;
    private ImageButton iv_download;
    private String patName,patContact;
    private static final int MY_NOTIFICATION_ID = 1;
    NotificationManager notificationManager;
    private NotificationCompat.Builder myNotification;
    private BroadcastReceiver DownloadReceiver=new BroadcastReceiver(){
        public void onReceive(Context context,Intent intent){
            // Display message from DownloadService
            Bundle b=intent.getExtras();
            if(b!=null){
                //Toast.makeText(VisitSummaryActivity.this,b.getString(DownloadService.EXTRA_MESSAGE),Toast.LENGTH_SHORT).show();
                //tv.setText(b.getString(DownloadService.EXTRA_MESSAGE));
                String fileName = patName + patContact.trim() + ".pdf";
                String filePath = Environment.getExternalStorageDirectory() + "/" + fileName;
                File file = new File(filePath);
                Log.d("downloadLink", "File to download = " + String.valueOf(file));
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String ext=file.getName().substring(file.getName().indexOf(".")+1);
                String type = mime.getMimeTypeFromExtension(ext);
                Intent openFile = new Intent(Intent.ACTION_VIEW, Uri.fromFile(file));
                openFile.setDataAndType(Uri.fromFile(file), type);
                openFile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent p = PendingIntent.getActivity(VisitSummaryActivity.this, 0, openFile, 0);
                myNotification = new NotificationCompat.Builder(VisitSummaryActivity.this);
                myNotification.setContentTitle("PDF Download")
                        .setContentText("download completed")
                        .setTicker("mCURA")
                        .setWhen(System.currentTimeMillis())
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setSmallIcon(R.mipmap.app_logo)
                        .setContentIntent(p);
                notificationManager.notify(MY_NOTIFICATION_ID, myNotification.build());
            }
        }
    };
    private String pdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_summary);
        initView();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        pdf = bundle.getString("pdf");
        patName = bundle.getString("patname");
        patContact = bundle.getString("patcontact");
        Log.d("detail",patName);
        Log.d("detail",patContact);
        lastVisitSummary(pdf);

        /*PharmacyModel pharmacyModels = (PharmacyModel) bundle.getSerializable("pharmacyModels");
        getPatientRecord(pharmacyModels);*/
    }

    private void initView() {
        pbar = (ProgressBar) findViewById(R.id.progressBar1);
        lastVisitSummary_webview = (WebView) findViewById(R.id.visit_summary);
        btn_close = (Button) findViewById(R.id.btn_close_pdf);
        btn_print = (Button) findViewById(R.id.btn_print_pdf);
        iv_download = (ImageButton) findViewById(R.id.iv_download);
        btn_close.setOnClickListener(this);
        btn_print.setOnClickListener(this);
        iv_download.setOnClickListener(this);
    }



    private void lastVisitSummary(String response) {
        dismissLoadingDialog();
        final String path = BuildConfig.BASE_URL + response;
        Log.d("path", path);
        lastVisitSummary_webview.getSettings().setBuiltInZoomControls(true);
        lastVisitSummary_webview.setInitialScale(1);
        lastVisitSummary_webview.getSettings().setLoadWithOverviewMode(true);
        lastVisitSummary_webview.getSettings().setUseWideViewPort(true);
        lastVisitSummary_webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        lastVisitSummary_webview.setScrollbarFadingEnabled(false);
        lastVisitSummary_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        lastVisitSummary_webview.getSettings().setJavaScriptEnabled(true);
        Log.d("pdf", path);
        //https://docs.google.com/gview?embedded=true&url=
        //http://drive.google.com/viewerng/viewer?embedded=true&url=
        String completeUrl = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + path;
        lastVisitSummary_webview.loadUrl(completeUrl);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {

        /*PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = this.getString(R.string.app_name) +
                " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());*/
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = getString(R.string.app_name) + " Document";
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if (printJob.isCompleted()) {
            Toast.makeText(getApplicationContext(), "Print Completed", Toast.LENGTH_LONG).show();
        } else if (printJob.isFailed()) {
            Toast.makeText(getApplicationContext(), "Print Failed", Toast.LENGTH_LONG).show();
        }
    }


    public void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage(this.getString(R.string.loading_message));
        }
        progress.show();
    }

    public void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_pdf:
                finish();
                break;
            case R.id.btn_print_pdf:
                createWebPrintJob(lastVisitSummary_webview);
                break;
            case R.id.iv_download:
                final String path = BuildConfig.BASE_URL + "/" + pdf;
                Intent newIntent=new Intent(VisitSummaryActivity.this,DownloadService.class);
                newIntent.setAction(DownloadService.ACTION_DOWNLOAD);
                newIntent.putExtra(DownloadService.EXTRA_URL,path);
                newIntent.putExtra("patname",patName);
                newIntent.putExtra("patcontact",patContact);
                // Start Download Service
                Toast.makeText(VisitSummaryActivity.this,"Downloading...",Toast.LENGTH_SHORT).show();
                VisitSummaryActivity.this.startService(newIntent);
                break;
        }
    }
    protected void onResume(){
        super.onResume();
        // Register receiver to get message from DownloadService
        registerReceiver(DownloadReceiver, new IntentFilter(DownloadService.ACTION_DOWNLOAD));

    }

    protected void onPause(){
        super.onPause();
        // Unregister the receiver
        unregisterReceiver(DownloadReceiver);

    }
}
