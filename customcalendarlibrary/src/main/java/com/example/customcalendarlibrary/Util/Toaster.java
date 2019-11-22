package com.example.customcalendarlibrary.Util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    public static void generateShortToast(Context mContext, String message) {
        if (mContext != null)
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void generateLongToast(Context mContext, String message) {
        if (mContext != null)
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }
}
