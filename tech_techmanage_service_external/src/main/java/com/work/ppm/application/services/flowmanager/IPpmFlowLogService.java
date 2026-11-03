package com.work.ppm.application.services.flowmanager;

import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;

import java.util.List;

/**
 * 流程日志
 */
public interface IPpmFlowLogService {

    /**
     * 分页
     * @param ppmFlowLog 对象
     * @param pager 分页对象
     */
    void queryPager(PpmFlowLog ppmFlowLog, Pager pager) ;
    /**
     * 添加
     * @param ppmFlowLog 对象
     * @return int
     */
    int addPpmFlowLog(PpmFlowLog ppmFlowLog) ;
    /**
     * 查询详情
     * @param ppmFlowLog 对象
     * @return PpmFlowLog
     */
    @Deprecated
    PpmFlowLog queryDetail(PpmFlowLog ppmFlowLog) ;

    PpmFlowLog selectOneByInstanceId(String instanceId);
    /**
     * 更新
     * @param ppmFlowLog 对象
     * @return Integer
     */
    Integer updatePpmFlowLog(PpmFlowLog ppmFlowLog) ;

    /**
     * 删除
     * @param ppmFlowLog 对象
     * @return Integer
     */
    Integer deletePpmFlowLog(PpmFlowLog ppmFlowLog) ;

    /**
     * 批量添加
     * @param list 集合
     * @return int
     */
    int batchAddFlowLog(List<PpmFlowLog> list) ;

    /**
     * 公共方法添加日志
     * @param bps 对象
     */
    void saveFlowLog(BpsWorkFlowDto bps) ;

    /**
     * 添加流程日志
     * @param bps 对象
     * @return ppmFlowLog
     */
    PpmFlowLog addFlowLog(BpsWorkFlowDto bps);

    /**
     * 获取流程日志信息
     * @param ppmFlowLog 对象
     * @return List<PpmFlowLogDto>
     */
    List<PpmFlowLogDto> queryList(PpmFlowLog ppmFlowLog);

    /**
     * 查询详情列表
     * @param instid  流程实例id
     * @return  list
     */
    List<PpmFlowLogDto> queryDetaiList(String instid);

    /**
     * 结合流程日志和历史流程日志分页查询
     * @param ppmFlowLogDto  对象
     * @param pager  分页对象
     */
    void queryContainHisPager(PpmFlowLogDto ppmFlowLogDto, Pager pager);

    /**
     * 批量删除
     * @param idss  数据
     * @return  int
     */
    int deletePpmFlowLog(String[] idss);

    /**
     * 获取密管密码流程信息
     * @param instanceid 流程id
     * @return PpmFlowLogDto
     */
    PpmFlowLogDto getPwdFlow(String instanceid);

    /**
     * 根据flowcaseno、applydesc、conclusion查询业务意向上线日志
     * @param ppmFlowLog 对象
     * @return List<PpmFlowLogDto>
     */
    List<PpmFlowLogDto> queryOnlineSplogs(PpmFlowLog ppmFlowLog);
    /**
     * 删除临时日志
     * @param ppmFlowLog 对象
     * @return int
     */
    int deleteByNullLog(PpmFlowLog ppmFlowLog);
}
