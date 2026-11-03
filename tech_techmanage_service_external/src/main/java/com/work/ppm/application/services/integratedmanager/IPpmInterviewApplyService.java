package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewApplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmInterviewerInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmInterviewApply;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 外包面试申请
 */
public interface IPpmInterviewApplyService {

    /**
     * 分页
     * @param ppmInterviewApply 对象
     * @param pager
     * @return List<PpmInterviewApplyDto>
     */
    List<PpmInterviewApplyDto> queryPager(PpmInterviewApplyDto ppmInterviewApply,
                                          Pager pager, CommOperatorDoLoginDto user) ;

    /**
     * 草稿
     * @param apply 对象
     * @param ppmInterviewerInfo 对象
     * @return String
     */
    String daftPpmInterviewApply(PpmInterviewApply apply, PpmInterviewerInfoDto ppmInterviewerInfo);

    /**
     * 添加并发起流程
     * @param interviewerInfoDto 对象
     * @return String
     */
    String addPpmInterviewApply(PpmInterviewApply ppmInterviewApply, PpmInterviewerInfoDto interviewerInfoDto) ;


    /**
     * 面试打分详情
     * @param ppmInterviewApply 对象
     * @return PpmInterviewApplyDto
     */
    PpmInterviewApplyDto interviewScore(PpmInterviewApplyDto ppmInterviewApply) ;

    /**
     * 更新
     * @param ppmInterviewApply 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmInterviewApply(PpmInterviewApply ppmInterviewApply) ;

    /**
     * 删除，包括面试关联数据
     * @param ppmInterviewApply 对象
     * @return Integer
     */
    Integer deletePpmInterviewApply(PpmInterviewApply ppmInterviewApply) ;

    /**
     * 详情
     * @param ppmInterviewApplyDto 对象
     * @return PpmInterviewApply
     */
    PpmInterviewApply queryDetail(PpmInterviewApplyDto ppmInterviewApplyDto);
    /**
     * 面试打分
     * @param bps 对象
     * @param ivsList 集合
     * @return String
     */
    String interviewScore(BpsWorkFlowDto bps, List<PpmIntervieweeScoreinfo> ivsList);

    /**
     * 录入结果
     * @param ppmInterviewerInfo 对象
     * @return String
     */
    String resultEntry(PpmInterviewerInfoDto ppmInterviewerInfo);

    /**
     * 单价确认
     * @param ppmInterviewerInfoDto 对象
     * @return String
     */
    String priceConfirm(PpmInterviewerInfoDto ppmInterviewerInfoDto);

    /**
     * 一级部总经理室审批
     * @param dto 对象
     * @return String
     */
    String zhbConfirm(PpmInterviewerInfoDto dto);

    /**
     * 导出
     * @param ppmInterviewApplyDto 对象
     * @param user session
     * @return List<PpmInterviewApplyDto>
     */
    List<PpmInterviewApplyDto> exportExcel( PpmInterviewApplyDto ppmInterviewApplyDto,
                                           CommOperatorDoLoginDto user);

    /**
     * 面试通过数据同步到老应用
     */
    void newAndOldDataSysnc();

    /**
     * 非私有调用接口
     * @param eeList list
     */
    void archiveApproval(List<PpmIntervieweeInfo> eeList);

}
