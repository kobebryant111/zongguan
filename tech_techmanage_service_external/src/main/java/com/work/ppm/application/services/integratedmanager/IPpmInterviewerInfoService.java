package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmInterviewerInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmInterviewerInfo;

import java.util.List;

/**
 * 面试官信息
 *
 */
public interface IPpmInterviewerInfoService {
    /**
     * 添加
     *
     * @param ppmInterviewerInfo 对象
     * @return int
     */
    int addPpmInterviewerInfo(PpmInterviewerInfoDto ppmInterviewerInfo);

    /**
     * 根据流程编号查询集合
     *
     * @param instanceId 流程编号
     * @return List<PpmInterviewerInfoDto>
     */

    List<PpmInterviewerInfoDto> queryListForInstanceid(String instanceId);

    /**
     * 根据流程编号查询集合
     *
     * @param instanceId 流程编号
     * @return PpmInterviewerInfoDto
     */

    PpmInterviewerInfoDto queryListForInstanceidAndMsgId(String instanceId, String msgid);

    /**
     * 更新
     *
     * @param ppmInterviewerInfo 对象
     * @return Integer
     */
    Integer updatePpmInterviewerInfo(PpmInterviewerInfo ppmInterviewerInfo);

    /**
     * 删除
     *
     * @return Integer
     */
    Integer deletePpmInterviewerInfo(PpmInterviewerInfo ppmInterviewerInfo);

    /**
     * 删除
     *
     * @param instanceid 流程编号
     * @return Integer
     */
    Integer deleteByInstanceId(String instanceid);

    /**
     * 查询详情
     *
     * @param instanceid 流程id
     * @return PpmInterviewerInfoDto
     */
    PpmInterviewerInfoDto getItemByDeptInstanceid(String instanceid);
}
