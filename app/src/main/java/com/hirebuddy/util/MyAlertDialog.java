package com.hirebuddy.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;

/**
 * Created by mobulous11 on 8/3/17.
 */

public class MyAlertDialog {

    public static AlertDialog.Builder getMyDialogBuilder(Context context, boolean cancelable, String title, View dialogView)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(cancelable);
        builder.setTitle(title);
        builder.setView(dialogView);

        return builder;
    }



}
