package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.OutDataDto;
import com.work.ppm.application.dto.integratedmanager.PpmOutsourcingTrainDto;
import com.work.ppm.application.model.integratedmanager.OutTrainData;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
  * 接口描述
  */
public interface IPpmOutsourcingTrainService {

    /**
     * 分页
     * @param ppmOutsourcingTrainDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmOutsourcingTrainDto ppmOutsourcingTrainDto, Pager pager) ;

    /**
     * 添加
     * @param ppmOutsourcingTrainDto 对象
     * @return int
     */
     @Deprecated
    int addPpmOutsourcingTrain(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) ;

    /**
     * 查询详情
     * @param ppmOutsourcingTrainDto 对象
     * @return PpmOutsourcingTrainDto
     */
    @Deprecated
    PpmOutsourcingTrainDto queryDetail(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) ;

    /**
     * 更新
     * @param ppmOutsourcingTrainDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmOutsourcingTrain(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) ;

    /**
     * 删除
     * @param ppmOutsourcingTrainDto 对象
     * @return int
     */
    @Deprecated
    int deletePpmOutsourcingTrain(PpmOutsourcingTrainDto ppmOutsourcingTrainDto) ;


    /**
     * 获得外包培训数据
     * @param outDataDto 对象
     * @return List<OutTrainData>
     */
    List<OutTrainData> getOutTrainList(OutDataDto outDataDto);
}
