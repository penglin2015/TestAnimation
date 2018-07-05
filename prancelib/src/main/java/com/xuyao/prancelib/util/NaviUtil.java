package com.xuyao.prancelib.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.stmt.query.In;
import com.xuyao.prancelib.base.WebActivity;
import com.xuyao.prancelib.base.WebLoadMapActivity;

import java.io.File;
import java.net.URISyntaxException;

public class NaviUtil {


    public static void invokingBD(Context mContext) {

        //  com.baidu.BaiduMap这是高德地图的包名
        //调起百度地图客户端try {
        Intent intent = null;
        try {
            String uri = "intent://map/direction?origin=latlng:0,0|name:我的位置&destination=" + "需要导航的地址" + "&mode=drivingion=" + "城市" + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
            intent = Intent.getIntent(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (isInstallByread("com.baidu.BaiduMap")) {
            mContext.startActivity(intent); //启动调用
            Log.e("GasStation", "百度地图客户端已经安装");
        } else {
            Toast.makeText(mContext, "没有安装百度地图客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 高德地图导航
     *
     * @param mContext
     * @param softName
     * @param bournName
     * @param lat
     * @param lon
     */
    public static void invokingGD(Context mContext, String softName, String bournName, double lon, double lat) {

//        //  com.autonavi.minimap这是高德地图的包名
//        Intent intent = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=应用名称&lat="+ "&dev=0"));
//        intent.setPackage("com.autonavi.minimap");
//        intent.setData(Uri.parse("androidamap://poi?sourceApplication=softname&keywords="+"需要导航的地址"));
//
//        if(isInstallByread("com.autonavi.minimap")){
//            mContext.startActivity(intent);
//            Log.e("GasStation", "高德地图客户端已经安装") ;
//        }else{
//            Toast.makeText(mContext, "没有安装高德地图客户端", Toast.LENGTH_SHORT).show();
//        }


//        Intent intent = new Intent("android.intent.action.VIEW",
//                Uri.parse("androidamap://showTraffic?sourceApplication="+softName+"&amp;poiid=BGVIS1&amp;lat=36.2&amp;lon=116.1&amp;level=10&amp;dev=0"));
//        intent.setPackage("com.autonavi.minimap");
//        mContext.startActivity(intent);
        String gdPacketName = "com.autonavi.minimap";
        if (isInstallByread(gdPacketName)) {
            Intent intent = new Intent();
            intent.addCategory("android.intent.category.DEFAULT");
            intent.setAction("android.intent.action.VIEW");
            String gdUriString = "androidamap://navi?sourceApplication=";
            gdUriString += softName;
            gdUriString += "&poiname=";
            gdUriString += bournName;//导航目的地的名称
            gdUriString += "&lat=";
            gdUriString += lat;//导航地址的纬度
            gdUriString += "&lon=";
            gdUriString += lon;//导航地址的经度
            gdUriString += "&dev=";
            gdUriString += 1;//是否国策加密 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
            gdUriString += "&style=";
            gdUriString += 0;//"导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵)"
            intent.setData(Uri.parse(gdUriString));
            intent.setPackage(gdPacketName);
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, "没有安装高德地图客户端", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 百度地图导航
     *
     * @param context
     * @param nowLat
     * @param nowLng
     * @param bournAddressDes
     */
    public static void selectBaidu(Context context, double nowLat, double nowLng, String bournAddressDes) {
        try {
            //调起App
            String bdPackName = "com.baidu.BaiduMap";
            if (isInstallByread(bdPackName)) {
                Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:"
                        + nowLat + "," + nowLng
                        + "|name:&destination=" + bournAddressDes + "&mode=driving®ion=" + "我的位置"
                        + "&referer=Autohome|GasStation#Intent;scheme=bdapp;package=" + bdPackName + ";end");
                context.startActivity(intent);
            } else {
            /*String url = "http://api.map.baidu.com/direction?origin=latlng:" + mLatitude + ","
                            + mLongitude + "|name:&destination=" + mDestination
                            + "&mode=driving&output=html&src=天工项目共建";
                    WebViewActivity.launch(getActivity(), url, "网页版地图导航");*/
                Toast.makeText(context, "如果您没有安装百度地图APP，" +
                        "可能无法正常使用导航，建议选择其他地图", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据经纬度搜索
     * @param context
     * @param lat
     * @param lon
     */
    public static void selectedBaiduSearchWhere(Context context,double lat,double lon,String whereName) {
        String bdPackName = "com.baidu.BaiduMap";
        if(!isInstallByread(bdPackName)){
            String url="http://api.map.baidu.com/marker?location="+lat+","+lon+"&title="+whereName+"&content="+whereName+"&output=html";
            WebLoadMapActivity.startWebLoadMapActivity(context,whereName,url);
            return;
        }
        Intent i1 = new Intent();
            // 反向地址解析
        String uri="baidumap://map/marker?location="+lat+","+lon+"&title="+whereName+"&traffic=on";
        i1.setData(Uri.parse(uri));
        context.startActivity(i1);
    }

    /**
     * 高德定位地址服务
     * @param context
     * @param lat
     * @param lon
     * @param whereName
     */
    public static void selectedGaodeSearchWhere(Context context,double lat,double lon,String whereName){
        String gdPacketName = "com.autonavi.minimap";
        if(!isInstallByread(gdPacketName)){
            String gdUrl="http://uri.amap.com/marker?";
            gdUrl+="position="+lon+","+lat+"&name="+whereName;
            WebLoadMapActivity.startWebLoadMapActivity(context,whereName,gdUrl);
            return;
        }
        Intent intent=new Intent();
        intent.setPackage(gdPacketName);
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse("androidamap://viewMap?sourceApplication=zhongqi&lat="+lat+"&lon="+lon+"&poiname="+whereName+"&dev="+0));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 选择高德地图导航
     *
     * @param mActivity
     * @param desLat
     * @param desLng
     * @param nowLat
     * @param nowLng
     */
    public static void selectGaode(Context mActivity, double desLat, double desLng, double nowLat, double nowLng) {
        double[] txDesLatLng = MapTranslateUtils.map_bd2hx(desLat, desLng);
        double[] txNowLatLng = MapTranslateUtils.map_bd2hx(nowLat, nowLng);
        if (isInstallByread("com.autonavi.minimap")) {
            try {
                Intent intentOther = new Intent("android.intent.action.VIEW",
                        Uri.parse("androidamap://navi?sourceApplication=amap&lat="
                                + desLat + "&lon=" + desLng + "&dev=1&stype=0"));
                intentOther.setPackage("com.autonavi.minimap");
                intentOther.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mActivity.startActivity(intentOther);
            } catch (Exception e) {
                String url = "http://m.amap.com/?from="
                        + nowLat + "," + nowLng
                        + "(from)&to=" + desLat + "," + desLng + "(to)&type=0&opt=1&dev=0";
                WebActivity.startWebActivity(mActivity, "导航", url, false);
            }

        } else {
            String url = "http://m.amap.com/?from="
                    + txNowLatLng[0] + "," + txNowLatLng[1]
                    + "(from)&to=" + txDesLatLng[0] + "," + txDesLatLng[1] + "(to)&type=0&opt=1&dev=0";
            WebActivity.startWebActivity(mActivity, "导航", url, false);

        }

    }


    /**
     * 腾讯地图导航
     *
     * @param mActivity
     * @param desLat
     * @param desLng
     * @param nowLat
     * @param nowLng
     */
    private void selectTencent(Context mActivity, double desLat, double desLng, double nowLat, double nowLng) {
        double[] txDesLatLng = MapTranslateUtils.map_bd2hx(desLat, desLng);
        double[] txNowLatLng = MapTranslateUtils.map_bd2hx(nowLat, nowLng);
        String url = "http://apis.map.qq.com/uri/v1/routeplan?type=drive&from=&fromcoord="
                + txNowLatLng[0] + "," + txNowLatLng[1]
                + "&to=&tocoord=" + txDesLatLng[0] + "," + txDesLatLng[1] + "&policy=0&referer=myapp";
        WebActivity.startWebActivity(mActivity, "导航", url, false);
    }

    public static void invokingTencetNavi() {
        String uri = "qqmap://map/routeplan?type=drive&from=天坛南门&fromcoord=39.873145,116.413306&to=国家大剧院&tocoord=39.907380,116.388501";


    }

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    private static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

}
