package com.xuyao.prancelib.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.xuyao.prancelib.R;
import com.xuyao.prancelib.util.DialogManger;
import com.xuyao.prancelib.util.ScreenUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class NetWorkBase {

    Context mContext;
    FinalHttp finalHttp;
    String baseUrl = "";//设置父路径地址
    boolean isProgressShow = true;//是否显示加载进度
    Class baseModleClassz;//设置总数据模型

    public void setProgressShow(boolean progressShow) {
        isProgressShow = progressShow;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setBaseModleClassz(Class baseModleClassz) {
        this.baseModleClassz = baseModleClassz;
    }

    public NetWorkBase(Context context) {
        this.mContext = context;
        finalHttp = new FinalHttp();

        initLoadingDialog();
    }

    public void post(String endWithUrl, MyParams myParams) {
        String endUrl = baseUrl + endWithUrl;
        finalHttp.post(endUrl, myParams.getAjaxParams(), ajaxCallBack.setAjaxParams(myParams.getAjaxParams()).setUrl(endUrl));
    }

    public void get(String endWithUrl, MyParams myParams) {
        String endUrl = baseUrl + endWithUrl;
        finalHttp.get(endUrl, myParams.getAjaxParams(), ajaxCallBack.setAjaxParams(myParams.getAjaxParams()).setUrl(endUrl));
    }

    public void post(String endWithUrl) {
        String endUrl = baseUrl + endWithUrl;
        finalHttp.post(endUrl, ajaxCallBack.setAjaxParams(new AjaxParams()).setUrl(endUrl));
    }

    public void get(String endWithUrl) {
        String endUrl = baseUrl + endWithUrl;
        finalHttp.get(endUrl, ajaxCallBack.setAjaxParams(new AjaxParams()).setUrl(endUrl));
    }

    /**
     * 自定义参数
     */
    public static class MyParams {
        AjaxParams ajaxParams;

        public MyParams() {
            ajaxParams = new AjaxParams();
        }

        public MyParams put(String key, Object value) {
            ajaxParams.put(key, "" + value);
            return this;
        }

        public MyParams put(String key, File file) {
            try {
                ajaxParams.put(key, file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return this;
        }

        public AjaxParams getAjaxParams() {
            return ajaxParams;
        }
    }


    public static class MyAjaxCallBack extends AjaxCallBack {
        String url;
        AjaxParams ajaxParams;

        public String getUrl() {
            return url;
        }

        public MyAjaxCallBack setUrl(String url) {
            this.url = url;
            return this;
        }

        public AjaxParams getAjaxParams() {
            return ajaxParams;
        }

        public MyAjaxCallBack setAjaxParams(AjaxParams ajaxParams) {
            this.ajaxParams = ajaxParams;
            return this;
        }
    }

    Object data;
    MyAjaxCallBack ajaxCallBack = new MyAjaxCallBack() {
        @Override
        public void onStart() {
            super.onStart();
            showProgressDialog();
        }

        @Override
        public void onSuccess(Object o) {
            super.onSuccess(o);
            delayDismissDiaog();
            NetWorkBase.this.data = o;
            noticeElsePlace(o);
            if (resultCallbackListener != null)
                resultCallbackListener.success(o);
        }

        @Override
        public void onFailure(final Throwable t, final int errorNo, final String strMsg) {
            super.onFailure(t, errorNo, strMsg);
            delayDismissDiaog();
            if (resultCallbackListener != null)
                resultCallbackListener.fail(t, errorNo, strMsg);
        }
    };

    /**
     * 通知其他地方得到数据消息
     *
     * @param o
     */
    public final static String MODE_KEY = "base_mode";
    public final static String MODE_STR_KEY = "base_mode_str";
    public final static String MODE_ACTION_BROCAST = "mode_notice";

    private void noticeElsePlace(Object o) {
        try {
            if (mContext != null) {
                Bundle bundle = new Bundle();
                if (baseModleClassz != null) {
                    bundle.putSerializable(MODE_KEY, (Serializable) JSON.parseObject("" + o, baseModleClassz));
                }
                if (data != null) {
                    bundle.putString(MODE_STR_KEY, "" + data);
                }
                Intent intent = new Intent(MODE_ACTION_BROCAST);
                intent.putExtras(bundle);
                mContext.sendBroadcast(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    DialogManger<LoadingViewHolder> dialogManger;

    private void initLoadingDialog() {
        dialogManger = new DialogManger<LoadingViewHolder>(mContext,
                (int) (ScreenUtils.getScreenWidth(mContext) * 0.5f),
                WindowManager.LayoutParams.WRAP_CONTENT) {
            @Override
            public LoadingViewHolder getDialogViewHolder(Context context) {
                return new LoadingViewHolder(context);
            }
        };
    }

    class LoadingViewHolder extends DialogManger.DialogBaseViewHolder {
        public LoadingViewHolder(Context context) {
            super(context);
        }

        @Override
        public int layIdForDialogView() {
            return R.layout.dialog_loading;
        }
    }

    /**
     * 延迟dismiss掉dialog
     */
    private void delayDismissDiaog() {
        if (!isProgressShow) {
            return;
        }
        handler.postDelayed(runnable, 1500);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            dismissProgressDialog();
        }
    };


    private void showProgressDialog() {
        if (!isProgressShow) {
            return;
        }
        dialogManger.showdialog();
    }


    private void dismissProgressDialog() {
        dialogManger.dismissDialog();
    }


    public interface ResultCallbackListener {
        void success(Object data);

        void fail(Throwable t, int errorNo, String msg);
    }

    ResultCallbackListener resultCallbackListener;

    public NetWorkBase setResultCallbackListener(ResultCallbackListener resultCallbackListener) {
        this.resultCallbackListener = resultCallbackListener;
        return this;
    }

    /**
     * 获取顶层的模型
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getBaseData(Class<T> tClass) {
        try {
            String data = "" + this.data;
            if (data.equals("") || data.equalsIgnoreCase("null")) {
                return null;
            }
            return JSON.parseObject(data, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        T t = null;
        try {
            t = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }


    public <T> T getBaseDataForGson(Class<T> tClass) {
        try {
            String data = "" + this.data;
            if (data.equals("") || data.equalsIgnoreCase("null")) {
                return null;
            }
            Gson gson = new Gson();
            return gson.fromJson(data, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        T t = null;
        try {
            t = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

}
