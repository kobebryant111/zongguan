package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmEarlyCourseApplyInfoDto;
import com.work.ppm.application.dto.integratedmanager.PpmIntervieweeInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 提前入场申请详情表
 */
public interface IPpmEarlyCourseApplyInfoService {

    /**
     * 查询分页
     * @param ppmEarlyCourseApplyDto 对象
     * @param pager 分页
     * @param user 当前登录
     * @return List<PpmInterviewApplyDto>
     */
    List<PpmEarlyCourseApplyInfoDto> queryPager(PpmEarlyCourseApplyInfoDto ppmEarlyCourseApplyDto,
                                            Pager pager, CommOperatorDoLoginDto user) ;

    /**
     * 查询分页
     * @param ppmEarlyCourseApplyDto 对象
     * @param pager 分页
     * @param user 当前登录
     * @return List<PpmInterviewApplyDto>
     */
    List<PpmEarlyCourseApplyInfoDto> queryPmPager(PpmEarlyCourseApplyInfoDto ppmEarlyCourseApplyDto,
                                                Pager pager, CommOperatorDoLoginDto user) ;

    /**
     * 添加
     * @param applyInfos 集合
     * @return int
     */
    int addPpmEarlyCourseApplyInfo(List<PpmEarlyCourseApplyInfoDto> applyInfos) ;

    /**
     * 查询详情 - 集合
     * @param instanceId 流程编号
     * @return List<PpmEarlyCourseApplyInfoDto>
     */
    List<PpmEarlyCourseApplyInfoDto> queryListForInstanceId(String instanceId) ;

    /**
     * 查询详情 - 集合
     * @param ppmEarlyCourseApplyInfoDto 对象
     * @return List<PpmEarlyCourseApplyInfoDto>
     */
    List<PpmEarlyCourseApplyInfoDto> queryBaseList(PpmEarlyCourseApplyInfoDto ppmEarlyCourseApplyInfoDto) ;

    /**
     *  详情
     * @param cardNo 卡号
     * @return  PpmEarlyCourseApplyInfoDto
     */
    PpmEarlyCourseApplyInfoDto queryDetailCert(String cardNo);

    /**
     * 更新
     * @param earlyCourseApplyInfoDto 对象
     * @return Integer
     */
    Integer updatePpmEarlyCourseApplyInfo(PpmEarlyCourseApplyInfoDto earlyCourseApplyInfoDto) ;

    /**
     * 删除
     * @param instanceId 流程编号
     * @return Integer
     */
    Integer deleteByInstanceId(String instanceId);

    /**
     * 删除
     * @param instanceId 流程编号
     * @return Integer
     */
    Integer deleteInCardNo(List<PpmEarlyCourseApplyInfoDto> infoDtos,String instanceId);

    /**
     * 查询面试通过但未进入准入申请的人员信息
     * @param deptNo 部门编号
     * @return List<PpmEarlyCourseApplyInfoDto>
     */
    List<PpmIntervieweeInfoDto> interViews(String deptNo);

    /**
     * 导出
     * @param ppmInterviewApplyInfoDto 对象
     * @param user 登录用户
     * @return list
     */
    List<PpmEarlyCourseApplyInfoDto> exportExcel(PpmEarlyCourseApplyInfoDto ppmInterviewApplyInfoDto,
                                                 CommOperatorDoLoginDto user);
}
