package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqTotalDto;
import com.work.ppm.application.model.integratedmanager.OutOverTimeMonthData;
import com.work.ppm.application.model.integratedmanager.ext.OutOvertimeOrderDataInfo;
import com.work.ppm.commons.pager.Pager;

/**
  * 接口描述
  */
public interface IPpmKqTotalService {

    /**
     * 分页
     * @param ppmKqTotalDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKqTotalDto ppmKqTotalDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKqTotalDto 对象
     * @return int
     */
     @Deprecated
    int addPpmKqTotal(PpmKqTotalDto ppmKqTotalDto) ;

    /**
     * 查询详情
     * @param ppmKqTotalDto 对象
     * @return PpmKqTotalDto
     */
    @Deprecated
    PpmKqTotalDto queryDetail(PpmKqTotalDto ppmKqTotalDto) ;

    /**
     * 更新
     * @param ppmKqTotalDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKqTotal(PpmKqTotalDto ppmKqTotalDto) ;

    /**
     * 删除
     * @param ppmKqTotalDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKqTotal(PpmKqTotalDto ppmKqTotalDto) ;

    /**
     * 获得外包加班月度统计数据
     * @param outDataDto 对象
     * @return OutOverTimeMonthData
     */
    OutOverTimeMonthData getOutOvertimeMonthData(OutDataDto outDataDto);

    /**
     * 获得外包加班统计排名
     * @param outDataDto 对象
     * @return OutOvertimeOrderDataInfo
     */
    OutOvertimeOrderDataInfo getOutOvertimeOrderData(OutDataDto outDataDto);
}
