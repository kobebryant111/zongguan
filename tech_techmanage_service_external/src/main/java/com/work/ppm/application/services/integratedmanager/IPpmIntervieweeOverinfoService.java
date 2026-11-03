package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.IntervieweeInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeOverinfoDto;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeOverinfo;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 面试归档
 *
 */
public interface IPpmIntervieweeOverinfoService {

    /**
     * 分页
     *
     * @param ppmIntervieweeOverinfo 对象
     * @param pager
     * @throws Exception
     */
    @Deprecated
    void queryPager(PpmIntervieweeOverinfo ppmIntervieweeOverinfo, Pager pager);

    /**
     * 添加
     *
     * @param ppmIntervieweeOverinfo 对象
     * @return int
     */
    int addPpmIntervieweeOverinfo(PpmIntervieweeOverinfo ppmIntervieweeOverinfo);

    /**
     * 查询详情
     *
     * @param ppmIntervieweeOverinfo 对象
     * @return PpmIntervieweeOverinfo
     */
    @Deprecated
    PpmIntervieweeOverinfo queryDetail(PpmIntervieweeOverinfo ppmIntervieweeOverinfo);

    /**
     * 更新
     *
     * @param ppmIntervieweeOverinfo 对象
     * @return Integer
     */
    @Deprecated
    Integer updatePpmIntervieweeOverinfo(PpmIntervieweeOverinfo ppmIntervieweeOverinfo);

    /**
     * 删除
     *
     * @param instanceId
     * @return Integer
     */
    Integer deleteByInstanceId(String instanceId);

    /**
     * 删除
     *
     * @param ppmIntervieweeOverinfo 对象
     * @return Integer
     */
    Integer deletePpmIntervieweeOverinfo(PpmIntervieweeOverinfo ppmIntervieweeOverinfo);

    /**
     * 根据人力需求ID查询已通过人数
     * @param rlId
     * @return
     */
    int queryCarryOutNum(Long rlId);

    /**
     * 检查人力导入excel是否存在
     *
     * @param number 身份证
     * @return boolean
     */
    PpmIntervieweeOverinfoDto getCheckedExistEntry(String number);

    /**
     * 检查人力导入excel是否通过
     *
     * @param number 身份证
     * @return boolean
     */
    PpmIntervieweeOverinfoDto getCheckedPassEntry(String number);

    /**
     * getIntervieweeInfo
     *
     * @param idcard
     * @return IntervieweeInfoDto
     */
    IntervieweeInfoDto getIntervieweeInfo(String idcard);
    /**
     * getIntervieweeInfo
     *
     * @param idcard
     * @return IntervieweeInfoDto
     */
    List<IntervieweeInfoDto> getIntervieweeInfos(String idcard);
}
