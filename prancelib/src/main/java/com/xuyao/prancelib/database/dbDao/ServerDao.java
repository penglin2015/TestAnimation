package com.xuyao.prancelib.database.dbDao;

import com.j256.ormlite.dao.Dao;
import com.xuyao.prancelib.database.DataBaseHelper;
import com.xuyao.prancelib.database.dbBean.ServerData;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by T470 on 2017/11/6.
 */

public class ServerDao {
    private DataBaseHelper helper;
    Dao<ServerData, Integer> serverDataDao;

    public ServerDao() {
        helper = DataBaseHelper.getInstance();
        try {
            serverDataDao = helper.getDao(ServerData.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建数据
     *
     * @param serverData
     */
    public void createServer(ServerData serverData) {
        try {
            serverDataDao.create(serverData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否存在数据,存在即刷新数据 不存在直接创建
     *
     * @return
     */
    public boolean isNullServerData() {
        try {
            List<ServerData> datas = serverDataDao.queryForAll();
            if (datas == null) {
                return true;
            }
            if (datas.size() == 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除全部数据
     */
    public void deleteAllData(){
        try {
            List<ServerData> datas = serverDataDao.queryForAll();
            if (datas == null&&datas.size()==0) {
                return;
            }
            for(ServerData data:datas){
                serverDataDao.deleteById(data.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询全部数据
     * @return
     */
    public ServerData queryServerData(){
        try {
            List<ServerData> serverDataList=serverDataDao.queryForAll();
            return serverDataList!=null&&serverDataList.size()>0?serverDataList.get(0):null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
