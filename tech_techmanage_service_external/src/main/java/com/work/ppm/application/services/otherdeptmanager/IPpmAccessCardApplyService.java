package com.work.ppm.application.services.otherdeptmanager;

import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyDto;
import com.work.ppm.application.dto.otherdeptmanager.PpmAccessCardApplyInfoExportDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;

import java.util.List;

/**
  *  监保部台账领用申请表 接口描述
  */
public interface IPpmAccessCardApplyService {

    /**
     * 添加
     * @param ppmAccessCardApplyDto 对象
     * @return int
     */
    int addPpmAccessCardApply(PpmAccessCardApplyDto  ppmAccessCardApplyDto) ;

    /**
     * 查询详情
     * @param ppmAccessCardApplyDto 对象
     * @return PpmAccessCardApplyDto
     */
    PpmAccessCardApplyDto queryDetail(PpmAccessCardApplyDto ppmAccessCardApplyDto) ;
    /**
     * 通过流程编号 查询详情
     * @param instanceId 流程编号
     * @return PpmAccessCardApplyDto
     */
    PpmAccessCardApplyDto queryDetailForInstanceId(String instanceId) ;

    /**
     * 更新
     * @param ppmAccessCardApplyDto  对象
     * @return int
     */
    int updatePpmAccessCardApply(PpmAccessCardApplyDto ppmAccessCardApplyDto) ;

    /**
     * 删除
     * @param ppmAccessCardApplyDto 对象
     * @return int
     */
    int deletePpmAccessCardApply(PpmAccessCardApplyDto ppmAccessCardApplyDto) ;

    /**
     * 制卡发起流程
     * ->已发起
     * @param ppmAccessCardApplyDto 对象
     * @return String
     */
    String flowInit(PpmAccessCardApplyDto ppmAccessCardApplyDto) ;

    /**
     * 归还发起流程
     * ->已发起
     * @param ppmAccessCardApplyDto 对象
     * @return String
     */
    String returnFlowInit(PpmAccessCardApplyDto ppmAccessCardApplyDto) ;


    /**
     * 金融科技部一级部总经理室审批
     * ->处理中
     * @param ppmAccessCardApplyDto 对象
     * @return String
     */
    String flowGmApproval(PpmAccessCardApplyDto ppmAccessCardApplyDto);

    /**
     * 监保部单人审批 - 制卡
     * ->处理中
     * @param ppmAccessCardApplyDto 对象
     * @return String
     */
    String flowMonitorApproval(PpmAccessCardApplyDto ppmAccessCardApplyDto);

    /**
     * 监保部多人审批
     * ->已归档
     * @param ppmAccessCardApplyDto 对象
     * @return String
     */
    String flowFinish(PpmAccessCardApplyDto ppmAccessCardApplyDto);
    /**
     * 监保部审批 生成附件docx
     * @param accessCardApplyDto 对象
     * @return String
     */
    String saveFlowCaseDocx(PpmAccessCardApplyDto accessCardApplyDto);

    /**
     * 监保部审批 生成附件docx
     * @param accessCardApplyDto 对象
     * @return String
     */
    String saveFlowCaseXlsx(PpmAccessCardApplyDto accessCardApplyDto);

    /**
     * 导出
     * @param accessCardApplyDto 导出对象
     * @param user 用户对象
     * @return list
     */
    List<PpmAccessCardApplyInfoExportDto> exportExcel(PpmAccessCardApplyDto accessCardApplyDto, CommOperatorDoLoginDto user);


}
