package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.onlinemanager.OnlineInfoDto;
import com.work.ppm.application.dto.onlinemanager.PpmProjectOnlinefjhDto;
import com.work.ppm.application.dto.projectmanager.DemandBackDto;
import com.work.ppm.application.dto.projectmanager.PpmProjectBuildSoftTreeDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmProjectNewonlineDto;

import java.util.List;
import java.util.Map;

/**
  * 项目上线接口类
  */
public interface IPpmProjectNewonlineService {

    /**
     * 分页
     * @param ppmProjectNewonlineDto 对象
     * @param pager 分页对象
     */
    void queryOnlinePager(PpmProjectNewonlineDto ppmProjectNewonlineDto, Pager pager) ;

    /**
     * 分页
     * @param ppmProjectNewonlineDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmProjectNewonlineDto ppmProjectNewonlineDto, Pager pager) ;

    /**
     * 添加
     * @param ppmProjectNewonlineDto 对象
     * @return int
     */
    @Deprecated
    int addPpmProjectNewonline(PpmProjectNewonlineDto ppmProjectNewonlineDto) ;

    /**
     * 查询详情
     * @param ppmProjectNewonlineDto 对象
     * @return PpmProjectNewonlineDto
     */
    PpmProjectNewonlineDto queryDetail(PpmProjectNewonlineDto ppmProjectNewonlineDto) ;

    /**
     * 更新
     * @param ppmProjectNewonlineDto  对象
     * @return int
     */
    int updatePpmProjectNewonline(PpmProjectNewonlineDto ppmProjectNewonlineDto) ;

    /**
     * 获取项目列表
     * @param ppmProjectNewonlineDto
     * @return List<PpmProjectBuildSoftTreeDto>
     */
    List<PpmProjectBuildSoftTreeDto> getPpmProjectBuildSoft(PpmProjectNewonlineDto ppmProjectNewonlineDto);

    /**
     * 发起流程
     * @param onlineInfo
     * @return int
     */
    Map<String, Object> addPpmProjectNewonlineFlow(OnlineInfoDto onlineInfo);

    /**
     * 撤回流程
     * @param demandBackDto
     * @return String
     */
    String recallPpmProjectNewonlineFlow(DemandBackDto demandBackDto);

    /**
     * 分行科技二级部经理审批
     * @param onlinefjh 业务流程对象
     * @param bps 流程对象
     * @return String
     */
    String approveFlowByEJBManagers(PpmProjectOnlinefjhDto onlinefjh, BpsWorkFlowDto bps);

    /**
     * 分行业务总经理审批
     * @param onlinefjh 业务流程对象
     * @param bps 流程对象
     * @return String
     */
    String approveFlowByBusZJL(PpmProjectOnlinefjhDto onlinefjh, BpsWorkFlowDto bps);

    /**
     * 分行业务科技总经理审批
     * @param onlinefjh 业务流程对象
     * @param bps 流程对象
     * @return String
     */
    String approveFlowByTechZJL(PpmProjectOnlinefjhDto onlinefjh, BpsWorkFlowDto bps);

}
