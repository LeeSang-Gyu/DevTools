package com.ssteam.nolcam.db;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingSharedPred {
    Context context;
    final String KEY_EXPLAIN = "isPermissionExplain";
    final String KEY_GPS = "isGpsPermission";

    public SettingSharedPred(Context context) {
        this.context = context;
    }

    public void setPermissionExplainValue(boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_EXPLAIN, value);
        editor.commit();
    }

    public boolean getPermissionExplainValue() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_EXPLAIN, false);
    }

    public void setGpsPermissionValue(boolean value) { // 처음 물어보면 false
        SharedPreferences sharedPreferences = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_GPS, value);
        editor.commit();
    }

    public boolean getGpsPermissionValue() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Setting", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_GPS, false);
    }
}
