package com.codeclinic.agent.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.codeclinic.agent.R;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;

public class CommonMethods {


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String convertSecondsToHMmSs(long milliSeaconds) {
        long s = milliSeaconds % 60;
        long m = TimeUnit.MILLISECONDS.toMinutes(milliSeaconds) % 60;
        long h = TimeUnit.MILLISECONDS.toHours(milliSeaconds) % 24;
        return String.format("%d:%02d:%02d", h, m, s);
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
    }

    public static String shareIntent(Context context, String productName) {

        PackageManager packageManager = context.getPackageManager();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check this " + productName + "   \n" + "https://play.google.com/store/apps/details?id=" + context.getPackageName());
        context.startActivity(Intent.createChooser(sendIntent, "Choose where to"));
        return getNameFromApp(packageManager, sendIntent);

    }

    public static void datePicker(final TextView tv, Context context) {
        final Calendar c;
        c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);


        final DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.Theme_AppCompat_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            String month;

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int m = monthOfYear + 1;

                if (m == 1) {
                    //month = getString(R.string.jaunuary);
                    month = "January";
                    m = 01;
                } else if (m == 2) {
                    //month = getString(R.string.february);
                    month = "February";
                    m = 02;
                } else if (m == 3) {
                    //month = getString(R.string.march);
                    month = "March";
                    m = 03;
                } else if (m == 4) {
                    //month = getString(R.string.april);
                    month = "April";
                    m = 04;
                } else if (m == 5) {
                    //month = getString(R.string.may);
                    month = "May";
                    m = 05;
                } else if (m == 6) {
//                            month = getString(R.string.june);
                    month = "June";
                    m = 06;
                } else if (m == 7) {
                    //month = getString(R.string.july);
                    month = "July";
                    m = 07;
                } else if (m == 8) {
                    //month = getString(R.string.august);
                    month = "August";
                    m = 8;
                } else if (m == 9) {
                    //month = getString(R.string.september);
                    month = "September";
                } else if (m == 10) {
                    //month = getString(R.string.octomer);
                    month = "October";
                } else if (m == 11) {
                    //month = getString(R.string.november);
                    month = "November";
                } else if (m == 12) {
                    //month = getString(R.string.december);
                    month = "December";
                }


                if (dayOfMonth < 10) {
                    tv.setText(year + "-" + m + "-" + "0" + dayOfMonth);
                } else {
                    tv.setText(m + "/" + dayOfMonth + "/" + year);
                    tv.setText(year + "-" + m + "-" + dayOfMonth);
                }

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        //datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis()-1000);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public static ArrayList<Date> getDayForWholeYear(String weekDay) {
        ArrayList<Date> arrList = new ArrayList();

        SimpleDateFormat format1 = new SimpleDateFormat("dd-M-yyyy");
        Date date = null;
        Calendar cal = Calendar.getInstance();

        for (int i = 0; i <= 52; i++) {
            try {
                cal.add(Calendar.WEEK_OF_YEAR, 0);
                if (weekDay.equals("Sunday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                } else if (weekDay.equals("Monday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                } else if (weekDay.equals("Tuesday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                } else if (weekDay.equals("Wednesday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                } else if (weekDay.equals("Thursday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                } else if (weekDay.equals("Friday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                } else if (weekDay.equals("Saturday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                }

                String formatted = format1.format(cal.getTime());
                date = format1.parse(formatted);
                arrList.add(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i <= 52; i++) {
            try {
                cal.add(Calendar.WEEK_OF_YEAR, +1);
                if (weekDay.equals("Sunday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                } else if (weekDay.equals("Monday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                } else if (weekDay.equals("Tuesday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                } else if (weekDay.equals("Wednesday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                } else if (weekDay.equals("Thursday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                } else if (weekDay.equals("Friday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                } else if (weekDay.equals("Saturday")) {
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                }

                String formatted = format1.format(cal.getTime());
                date = format1.parse(formatted);
                arrList.add(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return arrList;
    }

    public static String removeLastCharacter(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }


    public static String getNameFromApp(PackageManager packageManager, Intent shareIntent) {

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(shareIntent, 0);

        int lastDot = 0;

        String name = "";

        for (int i = 0; i < resolveInfos.size(); i++) {

            // Extract the label, append it, and repackage it in a LabeledIntent
            ResolveInfo resolveInfo = resolveInfos.get(i);
            String packageName = resolveInfo.activityInfo.packageName;

            lastDot = packageName.lastIndexOf(".");

            name = packageName.substring(lastDot + 1);
        }

        return name;
    }

    public static File compressImage(Uri photoURI, Compressor compressor) throws Exception {
        return compressor.setMaxWidth(400).setMaxHeight(400).setQuality(50).setCompressFormat(Bitmap.CompressFormat.JPEG).setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()).compressToFile(new File(photoURI.getPath()));
    }

    @SuppressLint("SimpleDateFormat")
    public static File compressImage(String photoPath, Compressor compressor) throws Exception {
        return compressor.setMaxWidth(500)
                .setMaxHeight(400)
                .setQuality(70)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .compressToFile(new File(photoPath));
    }

    public static boolean isPermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 200);
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean askLocationPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{"android.permission.ACCESS_FINE_LOCATION",
                                "android.permission.ACCESS_COARSE_LOCATION"}, 300);
                return false;
            }
        } else {
            return true;
        }
    }
}
