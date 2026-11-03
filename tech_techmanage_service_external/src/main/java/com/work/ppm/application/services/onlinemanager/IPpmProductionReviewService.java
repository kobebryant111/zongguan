package com.work.ppm.application.services.onlinemanager;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.onlinemanager.PpmProductionReviewDto;

/**
  * 接口描述
  */
public interface IPpmProductionReviewService {

    /**
     * 分页
     * @param ppmProductionReviewDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmProductionReviewDto ppmProductionReviewDto, Pager pager) ;

    /**
     * 添加
     * @param ppmProductionReviewDto 对象
     * @return int
     */
    int addPpmProductionReview(PpmProductionReviewDto ppmProductionReviewDto) ;

    /**
     * 查询详情
     * @param ppmProductionReviewDto 对象
     * @return PpmProductionReviewDto
     */
    PpmProductionReviewDto queryDetail(PpmProductionReviewDto ppmProductionReviewDto) ;

    /**
     * 更新
     * @param ppmProductionReviewDto  对象
     * @return int
     */
    int updatePpmProductionReview(PpmProductionReviewDto ppmProductionReviewDto) ;

    /**
     * 上线验证发起
     * @param ppmProductionReviewDto
     * @param userId
     * @return int
     */
    int addPpmProductionReviewFlow(PpmProductionReviewDto ppmProductionReviewDto, String userId) ;

    /**
     * 业务经理审批
     * @param ppmProductionReviewDto
     * @param bps
     * @return int
     */
    String approveReviewByBusjl(PpmProductionReviewDto ppmProductionReviewDto, BpsWorkFlowDto bps) ;

    /**
     * 根据instanceid查询流程信息
     * @param ppmProductionReviewDto
     * @return PpmProductionReviewDto
     */
    PpmProductionReviewDto queryDetailByInstanceid(PpmProductionReviewDto ppmProductionReviewDto) ;

    /**
     * 手机端延迟上线时只更新数据
     * @param ppmProductionReviewDto 对象
     * @return int
     */
    int updateMoblie(PpmProductionReviewDto ppmProductionReviewDto);
}
