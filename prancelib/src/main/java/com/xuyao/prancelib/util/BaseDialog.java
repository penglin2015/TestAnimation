package com.xuyao.prancelib.util;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BaseDialog {

    private Dialog dialog;
    View showDialogView;

    public BaseDialog(Context context, View view, int width, int height) {
        dialog = new AlertDialog.Builder(context).create();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        showdialog();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new BitmapDrawable());
        dialog.getWindow().clearFlags(WindowManager
                .LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        if (dm.heightPixels > dm.widthPixels) {
//            lp.width = dm.widthPixels;
//        }
//        else {
//            lp.width =width;
//        }
//        window.setAttributes(lp);

        window.setContentView(view);
        dialog.getWindow().setLayout(width, height);
        dialog.setCanceledOnTouchOutside(false);
        this.showDialogView = view;
        dismissDialog();
    }


    public void showdialog() {
        try {
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialog() {
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public View getShowDialogView() {
        return showDialogView;
    }

    public void setShowDialogView(View showDialogView) {
        if (dialog != null && !dialog.isShowing()) {
            this.showDialogView = showDialogView;
            dialog.setContentView(showDialogView);
        }
    }

    /**
     * 显示位置
     * @param gravity
     */
    public void setGravity(int gravity){
        dialog.getWindow().setGravity(gravity);
    }
}
