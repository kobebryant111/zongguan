package com.tansun.wf.extend.db;

import java.util.List;

/**
 * 工作流基础mapper类
 * 如果使用调用其中的类，mapper.xml 需要实现 insert,bulkInsert,delete,update
 * findList,selectById
 * @param <T>
 */
public interface WfBaseMapper<T> {

    /**
     * 新增
     *
     */
    int insert(T entity);

    /**
     * 批量新增
     *
     */
    int bulkInsert(List<T> list);

    /**
     * 根据条件删除
     */
    int delete(T entity);

    /**
     * 根据id删除
     */
    int deleteByIds(List<String> ids);

    /**
     * 根据主键修改
     */
    int update(T entity);

    /**
     * 查询列表
     */
    List<T> findList(WfBaseQuery query);

    /**
      * 根据主键查询
     * @param id
     * @return
     */
     T selectById(String id);
      


}

