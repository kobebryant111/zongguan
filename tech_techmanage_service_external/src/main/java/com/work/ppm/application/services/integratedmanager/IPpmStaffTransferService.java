package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmStaffTransferDto;

/**
  *  人员移交表 接口描述
  */
public interface IPpmStaffTransferService {

    /**
     * 添加
     * @param ppmStaffTransferDto 对象
     * @return int
     */
    int addPpmStaffTransfer(PpmStaffTransferDto  ppmStaffTransferDto) ;

    /**
     * 查询详情
     * @param ppmStaffTransferDto 对象
     * @return PpmStaffTransferDto
     */
    PpmStaffTransferDto queryDetail(PpmStaffTransferDto ppmStaffTransferDto) ;

    /**
     * 更新
     * @param ppmStaffTransferDto  对象
     * @return int
     */
    int updatePpmStaffTransfer(PpmStaffTransferDto ppmStaffTransferDto) ;

    /**
     * 删除
     * @param ppmStaffTransferDto 对象
     * @return int
     */
    int deletePpmStaffTransfer(PpmStaffTransferDto ppmStaffTransferDto) ;


    /* 流程 */
    /*
     * 草稿 未发起
     * 已创建
     * @param ppmStaffTransferDto 对象
     * @return String

    String managerCreated(PpmStaffTransferDto ppmStaffTransferDto);*/

    /**
     * 项目经理添加并发起流程
     * 已发起
     * @param ppmStaffTransferDto 对象
     * @return String
     */
    String managerInit(PpmStaffTransferDto ppmStaffTransferDto) ;

    /**
     * 二级部经理审批
     * 处理中
     * @param ppmStaffTransferDto 对象
     * @return String
     */
    String deptManagerApproval(PpmStaffTransferDto ppmStaffTransferDto);

    /**
     * 移交后二级部经理审批
     * 处理中
     * @param ppmStaffTransferDto 对象
     * @return String
     */
    String deptManagerApprovalTransfer(PpmStaffTransferDto ppmStaffTransferDto);


    /**
     * 移交后项目经理审批
     * 处理中
     * @param ppmEarlyCourseApplyDto 对象
     * @return String
     */
    String managerApprovalTransfer(PpmStaffTransferDto ppmEarlyCourseApplyDto);


}
