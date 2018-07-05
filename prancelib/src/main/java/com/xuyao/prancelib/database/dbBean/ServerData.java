package com.xuyao.prancelib.database.dbBean;

/**
 * Created by T470 on 2017/11/6.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 服务器参数
 */
@DatabaseTable(tableName = "server_table")
public class ServerData implements Serializable {

    @DatabaseField(generatedId = true)
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * base_url : http://192.168.1.28/v1
     * timestamp : 1497843749
     */
    @DatabaseField
    private String base_url;//请求基地址
    @DatabaseField
    private String image_url;//图片基地址
    @DatabaseField
    String upload_url;//上传图片地址
    @DatabaseField
    private long timestamp;//服务器时间

    @DatabaseField
    long current_time=0;//本地时间和服务器的时间差  服务器时间是10位，本地时间需要除以1000

    @DatabaseField
    private String web_url;//网页基地址
    @DatabaseField
    String qq;//聊天QQ

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getUpload_url() {
        return upload_url;
    }

    public void setUpload_url(String upload_url) {
        this.upload_url = upload_url;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public long getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time() {
        long systemTime=System.currentTimeMillis();
        long currentTime=getTimestamp()-systemTime/1000;
        this.current_time = currentTime;
    }
}