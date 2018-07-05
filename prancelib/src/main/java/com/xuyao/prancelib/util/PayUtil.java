package com.xuyao.prancelib.util;

import android.app.Activity;
import android.widget.Toast;

import com.xgr.easypay.EasyPay;
import com.xgr.easypay.alipay.AliPay;
import com.xgr.easypay.alipay.AlipayInfoImpli;
import com.xgr.easypay.callback.IPayCallback;
import com.xgr.easypay.unionpay.Mode;
import com.xgr.easypay.unionpay.UnionPay;
import com.xgr.easypay.unionpay.UnionPayInfoImpli;
import com.xgr.easypay.wxpay.WXPay;
import com.xgr.easypay.wxpay.WXPayInfoImpli;

public abstract class PayUtil {


    public abstract void callback(PAY_MODE pay_mode);

    Activity context;
    public PayUtil(Activity context){
        this.context=context;
    }

    public void unionpay(){
        //实例化银联支付策略
        UnionPay unionPay = new UnionPay();
        //构造银联订单实体。一般都是由服务端直接返回。测试时可以用Mode.TEST,发布时用Mode.RELEASE。
        UnionPayInfoImpli unionPayInfoImpli = new UnionPayInfoImpli();
        //收款账号
        unionPayInfoImpli.setTn("814144587819703061900");
        unionPayInfoImpli.setMode(Mode.TEST);
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(unionPay, context, unionPayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                callback(PAY_MODE.UNION);
            }

            @Override
            public void failed() {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }


    public void wxpay(){
        //实例化微信支付策略
        String wxAppId = "";
        WXPay wxPay = WXPay.getInstance(context,wxAppId);
        //构造微信订单实体。一般都是由服务端直接返回。
        WXPayInfoImpli wxPayInfoImpli = new WXPayInfoImpli();
        wxPayInfoImpli.setTimestamp("");
        wxPayInfoImpli.setSign("");
        wxPayInfoImpli.setPrepayId("");
        wxPayInfoImpli.setPartnerid("");
        wxPayInfoImpli.setAppid("");
        wxPayInfoImpli.setNonceStr("");
        wxPayInfoImpli.setPackageValue("");
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(wxPay, context, wxPayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                callback(PAY_MODE.WX);
            }

            @Override
            public void failed() {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }


    public void alipay(){
        //实例化支付宝支付策略
        AliPay aliPay = new AliPay();
        //构造支付宝订单实体。一般都是由服务端直接返回。
        AlipayInfoImpli alipayInfoImpli = new AlipayInfoImpli();
        //订单实体又称支付凭证
        alipayInfoImpli.setOrderInfo("");
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(aliPay, context, alipayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                callback(PAY_MODE.ALI);
            }

            @Override
            public void failed() {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }

    private void toast(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public enum PAY_MODE{
        ALI,WX,UNION
    }

}
