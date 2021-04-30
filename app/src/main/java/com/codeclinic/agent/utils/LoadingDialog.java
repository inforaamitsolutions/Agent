package com.codeclinic.agent.utils;

import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;

import com.codeclinic.agent.R;
import com.github.ybq.android.spinkit.SpinKitView;

public class LoadingDialog {
    public static int isFreshInstall = 1;
    public static String mText = null;
    public boolean isDialogHidden = false;
    Dialog dialog;
    AppCompatTextView txt_title_loader;
    Context context;


    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void showProgressDialog(String title) {
        isDialogHidden = false;
        dialog = new Dialog(context, R.style.DialogThemeloader);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_loader);

        //	ProgressBarCircularIndeterminate progressbar = (ProgressBarCircularIndeterminate)dialog.findViewById(R.id.progressBar);
        //LazyLoader progressbar = (LazyLoader) dialog.findViewById(R.id.loader);
        SpinKitView spinKitView = dialog.findViewById(R.id.spin_kit);
        try {
            if (dialog != null) {
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void hideProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                isDialogHidden = false;
                dialog.cancel();
                dialog.dismiss();
            }
        }
    }
}
