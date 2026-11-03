package com.work.ppm.commons.dao;

import com.work.ppm.commons.pager.Pager;

import java.util.List;
import java.util.Map;

/**
 * 数据接口
 */
public interface IMyBatisDao {
    /**
     * POINT
     */
    static String POINT = ".";

    /**
     *  查询单个数据
     * @param nameSpace 空间名称 唯一
     * @param t 对象
     * @param uniqueName 唯一名称
     * @return T
     */
    <T> T selectClass(String nameSpace,Object t, String uniqueName) ;

    /**
     * 查询单个对象
     * @param nameSpace 空间名称 唯一
     * @param t 对象
     * @param uniqueName 唯一名称
     * @return Object
     */
    @Deprecated
    Object selectObject(String nameSpace,Object t, String uniqueName);

    /**
     * 查询多个泛型集合
     *
     * @param nameSpace 空间名称 唯一
     * @param t 对象
     * @param uniqueName  唯一名称
     * @return List<T>
     */
    <T> List<T> selectListT(String nameSpace, Object t, String uniqueName);

    /**
     * 查询多个集合
     * @param nameSpace 空间名称 唯一
     * @param t 对象
     * @param uniqueName 唯一名称
     * @return List<Object>
     */
    @Deprecated
    List<Object> selectList(String nameSpace, Object t, String uniqueName);

    /**
     * 更新和删除、插入操作 - 泛型
     * @param nameSpace 空间名称 唯一
     * @param t 对象
     * @param uniqueName 唯一名称
     * @return Integer
     */
    <T> Integer executionPojo(String nameSpace, T t, SQL sql, String uniqueName);

    /**
     * 更新和删除、插入操作 - Map
     * @param nameSpace 空间名称 唯一
     * @param map map
     * @param sql 枚举
     * @param uniqueName  唯一名称
     * @return Integer
     */
    Integer executionMap(String nameSpace, Map<String,Object> map, SQL sql, String uniqueName) ;

    /**
     * 分页
     * @param nameSpace 空间名称 唯一
     * @param map map
     * @param pager 分页对象
     * @param uniqueName  唯一名称
     */
    void selectPager(String nameSpace, Map<String,Object> map, Pager pager, String uniqueName) ;

    /**
     * 查询数字
     * @param nameSpace 空间名称 唯一
     * @param map map
     * @param uniqueName 唯一名称
     * @return Long
     */
    Long selectCount(String nameSpace, Map<String,Object> map, String uniqueName);


    /**
     * 枚举
     */
    enum SQL {
        /**
         * 添加
         */
        INSTER,
        /**
         * 更新
         */
        UPDATE,
        /**
         * 删除
         */
        DELETE;
    }
}
