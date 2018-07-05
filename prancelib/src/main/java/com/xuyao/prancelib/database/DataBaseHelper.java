package com.xuyao.prancelib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xuyao.prancelib.database.dbBean.SearchStoryBean;
import com.xuyao.prancelib.database.dbBean.ServerData;
import com.xuyao.prancelib.util.LogUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 数据库根对象
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static String DB_NAME = "prance.db";
    private static final  int DB_VERSION =9;
    private Map<String,Dao> daos = new HashMap<String,Dao>();
    public DataBaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            for(Class clas:classes){
                TableUtils.createTableIfNotExists(connectionSource,clas);
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            LogUtils.e("[create com.penglin.prencelib.database error] :" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, SearchStoryBean.class,true);
            TableUtils.dropTable(connectionSource,ServerData.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            LogUtils.e("[update com.penglin.prencelib.database error :]" + e.getMessage());
        }
    }

    private static DataBaseHelper instance;
   static Class classes[];
    /**
     * 单例模式获取实例
     */
    public static DataBaseHelper getHeler(Context context,Class ...classes){
        DataBaseHelper.classes=classes;
        if (instance == null){
            synchronized (DataBaseHelper.class){
                if (instance == null){
                    instance = new DataBaseHelper(context);
                }
            }
        }
        return instance;
    }

    /**
     * 调单例模式
     * @return
     */
    public static DataBaseHelper getInstance(){
        return instance;
    }


    public Dao getDao(Class cl) throws java.sql.SQLException {
        Dao dao = null;
        String class_name = cl.getSimpleName();
        if (daos.containsKey(class_name)){
            dao = daos.get(class_name);
        }
        if (dao == null){
            dao = super.getDao(cl);
            daos.put(class_name,dao);
        }
        return dao;
    }

    /**
     * 释放
     */
    @Override
    public void close(){
        super.close();
        for (String key:daos.keySet()){
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
