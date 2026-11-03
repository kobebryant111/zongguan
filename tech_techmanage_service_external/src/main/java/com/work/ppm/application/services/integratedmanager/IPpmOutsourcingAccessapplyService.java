package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.bankmamager.PpmOutSorceInterviewDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingAccessapplyInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingAccessapply;

import java.util.Map;

/**
 * 新外包准入流程
 *
 */
public interface IPpmOutsourcingAccessapplyService {
    /**
     * 添加
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @param bps                          对象
     * @return PpmOutsourcingAccessapplyDto
     */
    PpmOutsourcingAccessapplyDto add(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto, BpsWorkFlowDto bps) throws Exception;

    /**
     * 查询详情
     *
     * @param ppmOutsourcingAccessapply 对象
     * @return PpmOutsourcingAccessapplyDto
     */
    PpmOutsourcingAccessapplyDto queryDetail(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapply);

    /**
     * 更新
     *
     * @param ppmOutsourcingAccessapply 对象
     * @return Integer
     */
    Integer updatePpmOutsourcingAccessapply(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapply);

    /**
     * 删除
     *
     * @param ppmOutsourcingAccessapply 对象
     * @return Integer
     */
    Integer deletePpmOutsourcingAccessapply(PpmOutsourcingAccessapply ppmOutsourcingAccessapply);

    /**
     * 流转
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @param bps                          对象
     * @return String
     */
    String liuzhuan(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto, BpsWorkFlowDto bps) throws Exception;

    /**
     * 二级部经理审批
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @param bps                          对象
     * @return String
     */
    String twoManagerAudit(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto, BpsWorkFlowDto bps) throws Exception;

    /**
     * 人事审批
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @param bps                          对象
     * @return String
     */
    String rsAudit(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto, BpsWorkFlowDto bps) throws Exception;

    /**
     * 风控征信审批
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @param bps                          对象
     * @return String
     */
    String fkzxAudit(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto, BpsWorkFlowDto bps) throws Exception;

    /**
     * 创建joid
     *
     * @return PpmOutsourcingAccessapplyDto
     */
    PpmOutsourcingAccessapplyDto createJoid();

    /**
     * 分控排查
     *
     * @param ppmOutsourcingAccessapplyDto 对象
     * @return PpmOutsourcingAccessapplyDto
     */
    PpmOutsourcingAccessapplyDto getWindControl(PpmOutsourcingAccessapplyDto ppmOutsourcingAccessapplyDto);

    /**
     * svn申请审批
     *
     * @param bps 对象
     * @return String
     */
    String svnApprove(BpsWorkFlowDto bps);

    /**
     * queryDetailExt
     *
     * @param ppmOutsourcingAccessapplyInfo
     * @return PpmOutsourcingAccessapplyInfoDto
     */
    PpmOutsourcingAccessapplyInfoDto queryDetailExt(PpmOutsourcingAccessapplyInfoDto ppmOutsourcingAccessapplyInfo);

    /**
     * 是否重复入职
     *
     * @param certifNumber 身份证
     * @return PpmOutsourcingAccessapplyDto
     */
    PpmOutsourcingAccessapplyDto getCheckedIdCard(String certifNumber, String userid);


    String selJobIdRepart(String jobId);

    /**
     * 是否重复申请入职
     *
     * @param certifNumber 身份证
     * @return PpmOutsourcingAccessapplyDto
     */
    PpmOutsourcingAccessapplyDto getCheckedApply(String certifNumber, String userid);

    /**
     * 获取公安信息
     *
     * @param name   名称
     * @param idCard 身份证号码
     * @return PpmOutsourcingAccessapplyDto
     */
    PpmOutsourcingAccessapplyDto getCheckedSecurity(String name, String idCard);

    /*
     * 通过工号查询对应准入流程
     *
     * @param jobId   名称
     * @return PpmOutsourcingAccessapplyDto

    PpmOutsourcingAccessapplyDto queryInstanceIdForJobId(String jobId);*/


    /**
     * 取卡片信息
     *
     * @param accessapply
     * @return
     */
    Map<String, String> getCardInfo(PpmOutsourcingAccessapplyDto accessapply);

    /**
     * 查询门禁编码同日期存在的数量
     *
     * @param prex
     * @return
     */
    long countEntranceCard(String prex);

    /**
     * 获取评价积分
     * @param ppmOutSorceInterviewDto
     * @return
     */
    String getScore(PpmOutSorceInterviewDto ppmOutSorceInterviewDto);

}
