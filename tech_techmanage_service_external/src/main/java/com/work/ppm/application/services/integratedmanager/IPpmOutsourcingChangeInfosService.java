package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.model.integratedmanager.PpmOutsourcingChangeInfos;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 *  员工信息变更详细
 */
public interface IPpmOutsourcingChangeInfosService {

    /**
     * 分页
     * @param ppmOutsourcingChangeInfos 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingChangeInfos 对象
     * @return int
     */
     @Deprecated
    int addPpmOutsourcingChangeInfos(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) ;

    /**
     * 查询详情
     * @param ppmOutsourcingChangeInfos 对象
     * @return PpmOutsourcingChangeInfos
     */
    @Deprecated
    PpmOutsourcingChangeInfos queryDetail(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) ;

    /**
     * 更新
     * @param ppmOutsourcingChangeInfos 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmOutsourcingChangeInfos(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) ;

    /**
     * 删除
     * @param ppmOutsourcingChangeInfos 对象
     * @return Integer
     */
    Integer deletePpmOutsourcingChangeInfos(PpmOutsourcingChangeInfos ppmOutsourcingChangeInfos) ;

    /**
     * addPpmOutsourcingChangeInfos 添加信息变更详细记录
     * @param eeList
     * @param instanceid
     * @return int
     */
    int addPpmOutsourcingChangeInfos(List<PpmOutsourcingChangeInfos> eeList, String instanceid);

    /**
     * 查询子数据
     * @param instanceid 流程id
     * @return  List<PpmOutsourcingChangeInfos>
     */
    List<PpmOutsourcingChangeInfos> queryLists(String instanceid) ;

    /**
     * deleteByInstanceid 删除子表数据
     * @param info 对象
     * @return int
     */
    int deleteByInstanceid(PpmOutsourcingChangeInfos info);
}
