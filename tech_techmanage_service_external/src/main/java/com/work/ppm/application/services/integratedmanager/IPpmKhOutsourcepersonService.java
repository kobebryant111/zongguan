package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmKhOutsourcepersonDto;
import com.work.ppm.application.model.integratedmanager.OutEvalMonthData;
import com.work.ppm.application.model.integratedmanager.ext.OutEvalOrderDataInfo;
import com.work.ppm.commons.pager.Pager;

/**
  * 接口描述
  */
public interface IPpmKhOutsourcepersonService {

    /**
     * 分页
     * @param ppmKhOutsourcepersonDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKhOutsourcepersonDto 对象
     * @return int
     */
     @Deprecated
    int addPpmKhOutsourceperson(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) ;

    /**
     * 查询详情
     * @param ppmKhOutsourcepersonDto 对象
     * @return PpmKhOutsourcepersonDto
     */
    @Deprecated
    PpmKhOutsourcepersonDto queryDetail(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) ;

    /**
     * 更新
     * @param ppmKhOutsourcepersonDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmKhOutsourceperson(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) ;

    /**
     * 删除
     * @param ppmKhOutsourcepersonDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmKhOutsourceperson(PpmKhOutsourcepersonDto ppmKhOutsourcepersonDto) ;
    /**
     * 获得外包考核月度统计数据
     * @param outDataDto 对象
     * @return OutEvalMonthData
     */
    OutEvalMonthData getOutEvalMonthData(OutDataDto outDataDto);

    /**
     * 获得外包考核统计排名
     * @param outDataDto 对象
     * @return OutEvalOrderDataInfo
     */
    OutEvalOrderDataInfo getOutEvalOrderData(OutDataDto outDataDto);
}
