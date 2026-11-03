package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeScoreinfoDto;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeScoreinfo;

import java.util.List;

/**
 * 面试打分
 */
public interface IPpmIntervieweeScoreinfoService {
    /**
     * 添加
     * @param ppmIntervieweeScoreinfos 集合
     * @return int
     */
    int addPpmIntervieweeScoreinfo(List<PpmIntervieweeScoreinfo> ppmIntervieweeScoreinfos) ;

    /**
     * 查询详情
     * @param ppmIntervieweeScoreinfo 对象
     * @return PpmIntervieweeScoreinfo
     */
    @Deprecated
    PpmIntervieweeScoreinfo queryDetail(PpmIntervieweeScoreinfo ppmIntervieweeScoreinfo) ;

    /**
     * 更新
     * @param ppmIntervieweeScoreinfo 对象
     * @return Integer
     */
    Integer updatePpmIntervieweeScoreinfo(PpmIntervieweeScoreinfo ppmIntervieweeScoreinfo) ;

    /**
     * 删除
     * @param processInstId 流程编号
     * @return Integer
     */
    Integer deleteByInstanceId(String processInstId) ;
    /**
     * 删除
     * @param ppmIntervieweeScoreinfo 流程编号
     * @return Integer
     */
    Integer deletePpmIntervieweeScoreinfo(PpmIntervieweeScoreinfo ppmIntervieweeScoreinfo) ;

    /**
     * 根据流程id查询面试打分记录
     * @param processInstId 流程id
     * @param pid 面试员工编号
     * @return List<PpmIntervieweeScoreinfo>
     */
    List<PpmIntervieweeScoreinfo> getScoreList(String processInstId,String pid);

    /**
     * 根据流程id查询面试打分记录
     * @param processInstId 流程id
     * @return List<PpmIntervieweeScoreinfo>
     */
    List<PpmIntervieweeScoreinfo> getScoreList(String processInstId);
    /**
     * 根据流程id查询面试打分记录
     * @param processInstId 流程id
     * @return List<PpmIntervieweeScoreinfo>
     */
    List<PpmIntervieweeScoreinfo> getScoreListByInstIdAndIdCard(String processInstId,String idCard);

    /**
     * 根据流程编号查询面试官信息
     * @param instanceId 流程编号
     * @return list
     */
    List<PpmIntervieweeScoreinfoDto> getInterviewer(String instanceId);
}
