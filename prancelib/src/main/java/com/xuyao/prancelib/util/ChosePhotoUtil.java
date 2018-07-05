package com.xuyao.prancelib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.xuyao.prancelib.R;

import java.io.File;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static android.app.Activity.RESULT_OK;

public class ChosePhotoUtil {

    Activity mA;

    public ChosePhotoUtil(Activity context) {
        this.mA = context;
        initChoicePicDialog();
    }

    DialogManger<ChoicePicViewHolder> choicePicViewHolderDialogManger;

    private void initChoicePicDialog() {
        choicePicViewHolderDialogManger = new DialogManger<ChoicePicViewHolder>(mA, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            public ChoicePicViewHolder getDialogViewHolder(Context context) {
                return new ChoicePicViewHolder(context);
            }
        };
        choicePicViewHolderDialogManger.setGravity(Gravity.BOTTOM);
        choicePicViewHolderDialogManger.setAnimation(R.style.translateBottomUpStyle);
        choicePicViewHolderDialogManger.setCanceledOnTouchOutside(true);
    }

   private  int cropX=1;
    private int cropY=1;
    class ChoicePicViewHolder extends DialogManger.DialogBaseViewHolder implements View.OnClickListener {

        public ChoicePicViewHolder(Context context) {
            super(context);
        }

        @Override
        public int layIdForDialogView() {
            return R.layout.dialog_choice_pic;
        }

        @Override
        public void initView(View baseView) {
            super.initView(baseView);
            baseView.findViewById(R.id.takePhotoFromCamera).setOnClickListener(this);
            baseView.findViewById(R.id.takePhotoFromFileBrower).setOnClickListener(this);
            baseView.findViewById(R.id.cancel).setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.takePhotoFromCamera) {
                PhotoPicker.builder()
                        //直接拍照
                        .setOpenCamera(true)
                        //拍照后裁剪
                        .setCrop(true)
                        //设置裁剪比例(X,Y)
                        .setCropXY(cropX, cropY)
                        //设置裁剪界面标题栏颜色，设置裁剪界面状态栏颜色
                        //.setCropColors(R.color.colorPrimary, R.color.colorPrimaryDark)
                        .start(mA);
            } else if (view.getId() == R.id.takePhotoFromFileBrower) {
                PhotoPicker.builder()
                        //设置图片选择数量
                        .setPhotoCount(1)
                        //取消选择时点击图片浏览
                        .setPreviewEnabled(false)
                        //开启裁剪
                        .setCrop(true)
                        //设置裁剪比例(X,Y)
                        .setCropXY(cropX, cropY)
                        //设置裁剪界面标题栏颜色，设置裁剪界面状态栏颜色
//                            .setCropColors(R.color.colorPrimary, R.color.colorPrimaryDark)
                        .start(mA);
            }
            choicePicViewHolderDialogManger.dismissDialog();
        }
    }

    public void show() {
        choicePicViewHolderDialogManger.showdialog();
    }

    public void setCropX(int cropX) {
        this.cropX = cropX;
    }

    public void setCropY(int cropY) {
        this.cropY = cropY;
    }

    /**
     * 处理读取回调
     * @param requestCode
     * @param resultCode
     * @param data
     * @return
     */
    public Object doResult(int requestCode, int resultCode, Intent data){
        //选择返回
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                return photos;
            }
        }
        //拍照功能或者裁剪后返回
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.CROP_CODE) {
            File file=new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH));
            LogUtils.e("文件大小:"+file.length());
            Uri uri=Uri.fromFile(file);
            return file;
        }
        return null;
    }

}
