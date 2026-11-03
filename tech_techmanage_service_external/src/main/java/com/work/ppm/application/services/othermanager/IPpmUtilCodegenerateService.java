package com.work.ppm.application.services.othermanager;

import com.work.ppm.application.model.othermanager.PpmUtilCodegenerate;

/**
 * 流水号生成接口
 */
public interface IPpmUtilCodegenerateService {


    /**
     * 添加
     * @param code 代码
     * @param value 值
     * @return int
     */
    int addPpmUtilCodegenerate(String code,long value) ;

    /**
     * 查询详情
     * @param code 代码
     * @return PpmUtilCodegenerate
     */
    PpmUtilCodegenerate queryDetail(String code) ;

    /**
     * 更新
     * @param code 代码
     * @param value 值
     * @return Integer
     */
    Integer updatePpmUtilCodegenerate(String code, long value) ;

}
