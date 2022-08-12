package com.GRP.uav_android.loginPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.GRP.uav_android.R;

/**
 * @author Mochuan
 * @version 1.0
 * @date 2020/3/25
 * @description: shows a loading dialog while loading
 **/

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity myActivity){
        activity = myActivity;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    /**
     * close dialog
     */
    public void dismissDialog(){
        dialog.dismiss();

    }
}
