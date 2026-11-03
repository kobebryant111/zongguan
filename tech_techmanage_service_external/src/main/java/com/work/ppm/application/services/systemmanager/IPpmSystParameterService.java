package com.work.ppm.application.services.systemmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.model.systemmanager.PpmSystParameter;

/**
 * 通用参数配置接口
 */
public interface IPpmSystParameterService {

    /**
     * 分页
     * @param ppmSystParameter 对象
     * @param pager
     */
    @Deprecated
    void queryPager(PpmSystParameter ppmSystParameter, Pager pager) ;

    /**
     * 添加
     * @param ppmSystParameter 对象
     * @return int
     */
     @Deprecated
    int addPpmSystParameter(PpmSystParameter ppmSystParameter) ;

    /**
     * 查询详情
     * @param key key
     * @return PpmSystParameter
     */
    PpmSystParameter queryDetail(String key) ;

    /**
     * 更新
     * @param ppmSystParameter 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmSystParameter(PpmSystParameter ppmSystParameter) ;

    /**
     * 删除
     * @param ppmSystParameter 对象
     * @return Integer
     */
    @Deprecated
    Integer deletePpmSystParameter(PpmSystParameter ppmSystParameter) ;

    /**
     * 根据key值获取value值
     * @param syaparamkey 对象
     * @return int
     */
    String getValueByKey(String syaparamkey);
}
