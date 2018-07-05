package com.xuyao.prancelib.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.youth.banner.loader.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by PL_Moster on 2017/4/24.
 * 获取手机中的JPG   PNG格式的图片
 */

public class ImageUtil {

    public static ImageUtil instance = null;
    public static Context context;

    public static ImageUtil getInstance(Context context) {
        ImageUtil.context = context;
        if (instance == null) {
            instance = new ImageUtil();
        }
        return instance;
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();

    private void getImages() {
        //显示进度条
//        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();

                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

                if (mCursor == null) {
                    return;
                }

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    String parentName = new File(path).getParentFile().getName();


                    //根据父路径名将图片放入到mGruopMap中
                    if (!mGruopMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<>();
                        chileList.add(path);
                        mGruopMap.put(parentName, chileList);
                    } else {
                        mGruopMap.get(parentName).add(path);
                    }
                }

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);
                mCursor.close();
            }
        }).start();

    }

    private final static int SCAN_OK = 1;//扫描完成标志
    private List<ImageBean> list = new ArrayList<>();
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    //关闭进度条
//                    mProgressDialog.dismiss();
                    list = (List<ImageBean>) subGroupOfImage(mGruopMap);
//                    adapter = new GroupAdapter(MainActivity.this, list = subGroupOfImage(mGruopMap), mGroupGridView);
//                    mGroupGridView.setAdapter(adapter);
                    break;
            }
        }

    };


    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @param mGruopMap
     * @return
     */
    private List<?> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
        if (mGruopMap.size() == 0) {
            return null;
        }
        List<ImageBean> list = new ArrayList<>();

        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            ImageBean mImageBean = new ImageBean();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            mImageBean.setFolderName(key);
            mImageBean.setImageCounts(value.size());
            mImageBean.setTopImagePath(value.get(0));//获取该组的第一张图片

            list.add(mImageBean);
        }

        return list;

    }


    /**
     * 转化的图片库
     */
    public class ImageBean {
        String folderName;
        int imageCounts;
        String topImagePath;

        public String getFolderName() {
            return folderName;
        }

        public void setFolderName(String folderName) {
            this.folderName = folderName;
        }

        public int getImageCounts() {
            return imageCounts;
        }

        public void setImageCounts(int imageCounts) {
            this.imageCounts = imageCounts;
        }

        public String getTopImagePath() {
            return topImagePath;
        }

        public void setTopImagePath(String topImagePath) {
            this.topImagePath = topImagePath;
        }
    }

//    /**
//     * 获取图片地址
//     *
//     * @param imgPath
//     * @param width
//     * @param height
//     * @param isOrigin
//     * @return
//     */
//    public static final String ORIGIN = "/origin";//原图后缀
//
//    public static String getDownImgUrl(String imgPath, int width, int height, boolean isOrigin) {
//        String imgUrl = "";
//        if (isOrigin) {
//            imgUrl = URL.getDbServer().getImage_url() + imgPath + ORIGIN;
//        } else {
//            imgUrl = URL.getDbServer().getImage_url() + imgPath + "/" + width + "x" + height;
//        }
//        LogUtils.e("图片地址为 " + imgUrl);
//        return imgUrl;
//    }

