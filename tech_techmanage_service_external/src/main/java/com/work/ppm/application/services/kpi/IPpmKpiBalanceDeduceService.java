package com.work.ppm.application.services.kpi;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiBalanceDeduceDto;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmKpiBalanceDeduceService {

    /**
     * 分页
     * @param ppmKpiBalanceDeduceDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiBalanceDeduceDto 对象
     * @return int
     */
    int addPpmKpiBalanceDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) ;

    /**
     * 查询详情
     * @param ppmKpiBalanceDeduceDto 对象
     * @return PpmKpiBalanceDeduceDto
     */
    @Deprecated
    PpmKpiBalanceDeduceDto queryDetail(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) ;

    /**
     * 更新
     * @param ppmKpiBalanceDeduceDto  对象
     * @return int
     */
    int updatePpmKpiBalanceDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) ;

    /**
     * 删除
     * @param ppmKpiBalanceDeduceDto 对象
     * @return int
     */
    int deletePpmKpiBalanceDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto) ;

    /**
     * 获得平衡计分扣分详细项
     * @param ppmKpiBalanceDeduceDto 对象
     * @return List<PpmKpiBalanceDeduceDto>
     */
    List<PpmKpiBalanceDeduceDto> getBalanceDeduceItem(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto);

    /**
     * 计算分数
     * @param ppmKpiBalanceDeduceDto 对象
     * @return double
     */
    double getSummaryValue(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto);

    /**
     * 更新考核指标项表中平衡计分数据
     * @param ppmKpiBalanceDeduceDto 对象
     * @return int
     */
    int setBalanceValue(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto);

    /**
     * 添加
     * @param ppmKpiBalanceDeduceDto 对象
     * @return int
     */
    int addDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto);

    /**
     * 更新
     * @param ppmKpiBalanceDeduceDto 对象
     * @return int
     */
    int updateDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto);

    /**
     * 删除
     * @param ppmKpiBalanceDeduceDto 对象
     * @return int
     */
    int deleteDeduce(PpmKpiBalanceDeduceDto ppmKpiBalanceDeduceDto);
}
