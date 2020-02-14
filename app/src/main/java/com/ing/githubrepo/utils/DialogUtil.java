package com.ing.githubrepo.utils;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.ing.githubrepo.R;


/**
 * Created by karamans on 13.02.2020.
 */

public class DialogUtil {

    public static void showResponseErrorDialog(
            Activity activity, final DialogInterface.OnClickListener positiveOnClickListener,
            DialogInterface.OnClickListener negativeOnClickListener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        //dialogBuilder.setTitle(R.string.error);
        dialogBuilder.setMessage(R.string.an_error_occured);
        dialogBuilder.setPositiveButton(R.string.ok, positiveOnClickListener);
        dialogBuilder.setNegativeButton(R.string.try_again, negativeOnClickListener);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public static void showErrorDialog(Activity activity, String message) {

        if (activity == null)
            return;

        if (activity.isFinishing())
            return;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Dialog);
        dialogBuilder.setTitle(R.string.error);
        dialogBuilder.setMessage(message);
        dialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    public static void showNoReposDialog(
            Activity activity, final DialogInterface.OnClickListener positiveOnClickListener,
            DialogInterface.OnClickListener negativeOnClickListener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        dialogBuilder.setMessage(R.string.no_repos);
        dialogBuilder.setPositiveButton(R.string.ok, positiveOnClickListener);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}