//    /**
//     * 地址为完整地址，但是后缀大小控制须有
//     *
//     * @param imgPath
//     * @param width
//     * @param height
//     * @param isOrigin
//     * @param isFullUrl
//     * @return
//     */
//    public static String getDownImgUrl(String imgPath, int width, int height, boolean isOrigin, boolean isFullUrl) {
//        String imgUrl = "";
//        if (isFullUrl) {
//            if (isOrigin) {
//                imgUrl = imgPath + ORIGIN;
//            } else {
//                imgUrl = imgPath + "/" + width + "x" + height;
//            }
//        } else {
//            if (isOrigin) {
//                imgUrl = URL.getDbServer().getImage_url() + imgPath + ORIGIN;
//            } else {
//                imgUrl = URL.getDbServer().getImage_url() + imgPath + "/" + width + "x" + height;
//            }
//        }
//        LogUtils.e("图片地址为 " + imgUrl);
//        return imgUrl;
//    }

    /**
     * glide
     *
     * @param imageVeiew
     * @param imgUrl
     * @param failSetImg
     */
    public static void glideSetImgToImgView(View imageVeiew, String imgUrl, final int failSetImg) {
        synchronized (imageVeiew.getContext()) {
            final ImageView iv = (ImageView) imageVeiew;
            Activity context= getActivityFromView(iv);
            if(context==null||context.isFinishing()){
                return;
            }
            Glide.with(context).load(imgUrl).placeholder(failSetImg).error(failSetImg).into(iv);
        }
    }

    /**
     * glide
     *  可以监听图片加载结果
     * @param imgUrl
     * @param failSetImg
     */
    public static void glideSetImgToImgView(GlideDrawableImageViewTarget glideDrawableImageViewTarget,String imgUrl, final int failSetImg) {
        View tav=glideDrawableImageViewTarget.getView();
        synchronized (tav.getContext()) {
            Activity context= getActivityFromView(tav);
            if(context==null||context.isFinishing()){
                return;
            }
            Glide.with(context).load(imgUrl).placeholder(failSetImg).error(failSetImg).into(glideDrawableImageViewTarget);
        }
    }


    /**
     * try get host activity from view.
     * views hosted on floating window like dialog and toast will sure return null.
     * @return host activity; or null if not available
     */
    public static Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /**
     * glide
     *
     * @param imv
     * @param imgUrl
     * @param failSetImg
     */
    public static void glideSetImgToImgView(View imv, String imgUrl, final int failSetImg, boolean isPlaceholder) {
        synchronized (imv.getContext()) {
            final ImageView iv = (ImageView) imv;
            Activity context= getActivityFromView(iv);
            if(context==null||context.isFinishing()){
                return;
            }
            DrawableRequestBuilder requestBuilder = Glide.with(context).load(imgUrl).error(failSetImg);
            if (isPlaceholder) {
                requestBuilder.placeholder(failSetImg);
            }
            requestBuilder.into(iv);
        }
    }

    /**
     * @param imageVeiew
     * @param imgUrl
     * @param failSetImg
     * @param viewWidth
     * @param viewHeight
     */
    public static void glideSetImgToImgView(View imageVeiew, String imgUrl, final int failSetImg, int viewWidth, int viewHeight) {
        synchronized (imageVeiew.getContext()) {
            final ImageView iv = (ImageView) imageVeiew;
            Activity context= getActivityFromView(iv);
            if(context==null||context.isFinishing()){
                return;
            }
            Glide.with(context).load(imgUrl).placeholder(failSetImg).override(viewWidth, viewHeight).placeholder(failSetImg).error(failSetImg).into(iv);
        }
    }
    /**
     * @param imageVeiew
     * @param imgUrl
     * @param viewWidth
     * @param viewHeight
     */
    public static void glideSetImgToImgView(View imageVeiew, String imgUrl, int viewWidth, int viewHeight) {
        synchronized (imageVeiew.getContext()) {
            final ImageView iv = (ImageView) imageVeiew;
            Activity context= getActivityFromView(iv);
            if(context==null||context.isFinishing()){
                return;
            }
            Glide.with(context).load(imgUrl).override(viewWidth, viewHeight).into(iv);
        }
    }
    /**
     * 设置资源文件
     *
     * @param imageVeiew
     * @param res
     * @param viewWidth
     * @param viewHeight
     */
    public static void glideSetImgToImgView(View imageVeiew, final int res, int viewWidth, int viewHeight) {
        synchronized (imageVeiew.getContext()) {
            final ImageView iv = (ImageView) imageVeiew;
            Activity context= getActivityFromView(iv);
            if(context==null||context.isFinishing()){
                return;
            }
            Glide.with(context).load(res).override(viewWidth, viewHeight).into(iv);
        }
    }

    /**
     * 设置资源文件
     *
     * @param imageVeiew
     * @param bitmap
     * @param viewWidth
     * @param viewHeight
     */
    public static void glideSetImgToImgView(View imageVeiew, final Bitmap bitmap, int viewWidth, int viewHeight) {
        synchronized (imageVeiew.getContext()) {
            final ImageView iv = (ImageView) imageVeiew;
            Activity context= getActivityFromView(iv);
            if(context==null||context.isFinishing()){
                return;
            }
            Glide.with(context).load(Bitmap2Bytes(bitmap)).override((int) DensityUtil.dpToPx(imageVeiew.getContext(), viewWidth), (int) DensityUtil.dpToPx(imageVeiew.getContext(), viewHeight)).into(iv);
        }
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * glide
     *
     * @param imageView
     * @param imgUrl
     * @param failSetImg
     * @param scaleType
     */
    public static void glideSetImgToImgView(View imageView, String imgUrl, int failSetImg, ImageView.ScaleType scaleType) {
        synchronized (imageView.getContext()) {
            ImageView iv = (ImageView) imageView;
            iv.setScaleType(scaleType);
            Activity context= getActivityFromView(iv);
            if(context==null||context.isFinishing()){
                return;
            }
            Glide.with(context).load(imgUrl).placeholder(failSetImg).error(failSetImg).into(iv);
        }
    }

    /**
     * 生成banner图片加载器
     *
     * @param imageViewBacColor
     * @param scaleType
     * @param failImg
     * @return
     */
    public static MyImageLoader callBackImgLoader(int imageViewBacColor, ImageView.ScaleType scaleType, int failImg) {
        return new MyImageLoader(imageViewBacColor, scaleType, failImg);
    }

    /**
     * 生成banner图片加载器
     *
     * @param imageViewBacColor
     * @param scaleType
     * @param failImg
     * @return
     */
    public static MyImageLoader callBackImgLoader(int imageViewBacColor, ImageView.ScaleType scaleType, int failImg, boolean isScale) {
        return new MyImageLoader(imageViewBacColor, scaleType, failImg,isScale);
    }

    public static class MyImageLoader extends ImageLoader {

        int imageViewBacColor;
        ImageView.ScaleType scaleType;
        int failImg;
        boolean isScale=false;

        public MyImageLoader(int imageViewBacColor, ImageView.ScaleType scaleType, int failImg) {

            this.imageViewBacColor = imageViewBacColor;
            this.scaleType = scaleType;
            this.failImg = failImg;
        }

        public MyImageLoader(int imageViewBacColor, ImageView.ScaleType scaleType, int failImg,boolean isScale) {
            this.imageViewBacColor = imageViewBacColor;
            this.scaleType = scaleType;
            this.failImg = failImg;
            this.isScale=isScale;
        }

        @Override
        public void displayImage(Context context, Object o, ImageView imageView) {
            imageView.setBackgroundColor(imageViewBacColor);
//            glideSetImgToImgView(imageView, "" + o, failImg, scaleType);//会存在部分机型无法加载图片
            aqueryLoadImg(imageView,""+o,failImg,scaleType);
        }
    }

    /**
     * AQuery加载图像
     * @param iv
     * @param url
     * @param failImgRes
     * @param scaleType
     */
    public static void aqueryLoadImg(ImageView iv, String url, int failImgRes, ImageView.ScaleType scaleType){
        iv.setScaleType(scaleType);
        new AQuery(iv).image(url,true,true,0,failImgRes);
    }


    /**
     * 将base64转换成bitmap
     * @param base64String
     * @return
     */
    public static Bitmap stringToBitmap(String base64String) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(base64String, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 图片转换成base64
     * @param bitmap
     * @return
     */
    public static String bitmapToString(Bitmap bitmap) {
//将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }
}
