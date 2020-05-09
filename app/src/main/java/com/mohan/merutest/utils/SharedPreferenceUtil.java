package com.mohan.merutest.utils;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.mohan.merutest.Constant.PREF_FILE_NAME;


public class SharedPreferenceUtil {

    private final static String QUERY = "";

    public void setSharedPreferenceString(@NonNull Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setSharedPreferenceBoolean(@NonNull Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @Nullable
    public String getSharedPreferenceString(@NonNull Context context, String key, String defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE_NAME, 0);
        return settings.getString(key, defValue);
    }

    public boolean getSharedPreferenceBoolean(@NonNull Context context, String key, boolean defValue) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE_NAME, 0);
        return settings.getBoolean(key, defValue);
    }

    public static void clearSharedPref(@NonNull Context context) {
        try {
            SharedPreferences sharedpreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.apply();
            editor.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}