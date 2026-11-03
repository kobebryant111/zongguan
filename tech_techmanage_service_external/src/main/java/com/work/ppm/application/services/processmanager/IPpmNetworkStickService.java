package com.work.ppm.application.services.processmanager;

import com.work.ppm.application.dto.processmanager.PpmNetworkStickDto;
import com.work.ppm.application.dto.processmanager.PpmNingGuanFlowQueryDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
  * 宁管部网点设备变更维护
  */
public interface IPpmNetworkStickService {

    /**
     * 分页
     * @param ppmNetworkStickDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmNetworkStickDto ppmNetworkStickDto, Pager pager) ;

    /**
     * 添加
     * @param ppmNetworkStickDto 对象
     * @return int
     */
    int addPpmNetworkStick(PpmNetworkStickDto ppmNetworkStickDto) ;

    /**
     * 查询详情
     * @param ppmNetworkStickDto 对象
     * @return PpmNetworkStickDto
     */
    PpmNetworkStickDto queryDetail(PpmNetworkStickDto ppmNetworkStickDto) ;

    /**
     * 更新
     * @param ppmNetworkStickDto  对象
     * @return String 下个节点信息
     */
    String initiatePpmNetworkStick(PpmNetworkStickDto ppmNetworkStickDto) ;

    /**
     * 宁管部二级部经理审批
     * @param ppmNetworkStickDto 对象
     * @return String 下个节点信息
     */
    String ministryManagerExamination(PpmNetworkStickDto ppmNetworkStickDto);

    /**
     * 处理人审批
     * @param ppmNetworkStickDto 对象
     * @return String 下个节点信息
     */
    String dealingExamination(PpmNetworkStickDto ppmNetworkStickDto);

    /**
     * 监控变更确认
     * @param ppmNetworkStickDto 对象
     * @return String 下个节点信息
     */
    String monitorChangeConfirm(PpmNetworkStickDto ppmNetworkStickDto);

    /**
     * 出去库确认
     * @param ppmNetworkStickDto 对象
     * @return String 下个节点信息
     */
    String skinprinterLibConfirm(PpmNetworkStickDto ppmNetworkStickDto);

    /**
     * cmdb录入确认
     * @param ppmNetworkStickDto 对象
     * @return String 下个节点信息
     */
    String cmdbEntryConfirm(PpmNetworkStickDto ppmNetworkStickDto);

    /**
     *获取当前登录人信息
     * @param userid 工号
     * @return PpmNetworkStickDto
     */
    PpmNetworkStickDto getCurrentInfo(String userid);

    /**
     * 保存草稿
     * @param ppmNetworkStickDto 对象
     * @return string 流程下个节点信息
     */
    String draft(PpmNetworkStickDto ppmNetworkStickDto);

    /**
     * 导出excel
     * @param ppmNingGuanFlowQueryDto 对象
     * @return list
     */
    List<PpmNetworkStickDto> exportExcel(PpmNingGuanFlowQueryDto ppmNingGuanFlowQueryDto);

    /**
     * 导出excel
     * @param ppmNetworkStickDto 对象
     * @return list
     */
    List<PpmNetworkStickDto> exportExcel(PpmNetworkStickDto ppmNetworkStickDto);
}
