package com.work.ppm.application.services.projectmanager;

import com.work.ppm.application.dto.projectmanager.DemandBackDto;
import com.work.ppm.application.dto.projectmanager.DemandInfoDto;
import com.work.ppm.application.dto.projectmanager.PpmDemandnewinfoDto;
import com.work.ppm.application.dto.systemmanager.CommEmployeeDto;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

/**
 * 奋进号业务意向实现接口
 */
public interface IPpmDemandnewinfoService {

    /**
     * 分页
     * @param ppmDemandnewinfoDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmDemandnewinfoDto ppmDemandnewinfoDto, Pager pager) ;

    /**
     * 添加
     * @param ppmDemandnewinfoDto 对象
     * @return int
     */
     @Deprecated
    int addPpmDemandnewinfo(PpmDemandnewinfoDto ppmDemandnewinfoDto) ;

    /**
     * 查询详情
     * @param ppmDemandnewinfoDto 对象
     * @return PpmDemandnewinfoDto
     */
    PpmDemandnewinfoDto queryDetail(PpmDemandnewinfoDto ppmDemandnewinfoDto) ;

    /**
     * 更新
     * @param ppmDemandnewinfoDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmDemandnewinfo(PpmDemandnewinfoDto ppmDemandnewinfoDto) ;

    /**
     * 删除
     * @param ppmDemandnewinfoDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmDemandnewinfo(PpmDemandnewinfoDto ppmDemandnewinfoDto) ;

    /**
     * 创建业务意向流程-奋进号
     * @param demandInfoDto 对象
     * @return 意向编号
     */
    String createDemandneFlow(DemandInfoDto demandInfoDto);

    /**
     * 研发二级部经理意向初审-奋进号
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult preliminaryReviewDemandne(PpmDemandnewinfoDto demandInfoDto);

    /**
     * 业务总经理意向审批-奋进号
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult busGeneralMsgApprove(PpmDemandnewinfoDto demandInfoDto);
    /**
     * 分行合规总经理审批
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult complianceGeneralMsgApprove(PpmDemandnewinfoDto demandInfoDto);

    /**
     * 分行人力资源部总经理审批-奋进号
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult humanResourcesApprove(PpmDemandnewinfoDto demandInfoDto);
    /**
     * 科技总经理审批
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult techGMApprove(PpmDemandnewinfoDto demandInfoDto);
    /**
     * 分行分管行长审批-奋进号
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult branchBankApprove(PpmDemandnewinfoDto demandInfoDto);
    /**
     * 分行管理部审核-奋进号
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult branchMgtApprove(PpmDemandnewinfoDto demandInfoDto);
    /**
     * 架构部三新审核-奋进号
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult frameworkDeptApprove(PpmDemandnewinfoDto demandInfoDto);
    /**
     * 架构部分管副总审批-奋进号
     * @param demandInfoDto 对象
     * @return ApiResult
     */
    ApiResult frameworkbrandeputyApprove(PpmDemandnewinfoDto demandInfoDto);
    /**
     * 删除流程
     * @param demandBackDto 对象
     * @return ApiResult
     */
    ApiResult removeDemand(DemandBackDto demandBackDto);

    /**
     * 计算预算信息的工作量
     * @param demandInfoDto 对象
     * @param info 对象
     * @return PpmDemandnewinfoDto
     */
    PpmDemandnewinfoDto budgetWorkload(DemandInfoDto demandInfoDto, PpmDemandnewinfoDto info);

    /**
     * 查询分行主持部经理
     * @param depno 部门编号
     * @return CommEmployeeDto
     */
    CommEmployeeDto getBranchGeneralManager(String depno);

    /**
     * 根据projectno获取需求
     * @param projectno
     * @return PpmDemandApplyDto
     */
    PpmDemandnewinfoDto getdemandByProjectno(String projectno);

    /**
     * 根据demandno获取需求
     * @param demandno
     * @return PpmDemandApplyDto
     */
    PpmDemandnewinfoDto getdemandByDemandno(String demandno);

    /**
     * 根据奋进号id查询流程id
     * @param issueid 奋进号id
     * @return PpmDemandnewinfoDto
     */
    PpmDemandnewinfoDto queryInstanceidByIssueId(String issueid);

    /**
     * 手机端详情
     * @param ppmDemandnewinfoDto 对象
     * @return PpmDemandnewinfoDto
     */
    PpmDemandnewinfoDto queryMobileDetail(PpmDemandnewinfoDto ppmDemandnewinfoDto);
}
