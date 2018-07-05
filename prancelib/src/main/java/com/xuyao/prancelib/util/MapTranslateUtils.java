package com.xuyao.prancelib.util;

public class MapTranslateUtils{



    /**
     * 坐标转换，腾讯地图（火星坐标）转换成百度地图坐标
     * @param lat 腾讯纬度
     * @param lon 腾讯经度
     * @return 返回结果：经度,纬度
     */
    public static  double[] map_hx2bd(double lat, double lon){
        double bd_lat;
        double bd_lon;
        double x_pi=3.14159265358979324;
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        bd_lon = z * Math.cos(theta) + 0.0065;
        bd_lat = z * Math.sin(theta) + 0.006;
        double[] doubles = new double[]{bd_lat,bd_lon};
        System.out.println("bd_lat:"+bd_lat);
        System.out.println("bd_lon:"+bd_lon);
        return  doubles;
    }



    /**
     * 坐标转换，百度地图坐标转换成腾讯地图坐标
     * @param lat  百度坐标纬度
     * @param lon  百度坐标经度
     * @return 返回结果：纬度,经度
     */
    public static  double[] map_bd2hx(double lat, double lon){
        double tx_lat;
        double tx_lon;
        double x_pi=3.14159265358979324;
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        tx_lon = z * Math.cos(theta);
        tx_lat = z * Math.sin(theta);

        double[] doubles = new double[]{tx_lat,tx_lon};
        return doubles;
    }


    //高德转百度
   public static  double[] bd_encrypt(double gg_lat, double gg_lon) {
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double[] bdnavi=new double[2];
        bdnavi[0] = z * Math.cos(theta) + 0.0065;
        bdnavi[1] = z * Math.sin(theta) + 0.006;
        return bdnavi;
    }
    //百度转高德
   public static  double[] bd_decrypt(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);
        double gdnavi[]=new double[2];
        gdnavi[0] = z * Math.cos(theta);
        gdnavi[1] = z * Math.sin(theta);
        return gdnavi;
    }


}