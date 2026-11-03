package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmIntervieweeInfo;

import java.util.List;

/**
 * 外包面试人员信息
 */
public interface IPpmIntervieweeInfoService {

    /**
     * 添加
     * @param peeList 集合
     * @return int
     */
    int addPpmIntervieweeInfo(List<PpmIntervieweeInfo> peeList) ;

    /**
     * 查询详情 - 集合
     * @param instanceid 流程编号
     * @return List<PpmIntervieweeInfoDto>
     */
    List<PpmIntervieweeInfoDto> queryListForInstanceid(String instanceid) ;

    /**
     * 更新
     * @param ppmIntervieweeInfo 对象
     * @return Integer
     */
    Integer updatePpmIntervieweeInfo(PpmIntervieweeInfo ppmIntervieweeInfo) ;

    /**
     * 删除
     * @param instanceId
     * @return Integer
     */
    Integer deleteByInstanceId(String instanceId) ;
    /**
     * 删除
     * @param ppmIntervieweeInfo 对象
     * @return Integer
     */
    Integer deletePpmIntervieweeInfo(PpmIntervieweeInfo ppmIntervieweeInfo) ;

    /**
     *  详情
     * @param id 编号
     * @param instanceid 流程编号
     * @return  PpmIntervieweeInfoDto
     */
    PpmIntervieweeInfoDto queryDetail(long id,String instanceid);

    /**
     * 面试进度
     * @param instanceid 流程id
     * @return List<PpmIntervieweeInfoDto>
     */
    List<PpmIntervieweeInfoDto> getInterviewProgress(String instanceid);

    /**
     * 根据身份证号查询信息
     * @param certnumber
     * @return PpmIntervieweeInfo
     */
    PpmIntervieweeInfo queryPpmIntervieweeInfoByIdcard(String certnumber);

    /**
     * 获取面试通过 未录入花名册的信息
     * @param deptNo 部门编号
     * @return List<PpmIntervieweeInfoDto>
     */
    List<PpmIntervieweeInfoDto> getCourseInfoList(String deptNo);
}
