package com.xuyao.prancelib.database.dbDao;

import com.j256.ormlite.dao.Dao;
import com.xuyao.prancelib.database.DataBaseHelper;
import com.xuyao.prancelib.database.dbBean.SearchStoryBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by admin on 2017/7/11.
 * 获得操作工具
 */

public class SearchDao {

    private DataBaseHelper helper;
    Dao<SearchStoryBean, Integer> foodIntegerDao;

    public SearchDao() {
        helper = DataBaseHelper.getInstance();
        try {
            foodIntegerDao = helper.getDao(SearchStoryBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加数据
     *
     * @param food
     */
    public void createSearchStoryBean(SearchStoryBean food) {
        try {
            foodIntegerDao.create(food);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(SearchStoryBean food) {
        try {
            SearchStoryBean qf=foodIntegerDao.queryBuilder().where().eq("key",food.getKey()).queryForFirst();
            qf.setCreate_time(System.currentTimeMillis());
            qf.setKey(food.getKey());
            qf.setIs_delete(false);
            foodIntegerDao.createOrUpdate(qf);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SearchStoryBean> getSearchStoryBeans() {
        try {
            return foodIntegerDao.queryBuilder().orderBy("create_time",false).query();//按照时间排序
//            return serverDataDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 单个查询
     *
     * @param id
     * @return
     */
    public SearchStoryBean getSingleSearchStoryBean(int id) {
        try {
            return foodIntegerDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SearchStoryBean getSearchStoryBeanForName(String key) {
        try {
            return foodIntegerDao.queryBuilder().where().eq("key", key).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据店铺查询菜品
     * @param id
     * @return
     */
    public List<SearchStoryBean> getSearchStoryBeansForShopId(int id){
        try {
            return foodIntegerDao.queryBuilder().where().eq("id",id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(SearchStoryBean food){
        try {
            foodIntegerDao.delete(food);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
