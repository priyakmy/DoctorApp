package com.mcura.mcurapharmacy.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DownloadService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_DOWNLOAD = "http://test.tn.mcura.com";
    //public static final String ACTION_BAZ = "com.example.dara.myapplication.action.BAZ";

    // TODO: Rename parameters
    public static final String EXTRA_URL = "http://test.tn.mcura.com";
    public static final String EXTRA_MESSAGE = "http://test.tn.mcura.com";
    private String patName, patContact;
    private static final int MY_NOTIFICATION_ID = 1;
    NotificationManager notificationManager;
    Notification myNotification;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final String url = intent.getStringExtra(EXTRA_URL);
                patName = intent.getStringExtra("patname");
                patContact = intent.getStringExtra("patcontact");
                Log.e("Service", url);
                Log.e("Service", patName + "" + patContact);
                downloadImage(url);
            }
        }
    }

    private void downloadImage(String urlStr) {
        FileOutputStream fos = null;
        InputStream is = null;
        String message = "Download failed.";
        try {
            // Get InputStream from the image url
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //connection.setDoInput(true);
            //connection.connect();
            is = connection.getInputStream();
            String fileName = patName + patContact.trim() + ".pdf";
            fos = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + fileName);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
            message = "Download completed";


        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (fos != null) {
                try {
                    fos.close();

                } catch (IOException e) {
                }

            }
            if (is != null) {
                try {
                    is.close();

                } catch (IOException e) {
                }

            }
            // Send the feedback message to the MainActivity
            Intent backIntent = new Intent(DownloadService.ACTION_DOWNLOAD);
            backIntent.putExtra(DownloadService.EXTRA_MESSAGE, message);
            sendBroadcast(backIntent);

            //generate notification
            //String notificationText = String.valueOf((int) (100 * i / 10)) + " %";

        }
    }
}
