package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmEarlyCourseApplyDto;

/**
 * 提前入场申请
 */
public interface IPpmEarlyCourseApplyService{

    /**
     * 详情
     * @param ppmEarlyCourseApplyDto 对象
     * @return PpmInterviewApply
     */
    PpmEarlyCourseApplyDto queryDetail(PpmEarlyCourseApplyDto ppmEarlyCourseApplyDto);

    /**
     * 新增
     * @param earlyCourseApplyDto 对象
     * @return Integer
     */
    Integer insertPpmEarlyCourseApply(PpmEarlyCourseApplyDto earlyCourseApplyDto) ;

    /**
     * 更新
     * @param earlyCourseApplyDto 对象
     * @return Integer
     */
    Integer updatePpmEarlyCourseApply(PpmEarlyCourseApplyDto earlyCourseApplyDto) ;

    /**
     * 删除，包括提前入场申请关联数据
     * @param earlyCourseApply 对象
     * @return Integer
     */
    Integer deletePpmEarlyCourseApply(PpmEarlyCourseApplyDto earlyCourseApply) ;

    /* 流程 */
    /**
     * 草稿 未发起
     * 已创建
     * @param ppmEarlyCourseApplyDto 对象
     * @return String
    String managerCreated(PpmEarlyCourseApplyDto ppmEarlyCourseApplyDto);*/

    /**
     * 项目经理添加并发起流程
     * 已发起
     * @param courseApplyDto 对象
     * @return String
     */
    String managerInit(PpmEarlyCourseApplyDto courseApplyDto) ;

    /**
     * 二级部经理审批
     * 处理中
     * @param ppmEarlyCourseApplyDto 对象
     * @return String
     */
    String departmentHeadApproval(PpmEarlyCourseApplyDto ppmEarlyCourseApplyDto);

    /**
     * 一级部总经理室审批
     * 处理中 / 已结束
     * @param ppmEarlyCourseApplyDto 对象
     * @return String
     */
    String generalManagerApproval(PpmEarlyCourseApplyDto ppmEarlyCourseApplyDto);

}
