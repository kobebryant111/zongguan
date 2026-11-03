package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.model.othermanager.EosUniqueTable;

/**
 * 主键生成表
 */
public interface IEosUniqueTableService {
    /**
     * 添加
     * @param eosUniqueTable 对象
     * @return int
     */
    int addEosUniqueTable(EosUniqueTable eosUniqueTable) ;

    /**
     * 查询详情
     * @param eosUniqueTable 对象
     * @return EosUniqueTable
     */
    EosUniqueTable queryDetail(EosUniqueTable eosUniqueTable) ;

    /**
     * 更新
     * @param eosUniqueTable 对象
     * @return Integer
     */
    Integer updateEosUniqueTable(EosUniqueTable eosUniqueTable) ;

    /**
     * 获取表序列号
     * 表名有参数决定
     * @param tableName 表名
     * @param id id
     * @return long
     */
    long  getTableSequence(String tableName,String id);

    /**
     * 查询主键是否重复
     * @param tableName 表名
     * @param fieldId 字段名称
     * @param id 主键
     * @return long
     */
    long getCheckedSequence(String tableName,String fieldId,String id);
}
