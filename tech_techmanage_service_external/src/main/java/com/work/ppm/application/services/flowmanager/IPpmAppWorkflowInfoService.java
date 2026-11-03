package com.work.ppm.application.services.flowmanager;

import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import java.util.List;

/**
  *  对接手机平台信息 接口描述
  */
public interface IPpmAppWorkflowInfoService {

    /**
     * 添加
     * @param ppmAppWorkflowInfoDto 对象
     * @return int
     */
    int addPpmAppWorkflowInfo(PpmAppWorkflowInfoDto  ppmAppWorkflowInfoDto) ;

    /*
     * 查询详情
     * @param ppmAppWorkflowInfoDto 对象
     * @return PpmAppWorkflowInfoDto

    PpmAppWorkflowInfoDto queryDetail(PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto) ;*/

    /**
     * 查询详情
     * @param taskId 对象
     * @return PpmAppWorkflowInfoDto
     */
    PpmAppWorkflowInfoDto queryDetailForTaskId(String taskId) ;

    /**
     * 查询详情集合
     * @param ppmAppWorkflowInfoDto 对象
     * @return List<PpmAppWorkflowInfoDto>
     */
    List<PpmAppWorkflowInfoDto> queryDetailList(PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto) ;

    /**
     * 查询推送失败的集合
     * @param pushState 对象
     * @param instanceId 对象
     * @return List<PpmAppWorkflowInfoDto>
     */
    PpmAppWorkflowInfoDto queryDetailForPushState(String pushState,String instanceId) ;


    /**
     * 更新
     * @param ppmAppWorkflowInfoDto  对象
     * @return int
     */
    int updatePpmAppWorkflowInfo(PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto) ;


}
