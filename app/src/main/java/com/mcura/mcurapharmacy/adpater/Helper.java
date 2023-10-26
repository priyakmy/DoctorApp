package com.mcura.mcurapharmacy.adpater;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mcura.mcurapharmacy.encryption.AESCrypt;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Helper {
    public static Date JsonDateToDate(String jsonDate) {
        int idx1 = jsonDate.indexOf("(");
        int idx2 = jsonDate.indexOf(")") - 5;
        String s = jsonDate.substring(idx1+1, idx2);
        long l = Long.parseLong(s);
        return new Date(l);
    }

    public static String getCompleteDate() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        String completeDate = year + "-" + month + "-" + date; //"2016-05-09"
        return completeDate;
    }

    public static String convertDate(String Date) {
        // String timestamp = Date.split("\\(")[1].split("\\+")[0];

        Date createdOn = Helper.JsonDateToDate(Date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(createdOn);
        return formattedDate;
    }

    public static String changeRegDateFormat(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sszzz");
            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String changeDOBDateFormat(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sszzz");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String Convert24to12(String time) {
        String convertedTime = "", displayTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            displayTime = convertedTime.replace("am", "AM").replace("pm", "PM");
            System.out.println("convertedTime : " + displayTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return displayTime;
        //Output will be 10:23 PM
    }

    public static boolean compareTime(String time1, String time2) {
        boolean status = false;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.format(date);
        System.out.println(dateFormat.format(date));

        try {
            if (dateFormat.parse(time1).before(dateFormat.parse(time2))) {
                status = true;
            } else {
                status = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String Convert24to12WithSecond(String time) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }


    public static String Convert24to12WithoutSecond(String time) {
        String convertedTime = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");//displayFormat
            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");//parseFormat
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }

    public static String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        String completeDate = year + "-" + month + "-" + date;
        return completeDate;
    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        String monthname = getMonthShortName(month);
        String curTime = date + "-" + monthname + "-" + year;
        return curTime;
    }

    public static String getDob(String encodedDob) {
        //String timestamp = encodedDob.split("\\(")[1].split("\\+")[0];
        Date createdOn = Helper.JsonDateToDate(encodedDob);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String dob = sdf.format(createdOn);
        return dob;
    }

    public static String ConvertReverseDob(String time) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MMM-yyyy");//dd-MMM-yyyy
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }

    public static String getMonthShortName(int monthNumber) {
        String monthName = "";
        if (monthNumber >= 0 && monthNumber < 12)
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            } catch (Exception e) {
                if (e != null)
                    e.printStackTrace();
            }
        return monthName;
    }

    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    public static String changeDateFormat(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String getCurrentDataAndTime() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int date = now.get(Calendar.DATE);
        String completeDate = date + "/" + month + "/" + year;
        return completeDate;
    }

    public static String getAESCryptEncodeString() {
        AESCrypt aesCrypt;
        String query = "";
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

    public static String getCurrentTimestamp() {
        java.util.Date date = new java.util.Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
        String ts = timeStamp.toString();
        return ts;
    }
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static String changeDObDateFormat(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String changeEventDateActivity(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String getCSharpDateFormat() {
        String date = getVitalDate();
        String JsonDate = "";
        long milliseconds;
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
            JsonDate = "/Date(" + milliseconds + "+0530)/";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return JsonDate;
    }

    public static String getVitalDate() {
        int year, month, date;
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        date = now.get(Calendar.DATE);
        String currentDate = date + "-" + month + "-" + year;
        return currentDate;
    }

    public static String Converttime(String time) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("EEE, dd MMM, yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
        //Output will be 10:23 PM
    }

    public static String getCompleteDateFormat(String time) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String capitalize(String input) {
        String[] words = input.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            if (i > 0 && word.length() > 0) {
                builder.append(" ");
            }

            String cap = word.substring(0, 1).toUpperCase() + word.substring(1);
            builder.append(cap);
        }
        return builder.toString();
    }

    public static String convert_YYYYMMDD_To_DDMMMYYYY(String time) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = parseFormat.parse(time);
            convertedTime = displayFormat.format(date);
            System.out.println("convertedTime : " + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String convert_YYYYMMDD_To_DDMMYYYY(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static String convert_DDMMYYYY_To_YYYYMMDD(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat parseFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static int getMonthDays(int month, int year) {
        int daysInMonth;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else {
            if (month == 2) {
                daysInMonth = (year % 4 == 0) ? 29 : 28;
            } else {
                daysInMonth = 31;
            }
        }
        return daysInMonth;
    }

    public static String revDateFormat(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }

    public static boolean CheckDates(String startDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String currentDateandTime = dfDate.format(new Date());

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(currentDateandTime))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(currentDateandTime))) {
                b = true;  // If two dates are equal.
            } else {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }

    public static String getBuildVersion(Context mContext) {
        PackageInfo pInfo = null;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionName;
    }

    public static String minusMinute(String date, String time) {
        //MM/dd/yyyy
        String dateTime = date + " " + time;
        System.out.println(dateTime);
        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d = null;
        Calendar cal = null;
        try {
            d = dfDate.parse(dateTime);
            cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MINUTE, -1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(dfDate.format(cal.getTime()));
        return dfDate.format(cal.getTime());
    }

    public static String plusMinute(String date, String time) {
        //MM/dd/yyyy
        String dateTime = date + " " + time;
        System.out.println(dateTime);
        SimpleDateFormat dfDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d = null;
        Calendar cal = null;
        try {
            d = dfDate.parse(dateTime);
            cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MINUTE, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(dfDate.format(cal.getTime()));
        return dfDate.format(cal.getTime());
    }

    public static String dateTimeDifference(String fromDateTime, String currentDateTime) {
        String dateStart = currentDateTime;
        String dateStop = fromDateTime;

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        String dateTimeDiff = "";
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            dateTimeDiff = diffDays+","+diffHours+","+diffMinutes+","+diffSeconds;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTimeDiff;
    }
    public static String convert_DDMMYYYY_To_MMDDYYYY(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
    public static String convert_DDMMYYYY_To_MMDD(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
    public static String convert_DDMMYYYY_To_DDMMMYYYY(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
            System.out.println("registrationDate" + convertedTime);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
    public static String convertPostData(String s) {
        String convertedTime = "";
        try {
            SimpleDateFormat parseFormat = new SimpleDateFormat("dd-mm-yyyy");
            SimpleDateFormat displayFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date = parseFormat.parse(s);
            convertedTime = displayFormat.format(date);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return convertedTime;
    }
}