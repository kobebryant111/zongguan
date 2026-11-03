package com.work.ppm.application.services.integratedmanager;

import com.work.ppm.application.dto.integratedmanager.PpmAttendanceModifyDto;
import com.work.ppm.commons.ApiResult;
import com.work.ppm.commons.pager.Pager;

import java.util.List;

/**
 * 考勤修改流程
 */
public interface IPpmAttendanceModifyService {

    /**
     * 分页
     * @param ppmAttendanceModifyDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmAttendanceModifyDto ppmAttendanceModifyDto, Pager pager) ;

    /**
     * 添加
     * @param ppmAttendanceModifyDto 对象
     * @return int
     */
    int addPpmAttendanceModify(PpmAttendanceModifyDto ppmAttendanceModifyDto) ;

    /**
     * 查询详情
     * @param ppmAttendanceModifyDto 对象
     * @return PpmAttendanceModifyDto
     */
    PpmAttendanceModifyDto queryDetail(PpmAttendanceModifyDto ppmAttendanceModifyDto) ;

    /**
     * 更新
     * @param ppmAttendanceModifyDto  对象
     * @return int
     */
    @Deprecated
    int updatePpmAttendanceModify(PpmAttendanceModifyDto ppmAttendanceModifyDto) ;

    /**
     * 保存草稿
     * @param ppmAttendanceModifyDto 对象
     * @return string
     */
    ApiResult saveFlow(PpmAttendanceModifyDto ppmAttendanceModifyDto);

    /**
     * 流转
     * @param ppmAttendanceModifyDto 对象
     * @return string
     */
    String liuZhuan(PpmAttendanceModifyDto ppmAttendanceModifyDto);

    /**
     * 二级部主持经理审核
     * @param ppmAttendanceModifyDto 对象
     * @return string
     */
    String hostManagerReview(PpmAttendanceModifyDto ppmAttendanceModifyDto);

    /**
     * 分管总经理审批
     * @param ppmAttendanceModifyDto 对象
     * @return string
     */
    String generalMnager(PpmAttendanceModifyDto ppmAttendanceModifyDto);

    /**
     * 研发管理部确认
     * @param ppmAttendanceModifyDto 对象
     * @return string
     */
    String generalDept(PpmAttendanceModifyDto ppmAttendanceModifyDto);

    /**
     * 导出
     * @param ppmAttendanceModifyDto 对象
     * @return list
     */
    List<PpmAttendanceModifyDto> exportExcel(PpmAttendanceModifyDto ppmAttendanceModifyDto);
}
