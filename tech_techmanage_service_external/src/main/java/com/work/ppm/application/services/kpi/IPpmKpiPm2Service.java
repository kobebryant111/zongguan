package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.KpiLevelPersons;
import com.work.ppm.application.dto.kpi.PpmKpiLevelPersonInfoDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoDto;
import com.work.ppm.application.model.kpi.PpmKpiPm;
import com.work.ppm.commons.pager.Pager;

import java.util.List;
import java.util.Map;

/**
  * 考核主表
  */
public interface IPpmKpiPm2Service {
    /**
     * 计算各个分类的分数和总分
     * @param pid 对象
     */
    void summaryScore(long pid);

    /**
     * 根据条件查询员工考核数据_分页
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     */
    void getEmpKpiDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid);

    /**
     * 根据条件查询员工考核数据
     * @param ppmKpiPmInfoDto 对象
     * @return List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmInfoDto> getEmpKpiDataList(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid);

    /**
     * 员工考核查询数据分页
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     */
    void queryKpiPmByConditionPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager);


    /**
     * 查询状态修改显示数据
     * @param ppmKpiPmInfoDto 对象
     * @return List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmInfoDto> queryKpiPmByConditionList(PpmKpiPmInfoDto ppmKpiPmInfoDto);

    /**
     * 获得下级对上级的待评价的汇总信息分页
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     * @param userid 对象
     */
    void getPostEvalInfoSummaryPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid);

    /**
     * 获得下级对上级的待评价的汇总信息
     * @param param 对象
     * @return List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmInfoDto> getPostEvalInfoSummaryList(Map param);


    /**
     * 发起履职评估
     * @param launchPpmKpiPmDto 对象
     * @return int 对象
     */
    int launchPostEvaluationEval(PpmKpiPmDto launchPpmKpiPmDto);

    /**
     * 发起年度评价
     * @param ppmKpiPmDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int launchYearData(PpmKpiPmDto ppmKpiPmDto, String userid);

    /**
     * 获得部门年度确认分页数据
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     * @param userid 对象
     */
    void getDepYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid);

    /**
     * 获得部门年度确认数据
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmInfoDto> getDepYearDataList(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid);


    /**
     * 确认添加分
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return  int 对象
     */
    int addScore(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid);

    /**
     * 提交部门考核年度确认数据
     * @param userid 对象
     * @return int 对象
     */
    int submitDepYearConfirm(String userid);

    /**
     * 获得中心年度数据初评
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmInfoDto> getCenterYearDataList(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid);

    /**
     * 获得中心年度数据初评分页
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     * @param userid 对象
     */
    void getCenterYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid);
    /**
     * 提交中心年度数据
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int submitCenterYearEval(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid);

    /**
     * 查询比例人数提示
     * @param ppmKpiLevelPersonInfoDto 对象
     * @param userid 对象
     * @return List<KpiLevelPersons>
     */
    List<KpiLevelPersons> getLevelPersonTip(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, String userid);

    /**
     * 查询年度考核数据
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return  List<PpmKpiPmInfoDto> 对象
     */
    List<PpmKpiPmInfoDto> getTecYearDataList(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid);

    /**
     * 查询年度考核数据分页
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     * @param userid 对象
     */
    void getTecYearDataPager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager, String userid);

    /**
     * 提交科技部数据
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int submitTecYearEval(PpmKpiPmInfoDto ppmKpiPmInfoDto, String userid);

    /**
     * 更新中心年度考核数据
     * @param ppmKpiPmInfoDto 对象
     * @return  int 对象
     */
     int updateCenterTlevel(PpmKpiPmInfoDto ppmKpiPmInfoDto);

    /**
     * 更新科技部年度考核数据
     * @param ppmKpiPmInfoDto 对象
     * @return  int 对象
     */
    int updateTecTlevel(PpmKpiPmInfoDto ppmKpiPmInfoDto);

    /**
     * 查询中心比例人数提示
     * @param ppmKpiLevelPersonInfoDto 对象
     * @param userid 对象
     * @return  List<KpiLevelPersons>
     */
     List<KpiLevelPersons> getCenterLevelPersonTip(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto, String userid);

    /**
     * 获得部门考核状态数据。从统计过来的，取部门终最小的状态
     * @param ppmKpiPmDto 对象
     * @param pager 对象
     */
    void getDepStatusPager(PpmKpiPmDto ppmKpiPmDto, Pager pager);

    /**
     * 更新部门状态数据
     * @param ppmKpiPmDto 对象
     * @return int 对象
     */
    int updateDepStatus(PpmKpiPmDto ppmKpiPmDto);


    /**
     *  发起综合能力评估
     * @param launchPpmKpiPmDto 对象
     * @return int 对象
     */
    int launchComprehensive(PpmKpiPmDto launchPpmKpiPmDto);

    /**
     *  发起总体评语
     * @param launchPpmKpiPmDto 对象
     * @return int 对象
     */
    int launchComment(PpmKpiPmDto launchPpmKpiPmDto);


    /**
     * 发起经理Kpi自评
     * @param launchPpmKpiPmDto 对象
     * @return int 对象
     */
    int launchDepManagersSelfEval(PpmKpiPmDto launchPpmKpiPmDto);

    /**
     * 获得未提交的中心
     * @param ppmKpiLevelPersonInfoDto 对象
     * @param userid 对象
     * @return KpiLevelPersons 对象
     */
    KpiLevelPersons getNoSubmitCenterTip(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto,
                                         String userid);

    /**
     * 获得未提交的部门
     * @param ppmKpiLevelPersonInfoDto 对象
     * @param userid 对象
     * @return KpiLevelPersons 对象
     */
    KpiLevelPersons getNoSubmitDepTip(PpmKpiLevelPersonInfoDto ppmKpiLevelPersonInfoDto,
                                      String userid);

    /**
     * 平衡计分汇总分数
     * @param ppmKpiPm 对象
     */
    void summaryScoreForBalance( PpmKpiPm ppmKpiPm);

}
