package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingRiskResultDto;
import com.work.ppm.application.model.integratedmanager.OutRiskResultData;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmOutsourcingRiskResultService {

    /**
     * 分页
     * @param ppmOutsourcingRiskResultDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingRiskResultDto 对象
     * @return int
     */
     @Deprecated
    int addPpmOutsourcingRiskResult(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) ;

    /**
     * 查询详情
     * @param ppmOutsourcingRiskResultDto 对象
     * @return PpmOutsourcingRiskResultDto
     */
    @Deprecated
    PpmOutsourcingRiskResultDto queryDetail(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) ;

    /**
     * 更新
     * @param ppmOutsourcingRiskResultDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmOutsourcingRiskResult(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) ;

    /**
     * 删除
     * @param ppmOutsourcingRiskResultDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmOutsourcingRiskResult(PpmOutsourcingRiskResultDto ppmOutsourcingRiskResultDto) ;

    /**
     * 查询外包最新的排查数据
     * @param outDataDto
     * @return List<OutEvalOrderDataInfo>
     */
    List<OutRiskResultData> getOutRiskResults(OutDataDto outDataDto);
}
