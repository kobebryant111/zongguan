package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.PpmKpiPostevaluationInfoDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiPostevaluationDto;

import java.util.List;
import java.util.Map;

/**
  * 履职评估接口
  */
public interface IPpmKpiPostevaluationService {

    /**
     * 分页
     * @param ppmKpiPostevaluationDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiPostevaluationDto ppmKpiPostevaluationDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiPostevaluationDto 对象
     * @return int
     */
    int addPpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) ;

    /**
     * 查询详情
     * @param ppmKpiPostevaluationDto 对象
     * @return PpmKpiPostevaluationDto
     */
    @Deprecated
    PpmKpiPostevaluationDto queryDetail(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) ;

    /**
     * 更新
     * @param ppmKpiPostevaluationDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) ;

    /**
     * 删除
     * @param ppmKpiPostevaluationDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto) ;

    /**
     *  查询履职评估待评价条数
     * @param param
     * @return long
     */
    long queryKpiPostevaluations(Map param);

    /**
     * 查询需要评价数据
     * @param ppmKpiPostevaluationInfoDto
     * @return List<PpmKpiPostevaluationInfoDto>
     */
    List<PpmKpiPostevaluationInfoDto> queryPpmKpiPostevaluationInfos(
            PpmKpiPostevaluationInfoDto ppmKpiPostevaluationInfoDto);

    /**
     * 根据条件查询履职评估数据
     * @param ppmKpiPostevaluationDto
     * @return List<PpmKpiPostevaluationDto>
     */
    List<PpmKpiPostevaluationDto> queryPpmKpiPostevaluationInfosByCondition(
            PpmKpiPostevaluationDto ppmKpiPostevaluationDto);
    /**
     * 单条数据更新
     * @param ppmKpiPostevaluationDto
     * @return int
     */
    int updateSinglePpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto);

    /**
     * 获得平均分
     * @param param
     * @return Double
     */
    Double getAvgScore(Map param);

    /**
     * 提交数据
     * @param ppmKpiPostevaluationDto
     * @return int
     */
    int submitPpmKpiPostevaluation(PpmKpiPostevaluationDto ppmKpiPostevaluationDto);

    /**
     * 按条件查询数据
     * @param param
     * @return List<PpmKpiPostevaluationDto>
     */
    List<PpmKpiPostevaluationDto> queryPpmKpiPostevaluationInfosByCondition(Map param);

    /**
     *  查询统计计分
     * @param ppmKpiPostevaluationDto
     * @return PpmKpiPostevaluationDto
     */
    PpmKpiPostevaluationDto queryPostEvaluationSumScore(PpmKpiPostevaluationDto ppmKpiPostevaluationDto);
}
