package com.mcura.mcurapharmacy.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mcura.mcurapharmacy.encryption.AESCrypt;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mCURA1 on 3/6/2017.
 */

public class Helper {
    public static String changeDateFormat(String date) {
        String jsonValue = date;
        String timestamp = jsonValue.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date();
        try {
            createdOn  = new Date(Long.parseLong(timestamp));
        }catch (NumberFormatException  numberFormatException){
        }         SimpleDateFormat orderedFormat = new SimpleDateFormat("dd/MM/yyyy");
        String orderedDate = orderedFormat.format(createdOn);
        Log.d("formattedDate", orderedDate);
        System.out.print("formattedDate-->" + orderedDate);
        return orderedDate;
    }
    public static boolean isInternetConnected(Context context){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    public static String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
    public static String getCurrentDataAndTime(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        String completeDate = date + "/" + month + "/" + year;
        return completeDate;
    }
    public static String getCurrentDataAndTimeStatusChange(){
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        String completeDate = year + "/" + month + "/" + date;
        return completeDate;
    }
    public static String convert(int number) {

        if (number == 0) { return "Zero"; }

        String prefix = "";

        if (number < 0) {
            number = -number;
            prefix = "Negative";
        }

        String current = "";
        int place = 0;

        do {
            int n = number % 1000;
            if (n != 0){
                String s = convertLessThanOneThousand(n);
                current = s + specialNames[place] + current;
            }
            place++;
            number /= 1000;
        } while (number > 0);

        return (prefix + current).trim();
    }
    private static final String[] specialNames = {
            "",
            " Thousand",
            " Million",
            " Billion",
            " Trillion",
            " Quadrillion",
            " Quintillion"
    };

    private static final String[] tensNames = {
            "",
            " Ten",
            " Twenty",
            " Thirty",
            " Forty",
            " Fifty",
            " Sixty",
            " Seventy",
            " Eighty",
            " Ninety"
    };

    private static final String[] numNames = {
            "",
            " One",
            " Two",
            " Three",
            " Four",
            " Five",
            " Six",
            " Seven",
            " Eight",
            " Nine",
            " Ten",
            " Eleven",
            " Twelve",
            " Thirteen",
            " Fourteen",
            " Fifteen",
            " Sixteen",
            " Seventeen",
            " Eighteen",
            " Nineteen"
    };
    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20){
            current = numNames[number % 100];
            number /= 100;
        }
        else {
            current = numNames[number % 10];
            number /= 10;

            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) return current;
        return numNames[number] + " Hundred" + current;
    }
    public static String getAESCryptEncodeString(){
        AESCrypt aesCrypt;
        String query="";
        SimpleDateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        parseFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = parseFormat.format(new Date());
        System.out.println("convertedTime : " + gmtTime);

        try {
            aesCrypt = new AESCrypt("Mcura$Secr3tKeyF0rH0sP1taL$API!1");
            String encyptedStr = aesCrypt.encrypt(gmtTime);
            Log.d("encyptedStr", encyptedStr);
            query = URLEncoder.encode(encyptedStr, "utf-8");
            Log.d("query", query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }
    public static String changeDateTimeFormat(String date) {
        String jsonValue = date;
        String timestamp = jsonValue.split("\\(")[1].split("\\+")[0];
        Date createdOn = new Date();
        try {
            createdOn  = new Date(Long.parseLong(timestamp));
        }catch (NumberFormatException  numberFormatException){
        }         SimpleDateFormat orderedFormat = new SimpleDateFormat("dd/MM/yyyy | h:mm");
        String orderedDate = orderedFormat.format(createdOn);
        Log.d("formattedDate", orderedDate);
        System.out.print("formattedDate-->" + orderedDate);
        return orderedDate;
    }
}
