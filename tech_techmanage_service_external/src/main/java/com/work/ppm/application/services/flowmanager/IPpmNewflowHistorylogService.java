package com.work.ppm.application.services.flowmanager;

import com.work.ppm.application.dto.flowmanager.PpmNewflowHistorylogDto;
import com.work.ppm.application.model.flowmanager.PpmNewflowHistorylog;

import java.util.List;

/**
  * 历史流程日志
  */
public interface IPpmNewflowHistorylogService {

    /**
     * 添加
     * @param ppmNewflowHistorylogDto 对象
     * @return int
     */
    int addPpmNewflowHistorylog(PpmNewflowHistorylogDto ppmNewflowHistorylogDto) ;

    /**
     * 删除
     * @param ppmNewflowHistorylogDto 对象
     * @return int
     */
    int deletePpmNewflowHistorylog(PpmNewflowHistorylogDto ppmNewflowHistorylogDto) ;

    /**
     * 添加历史流程日志
     * @param flowcaseno 流程id
     * @return int
     */
    int addHisLog(String flowcaseno);

    /**
     * 更新
     * @param ppmNewflowHistorylogDto 对象
     * @return int
     */
    int updatePpmNewflowHistorylog(PpmNewflowHistorylogDto ppmNewflowHistorylogDto);

    /**
     * 删除
     * @param ids id字符串 多个 ‘,’ 分开
     * @return int
     */
    int deletePpmNewflowHistorylog(String ids) ;

    /**
     * 根据流程id查询
     * @param flowcaseno 流程id
     * @return list
     */
    List<PpmNewflowHistorylogDto> queryList(String flowcaseno);

    /**
     * 删除历史日志
     * @param ppmFlowLog 对象
     * @return int
     */
    int deleteByHisNullLog(PpmNewflowHistorylog ppmFlowLog);
}
