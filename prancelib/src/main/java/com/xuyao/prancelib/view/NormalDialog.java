package com.xuyao.prancelib.view;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuyao.prancelib.R;
import com.xuyao.prancelib.util.DensityUtil;
import com.xuyao.prancelib.util.DialogManger;

public class NormalDialog {
    Context context;
    DialogManger dialogManger;
    DialogViewHolder dialogViewHolder;

    public NormalDialog(Context context) {
        this.context = context;
        dialogManger = new DialogManger<DialogViewHolder>(context, (int) DensityUtil.getWidthForRatioWidthPx(context, 0.7f), WindowManager.LayoutParams.WRAP_CONTENT) {
            @Override
            public DialogViewHolder getDialogViewHolder(Context context) {
                dialogViewHolder = new DialogViewHolder(context);
                dialogViewHolder.firstTv.setOnClickListener(onClickListener);
                dialogViewHolder.secondTv.setOnClickListener(onClickListener);
                dialogViewHolder.singleTv.setOnClickListener(onClickListener);
                return dialogViewHolder;
            }

            @Override
            public void doSomeThing(DialogViewHolder holder) {
                holder.firstTv.setOnClickListener(onClickListener);
                holder.secondTv.setOnClickListener(onClickListener);
                holder.singleTv.setOnClickListener(onClickListener);
            }
        };

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            int id = v.getId();
            if (id == R.id.firstTv) {
                buttonTouch(BUTT_TYPE.PAIRING_LEFT);
                buttonTouch(1);

            } else if (id == R.id.secondTv) {
                buttonTouch(BUTT_TYPE.PAIRING_RIGHT);
                buttonTouch(2);

            } else if (id == R.id.singleTv) {
                buttonTouch(BUTT_TYPE.SINGLE);
                buttonTouch(0);
            }
        }
    };

    public void buttonTouch(BUTT_TYPE butt_type) {}

    @Deprecated
    public void buttonTouch(int index){}

    /**
     * 点击类型
     */
    public enum BUTT_TYPE{
        PAIRING_LEFT,PAIRING_RIGHT,SINGLE
    }


    public NormalDialog setTitle(String title) {
        dialogViewHolder.title.setText(title);
        return this;
    }


    public NormalDialog setMsg(String msg) {
        dialogViewHolder.msg.setText(msg);
        return this;
    }

    public NormalDialog setFirstBottonName(String buttonName) {
        dialogViewHolder.firstTv.setText(buttonName);
        return this;
    }

    public NormalDialog setSecondBottonName(String buttonName) {
        dialogViewHolder.secondTv.setText(buttonName);
        return this;
    }

    public NormalDialog setSingleBottonName(String buttonName) {
        dialogViewHolder.singleTv.setText(buttonName);
        return this;
    }

    public NormalDialog setSingle(boolean isSingle) {
        if (isSingle) {
            dialogViewHolder.singleButtGroup.setVisibility(View.VISIBLE);
            dialogViewHolder.towButtGroup.setVisibility(View.GONE);
            dialogViewHolder.deLine.setVisibility(View.GONE);
            return this;
        }
        dialogViewHolder.towButtGroup.setVisibility(View.VISIBLE);
        dialogViewHolder.singleButtGroup.setVisibility(View.GONE);
        dialogViewHolder.deLine.setVisibility(View.VISIBLE);
        return this;
    }


    public void show() {
        dialogManger.showdialog();
    }

    public void dismiss() {
        dialogManger.dismissDialog();
    }

    class DialogViewHolder extends DialogManger.DialogBaseViewHolder {
        TextView title;
        TextView msg;
        TextView firstTv;
        TextView secondTv;
        LinearLayout towButtGroup;
        TextView singleTv;
        LinearLayout singleButtGroup;
        View deLine,blueLine;


        public DialogViewHolder(Context context) {
            super(context);
            title= getView(R.id.title);
            msg= getView(R.id.msg);
            firstTv= getView(R.id.firstTv);
            secondTv= getView(R.id.secondTv);
            singleTv= getView(R.id.singleTv);
            towButtGroup= getView(R.id.towButtGroup);
            singleButtGroup= getView(R.id.singleButtGroup);
            deLine= getView(R.id.deLine);
            blueLine= getView(R.id.blueLine);
        }

        @Override
        public int layIdForDialogView() {
            return R.layout.dialog_normal;
        }
    }
}
