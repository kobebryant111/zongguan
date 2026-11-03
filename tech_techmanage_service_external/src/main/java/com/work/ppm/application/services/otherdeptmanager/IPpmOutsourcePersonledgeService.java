package com.work.ppm.application.services.otherdeptmanager;

import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.otherdeptmanager.PpmOutsourcePersonledgeDto;

import java.util.List;

/**
  *  非科技外包人员台账表 接口描述
  */
public interface IPpmOutsourcePersonledgeService {

    /**
     * 分页
     * @param ppmOutsourcePersonledgeDto 对象
     * @param pager 分页对象
     */
    void queryPager(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto, Pager pager,CommOperatorDoLoginDto user) ;

    /**
     * 添加
     * @param ppmOutsourcePersonledgeDto 对象
     * @return int
     */
    int addPpmOutsourcePersonledge(PpmOutsourcePersonledgeDto  ppmOutsourcePersonledgeDto) ;

    /**
     * 查询详情
     * @param ppmOutsourcePersonledgeDto 对象
     * @return PpmOutsourcePersonledgeDto
     */
    PpmOutsourcePersonledgeDto queryDetail(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) ;

    /**
     * 查询详情
     * @param jobId 对象
     * @return PpmOutsourcePersonledgeDto
     */
    PpmOutsourcePersonledgeDto queryDetailByJobId(String jobId) ;

    /**
     * 查询list
     * @param ppmOutsourcePersonledgeDto 对象
     * @return List<PpmOutsourcePersonledgeDto>
     */
    List<PpmOutsourcePersonledgeDto> queryDetailList(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto);

    /**
     * 更新
     * @param ppmOutsourcePersonledgeDto  对象
     * @return int
     */
    int updatePpmOutsourcePersonledge(PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto) ;

    /**
     * 导出表格
     * @param ppmOutsourcePersonledgeDto 对象
     * @param user 用户
     * @return list
     */
    List<PpmOutsourcePersonledgeDto> exportExcel(
            PpmOutsourcePersonledgeDto ppmOutsourcePersonledgeDto, CommOperatorDoLoginDto user);

    /**
     * 导出表格
     * @param personledgeDto 对象
     * @return list
     */
    void pushEmptyPhoto(PpmOutsourcePersonledgeDto personledgeDto);
}
