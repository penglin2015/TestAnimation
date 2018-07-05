package com.xuyao.prancelib.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xuyao.prancelib.R;

import butterknife.ButterKnife;

public abstract class DialogManger<VH extends DialogManger.DialogBaseViewHolder> {

    public VH getDialogViewHolder(Context context) {
        return null;
    }

    public void doSomeThing(VH holder) {

    }

    Context context;
    int w;
    int h;
    Dialog dialog;
    VH holder;
    Window window;
    public DialogManger(Context context, int w, int h) {
        this.context = context;
        this.w = w;
        this.h = h;
        init();
    }


    public VH getHolder() {
        return holder;
    }

    /**
     * 初始化
     */
    void init(){
        dialog = new AlertDialog.Builder(context).create();
        showdialog();
        window = dialog.getWindow();
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

        holder = getDialogViewHolder(context);
        doSomeThing(holder);
        window.setContentView(holder.getDialogView());
        dialog.getWindow().setLayout(w, h);
        dialog.setCanceledOnTouchOutside(false);
        dismissDialog();
    }

    public DialogManger setGravity(int gravity){
        window.setGravity(gravity);
        return this;
    }

    public DialogManger setAnimation(int style){
        window.setWindowAnimations(style);
        return this;
    }

    public DialogManger setCanceledOnTouchOutside(boolean isCanceled){
        dialog.setCanceledOnTouchOutside(isCanceled);
        return this;
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

    /**
     * 重新初始化
     */
    public void reset(){
        dismissDialog();
        init();
    }


    public static abstract class DialogBaseViewHolder {

        Context context;
        View dialogView;


        public int layIdForDialogView() {
            return 0;
        }

        public DialogBaseViewHolder(Context context) {
            this.context = context;
            this.dialogView = LayoutInflater.from(context).inflate(layIdForDialogView(), null);
            ButterKnife.bind(this, dialogView);
            initView(this.dialogView);
        }

        public DialogBaseViewHolder(View dialogView) {
            this.dialogView = dialogView;
            ButterKnife.bind(this, dialogView);
            initView(this.dialogView);
        }

        public void initView(View baseView){

        }

        public <T extends View> T getView(int v_id){
            return this.dialogView.findViewById(v_id);
        }

        public <T extends View> T getView(View baseView, int v_id){
            return baseView.findViewById(v_id);
        }



        public View getDialogView() {
            return dialogView;
        }
    }

}
