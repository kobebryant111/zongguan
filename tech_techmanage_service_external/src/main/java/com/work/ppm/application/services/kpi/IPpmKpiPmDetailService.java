package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.ComprehensiveAbilityEvalDto;
import com.work.ppm.application.dto.kpi.PostEvaluationEvalDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailInfoDto;
import com.work.ppm.application.model.kpi.PpmKpiPmDetail;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiPmDetailDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
  * 接口描述
  */
public interface IPpmKpiPmDetailService {

    /**
     * 分页
     * @param ppmKpiPmDetailDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiPmDetailDto ppmKpiPmDetailDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiPmDetailDto 对象
     * @return int
     */
    int addPpmKpiPmDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) ;

    /**
     * 查询详情
     * @param ppmKpiPmDetailDto 对象
     * @return PpmKpiPmDetailDto
     */
    PpmKpiPmDetailDto queryDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) ;

    /**
     * 更新
     * @param ppmKpiPmDetailDto  对象
     * @return int
     */
    int updatePpmKpiPmDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) ;

    /**
     * 删除
     * @param ppmKpiPmDetailDto 对象
     * @return int
     */
    int deletePpmKpiPmDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto) ;
    /**
     * 插入员工考核指标
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int insertPpmKpiPmDetailEmp(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 插入经理考核指标
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int insertPpmKpiPmDetailManagers(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 插入主持工作考核数据
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int insertPpmKpiPmDetailWorkManager(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 根据条件查询下面考核项的条数
     * @param param
     * @return long
     */
    long queryKpiPmDetails(Map param);

    /**
     * 根据条件查询详细项数据
     * @param ppmKpiPmDetailInfoDto
     * @return List<PpmKpiPmDetailInfoDto>
     */
    List<PpmKpiPmDetailInfoDto> getPpmKpiPmDetails(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto);

    /**
     * kpi和能力提升录入，审批时添加
     * @param ppmKpiPmDetailDto
     * @param userid
     * @return int
     */
    int addPpmKpiPmDetailItemApprove(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid);


    /**
     * kpi和能力提升录入，制定时添加
     * @param ppmKpiPmDetailDto
     * @param userid
     * @return int
     */
    int addPpmKpiPmDetailItemFormulate(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid);


    /**
     * 考核审批修改指标项
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int updatePpmKpiPmDetailApprove(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 考核录入修改指标项
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int updatePpmKpiPmDetailFormulate(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 查询需要处理考核项集合数据
     * @param param
     * @return List<PpmKpiPmDetail>
     */
    List<PpmKpiPmDetail> queryPpmKpiPmDetails(Map param);

    /**
     * 状态变更的时候更新数据（自评，评价，半年度自评等）代码内部参数传递
     * @param ppmKpiPmDetail
     * @return int
     */
    int updatePpmKpiPmDetail(PpmKpiPmDetail ppmKpiPmDetail);

    /**
     * 根据ID查询详情数据
     * @param ppmKpiPmDetailInfoDto
     * @return PpmKpiPmDetailInfoDto
     */
    PpmKpiPmDetailInfoDto getPpmKpiPmDetailInfo(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto);

    /**
     *  用于自评类更新先从页面传来参数
     * @param ppmKpiPmDetailDto
     * @return  int
     */
    int updatePpmKpiPmDetailDto(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 更新子项状态
     * @param param
     * @return int
     */
    int updateItemStatus(Map param);

    /**
     * 根据Pid和kpitype查询子项分数
     * @param param
     * @return PpmKpiPmDetail
     */
    PpmKpiPmDetail querySumValue(Map param);

    /**
     * 考核综合能力评价显示页面
     * @param ppmKpiPmDetailInfoDto
     * @return List<PpmKpiPmDetailInfoDto>
     */
    List<PpmKpiPmDetailInfoDto> getPpmKpiPmComprehensiveAbilityEvalInfo(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto);

    /**
     * 评价修改考核信息
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int updateDetail(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 更新多条_评价修改考核信息
     * @param ppmKpiPmDetailDtos
     * @return int
     */
    int updateDetails(List<PpmKpiPmDetailDto> ppmKpiPmDetailDtos);

    /**
     * 考核评价回退到自评
     * @param ppmKpiPmDetailDto
     * @param userid
     * @return int
     */
    int backEvalSelf(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid);

    /**
     * 考核评价回退到终评
     * @param ppmKpiPmDetailDto
     * @param userid
     * @return int
     */
    int backFirstEval(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid);

    /**
     * 提交考核评价
     * @param ppmKpiPmDetailDto
     * @param userid
     * @return int
     */
    int submitEval(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid);

    /**
     * 查询详细项数据
     * @param ppmKpiPmDetailDto
     * @return List<PpmKpiPmDetailDto>
     */
    List<PpmKpiPmDetailDto> getPpmKpiPmDetails(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 获得综合评价的数据
     * @param comprehensiveAbilityEvalDto
     * @return ComprehensiveAbilityEvalDto
     */
    ComprehensiveAbilityEvalDto queryComprehensiveAbilityEvalInfo(
            ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto);

    /**
     * 提交整体评语
     * @param ppmKpiPmDetailDto
     * @param userid
     * @return int
     */
    int submitWholeComment(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid);


    /**
     * 提交综合能力评价数据
     * @param comprehensiveAbilityEvalDto
     * @param userid
     * @return int
     */
    int submitComprehensiveAbilityEval(ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto,String userid);

    /**
     * 获得kpi和平衡计分的汇总分
     * @param param
     * @return double
     */
    double getKpiOrBalanceSumScore(Map param);

    /**
     * 获得综合能力和履职评估的汇总分
     * @param param
     * @return double
     */
    double getComprehensiveAbilityOrPostEvaluationSumScore(Map param);

    /**
     * 查询考核项目表里面平衡计分项
     * @param ppmKpiPmDetailDto
     * @return List<PpmKpiPmDetailDto>
     */
    List<PpmKpiPmDetailDto> getBalanceItem(PpmKpiPmDetailDto ppmKpiPmDetailDto);


    /**
     * 查询考核项目表里面平衡计分项描述
     * @param ppmKpiPmDetailDto
     * @return List<PpmKpiPmDetailDto>
     */
    List<PpmKpiPmDetailDto> getBalancePlan(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 根据条件查询
     * @param ppmKpiPmDetail
     * @return PpmKpiPmDetail
     */
    PpmKpiPmDetail queryPpmKpiPmDetailByCondition(PpmKpiPmDetail ppmKpiPmDetail);


    /**
     * 综合能力，履职评估，平衡计分统计评价数据
     * @param ppmKpiPmDetailDto
     * @return List<PpmKpiPmDetailDto>
     */
    List<PpmKpiPmDetailDto> queryPpmKpiPmDetailEvalGroup(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 删除子项考核数据
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int deletePpmKpiPmDetailByPid(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 保存综合能力评价数据
     * @param comprehensiveAbilityEvalDto
     * @param userid
     * @return int
     */
    int saveComprehensiveAbilityEval(ComprehensiveAbilityEvalDto comprehensiveAbilityEvalDto,String userid);

    /**
     * 综合能力整体提交
     * @param ppmKpiPmDetailDto
     * @param userid
     * @return int
     */
    int submitTotalComprehensiveAbilityEval(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid);


    /**
     * 保存评语
     * @param ppmKpiPmDetailDto
     * @param userid
     * @return int
     */
    int saveWholeComment(PpmKpiPmDetailDto ppmKpiPmDetailDto,String userid);


    /**
     * 员工考核详细信息状态修改查询
     * @param ppmKpiPmDetailDto
     * @return List<PpmKpiPmDetailDto>
     */
    List<PpmKpiPmDetailInfoDto> queryPpmKpiPmDetailInfoGroup(PpmKpiPmDetailDto ppmKpiPmDetailDto);


    /**
     * 员工考核修改状态获得考核详细数据
     * @param ppmKpiPmDetailInfoDto
     * @return List<PpmKpiPmDetailInfoDto>
     */
    List<PpmKpiPmDetailInfoDto> getDetailItemList(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto);

    /**
     * 后台更新状态数据_单条
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int backStageUpdateStatus(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 后台更新状态数据_-多条
     * @param ppmKpiPmDetailDtos
     * @return int
     */
    int backStageUpdateListStatus(List<PpmKpiPmDetailDto> ppmKpiPmDetailDtos);

    /**
     * 后台更新状态数据_单条
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int singleBackStageUpdateStatus(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 保存评价
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int updateDetailEval(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 查询履职评估信息
     * @param ppmKpiPmDetailInfoDto
     * @return List<PpmKpiPmDetailInfoDto>
     */
    List<PpmKpiPmDetailInfoDto> getPpmKpiPmPostEvaluationEvalInfo(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto);


    /**
     * 获得履职评估的数据
     * @param postEvaluationEvalDto
     * @return ComprehensiveAbilityEvalDto
     */
    PostEvaluationEvalDto queryPostEvaluationEvalEvalInfo(PostEvaluationEvalDto postEvaluationEvalDto);

    /**
     * 保存数据
     * @param postEvaluationEvalDto
     * @return int
     */
    int savePostEvaluationEval(PostEvaluationEvalDto postEvaluationEvalDto);

    /**
     * 提交数据
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int submitTotalPostEvaluationEval(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 考核履职评估科技部后台查询
     * @param ppmKpiPmDetailInfoDto
     * @param pager
     */
    void getPostEvaluationPager(PpmKpiPmDetailInfoDto  ppmKpiPmDetailInfoDto, Pager pager);

    /**
     *  考核履职评估科技部评价后台查询数据
     * @param ppmKpiPmDetailInfoDto
     * @return List<PpmKpiPmDetailInfoDto>
     */
    List<PpmKpiPmDetailInfoDto> getPostEvaluationList(PpmKpiPmDetailInfoDto ppmKpiPmDetailInfoDto);

    /**
     * 导入科技部履职评估数据
     * @param file
     * @return boolean
     */
    boolean importExcel(MultipartFile file);


    /**
     *  提交科技部评价履职评估数据
     * @param ppmKpiPmDetailDto
     * @return int
     */
    int submitTechPostEvaluation(PpmKpiPmDetailDto ppmKpiPmDetailDto);

    /**
     * 考核履职评估科技部评价后台查询数据分组
     * @param ppmKpiPmDetailInfoDto
     * @param pager
     */
    void getPostEvaluationGroupPager(PpmKpiPmDetailInfoDto  ppmKpiPmDetailInfoDto, Pager pager);
}
