package com.work.ppm.application.services.vendormanager;

import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessApplyDto;
import com.work.ppm.application.dto.vendormanager.OutsrcEmpAssessDto;
import com.work.ppm.application.model.integratedmanager.ext.PpmOutsourcingBasicInfo;
import com.work.ppm.application.model.vendormanager.OutsrcEmpAssess;

import java.util.List;

/**
 * <p>
 * 外包人员考核 接口描述
 * </p>
 *
 * @since 2023-01-03
 */


public interface IOutsrcEmpAssessService {

    /**
     * 集合查询
     * @param outsrcEmpAssess 对象
     * @return list
     */
    List<OutsrcEmpAssessDto> queryList(OutsrcEmpAssess outsrcEmpAssess);

    /**
     * 年度考核计算平均分
     * @param outsrcingAssess 对象
     * @return list
     */
    List<OutsrcEmpAssessDto> getYearAssessWithAvgScore(OutsrcEmpAssess outsrcingAssess);

    /**
     * 年度考评 - 每年1月1号调度执行
     * @param outsrcingAssess 对象
     * @return list
     */
    List<OutsrcEmpAssessDto> getYearAssess(OutsrcEmpAssess outsrcingAssess);

    /**
     * 年度Apply
     * @param outsrcingAssess 对象
     * @return list
     */
    List<OutsrcEmpAssessApplyDto> getYearAssessApply(OutsrcEmpAssess outsrcingAssess);

    /**
     * 获取
     * @param outsrcingAssess 对象
     * @param apply 申请
     * @return list
     */
    List<PpmOutsourcingBasicInfo> getPersonListByMgr(OutsrcEmpAssess outsrcingAssess,OutsrcEmpAssessApplyDto apply);

    /**
     * 根据流程编号查询数据集合
     * @param instanceId 流程编号
     * @return list
     */
    List<OutsrcEmpAssessDto> queryListByInsId(String instanceId);

    /**
     * 增加1条
     * @param outsrcEmpAssess 对象
     * @return int
     */
     int addOutsrcingAssess(OutsrcEmpAssess outsrcEmpAssess);

    /**
     * 增加多条
     * @param outsrcEmpAssessList 对象
     * @return int
     */
    int batchAddOutsrcEmpAssess(List<OutsrcEmpAssessDto> outsrcEmpAssessList);

    /**
     * 修改信息
     * @param outsrcEmpAssess 对象
     * @return int
     */
     int updateOutsrcEmpAssess(OutsrcEmpAssess outsrcEmpAssess);

    /**
     * 根据流程编号修改
     * @param outsrcEmpAssess 对象
     * @return int
     */
     int updateByInsId(OutsrcEmpAssess outsrcEmpAssess);


}