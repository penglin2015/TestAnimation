package com.xuyao.prancelib.database.dbBean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by admin on 2017/7/11.
 * 搜索关键字对象
 */

@DatabaseTable(tableName = "search_story_key")
public class SearchStoryBean implements Serializable{

    @DatabaseField(generatedId = true)
    int id;//唯一id
    @DatabaseField
    String key;//搜索关键字
    @DatabaseField
    long create_time;//创建时间

    @DatabaseField
    boolean is_delete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public boolean is_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
