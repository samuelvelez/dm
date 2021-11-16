package com.bhargav.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {


    public SharedPreferences sharedPreferences;

    public Utils(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
    }

    public boolean infoget() {
        return this.sharedPreferences.getBoolean("check1", false);
    }

    public static final String FILENAME = "PREFERENCES_FILE";

    public static void saveData(Context context, String key, String data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public static String getData(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(key, null);
        return data;
    }

    public static void removeData(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);

    }

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

}
