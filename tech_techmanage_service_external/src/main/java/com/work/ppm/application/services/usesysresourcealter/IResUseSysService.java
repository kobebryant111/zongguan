package com.work.ppm.application.services.usesysresourcealter;

import com.work.ppm.application.dto.resourceplat.FlowDto;
import com.work.ppm.application.dto.usesysresourcealter.ResUseSysDto;
import com.work.ppm.application.model.usesysresourcealter.ResUseSys;
import com.work.ppm.application.model.usesysresourcealter.ResUseSysOther;
import com.work.ppm.commons.pager.Pager;

import java.util.List;
import java.util.Map;

/**
 * 应用系统资源变更流程
 */
public interface IResUseSysService {

    /**
     * 分页
     *
     * @param resUseSysDto 对象
     * @param pager       分页对象
     */
    void queryPager(ResUseSysDto resUseSysDto, Pager pager);

    /**
     * 获取应用系统数据
     * @param appNo 应用系统id
     * @return List
     */
    List<Map<String,Object>> queryUseSysList(String appNo, String ids) ;

    /**
     * 获取应用系统数据
     * @param appNo 应用系统id
     * @return 系统管理员A岗
     */
    String querySystemUser(String appNo) ;

    /**
     * 分页
     * @param resUseSysDto
     * @return List
     */
    List<Map<String,Object>> queryUseSysPageList(ResUseSysDto resUseSysDto, Pager pager) ;


    /**
     * 发起应用系统资源变更申请
     * @param resUseSysDto
     * @return processInfo
     */
    String submitResUseSysData(ResUseSysDto resUseSysDto);


    /**
     * 保存应用系统变更数据
     * @param resUseSysDto
     */
    void saveUseSysData(ResUseSysDto resUseSysDto, String status);

    /**
     * 获取应用系统资源变更数据
     * @param resUseSys
     * @return ResUseSysDto
     */
    List<ResUseSysOther> queryResUseSysAlterData(ResUseSys resUseSys);

    /**
     * 删除
     *
     * @param resUseSysOther
     */
    void delUseSysAlterData(ResUseSysOther resUseSysOther);

    /**
     * 提交工作项
     * @param flowDto
     *
     * @return true/false
     */
    boolean submitWorkItem(FlowDto flowDto);

    /**
     * 重新提交流程
     * @param flowDto
     * @param resUseSysDto
     * @return true/false
     */
    boolean reSubmitWorkFlow(FlowDto flowDto, ResUseSysDto resUseSysDto);

    /**
     * 流程撤回时回滚数据
     * @param resUseSysOther
     * @return int
     */
    Integer updateUseSysFlowData(ResUseSysOther resUseSysOther);

    /**
     * 生成流水号
     * @return 流水号
     */
    String getFlowId() ;
}
