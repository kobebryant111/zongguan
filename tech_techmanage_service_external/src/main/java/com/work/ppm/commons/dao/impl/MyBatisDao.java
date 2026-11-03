package com.work.ppm.commons.dao.impl;

import com.work.ppm.commons.mybatis.plugin.PageHelper;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.dao.IMyBatisDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * 数据接口
 */
@Repository("myBatisDao")
public class MyBatisDao extends SqlSessionDaoSupport implements IMyBatisDao {
    /**
     * setSqlSessionFactory
     */
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    @Override
    public <T> T selectClass(String nameSpace, Object t, String uniqueName)  {
        return this.getSqlSession().selectOne(nameSpace + POINT + uniqueName, t);
    }

    @Override
    public Object selectObject(String nameSpace, Object t, String uniqueName)  {
        return this.getSqlSession().selectOne(nameSpace + POINT + uniqueName, t);
    }

    @Override
    public <T> List<T> selectListT(String nameSpace, Object t, String uniqueName)  {
        return this.getSqlSession().selectList(nameSpace + POINT + uniqueName, t);
    }

    @Override
    public List<Object> selectList(String nameSpace, Object t, String uniqueName) {
        return this.getSqlSession().selectList(nameSpace + POINT + uniqueName, t);
    }

    @Override
    public <T> Integer executionPojo(String nameSpace, T t, SQL sql, String uniqueName)  {
        switch (sql) {
            case INSTER:
                return this.getSqlSession().insert(nameSpace + POINT + uniqueName, t);
            case DELETE:
                return this.getSqlSession().delete(nameSpace + POINT + uniqueName, t);
            case UPDATE:
                return this.getSqlSession().update(nameSpace + POINT + uniqueName, t);
                default:
        }
        return -1;
    }

    @Override
    public Integer executionMap(String nameSpace, Map<String,Object> map, SQL sql, String uniqueName)  {
        switch (sql) {
            case INSTER:
                return this.getSqlSession().insert(nameSpace + POINT + uniqueName, map);
            case DELETE:
                return this.getSqlSession().delete(nameSpace + POINT + uniqueName, map);
            case UPDATE:
                return this.getSqlSession().update(nameSpace + POINT + uniqueName, map);
            default:
        }
        return -1;
    }

    @Override
    public void selectPager(String nameSpace, Map<String, Object> map, Pager pager, String uniqueName){
//        map.put("size", pager.getSize());
//        map.put("start", pager.getStart());
//        List<?> list = this.selectList(nameSpace ,map,uniqueName);
//        Long count = this.selectCount(nameSpace,map, uniqueName + COUNT);
//        pager.setList(list);
//        pager.setTotal(count.intValue());
//        pager.getComputePageCount();
        PageHelper.startPage( pager);
        this.selectList(nameSpace ,map,uniqueName);
        Pager localPage = PageHelper.endPage();
        pager.setList(localPage.getList());
        pager.setTotal(localPage.getTotal());
        pager.setPagerCount(localPage.getPagerCount());
    }

    @Override
    public Long selectCount(String nameSpace, Map<String, Object> map, String uniqueName)  {
        return this.getSqlSession().selectOne(nameSpace + POINT + uniqueName, map);
    }
}
