package com.work.ppm.application.services.kpi;

import com.work.ppm.application.dto.kpi.MyKpiDbDto;
import com.work.ppm.application.dto.kpi.PpmKpiPmInfoDto;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.application.dto.kpi.PpmKpiPmDto;

/**
  * 考核主表
  */
public interface IPpmKpiPmService {

    /**
     * 分页
     * @param ppmKpiPmDto 对象
     * @param pager 分页对象
     */
    @Deprecated
    void queryPager(PpmKpiPmDto ppmKpiPmDto, Pager pager) ;

    /**
     * 添加
     * @param ppmKpiPmDto 对象
     * @return int 对象
     */
     @Deprecated
    int addPpmKpiPm(PpmKpiPmDto ppmKpiPmDto) ;

    /**
     * 查询详情
     * @param ppmKpiPmDto 对象
     * @return PpmKpiPmDto 对象
     */
    PpmKpiPmDto queryDetail(PpmKpiPmDto ppmKpiPmDto) ;

    /**
     * 更新
     * @param ppmKpiPmDto  对象
     * @return int 对象
     */
    int updatePpmKpiPm(PpmKpiPmDto ppmKpiPmDto) ;

    /**
     * 删除
     * @param ppmKpiPmDto 对象
     * @return int 对象
     */
    int deletePpmKpiPm(PpmKpiPmDto ppmKpiPmDto) ;

    /**
     * 发起上下半年员工考核
     * @param ppmKpiPmDto 对象
     * @return int 对象
     */
     int launchEmployee(PpmKpiPmDto ppmKpiPmDto,String userid);

    /**
     * 通过条件查询考核主记录数据
     * @param ppmKpiPmDto 对象
     * @return PpmKpiPmDto 对象
     */
    PpmKpiPmDto queryDetailByCondition(PpmKpiPmDto ppmKpiPmDto);

    /**
     * 发起经理层考核
     * @param ppmKpiPmDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int launchManagers(PpmKpiPmDto ppmKpiPmDto,String userid);

    /**
     * 发起主持工作经理
     * @param ppmKpiPmDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int launchWorkManager(PpmKpiPmDto ppmKpiPmDto,String userid);

    /**
     * 获取我的代办信息
     * @param myDbDto 对象
     * @param userid 对象
     * @return MyKpiDbDto 对象
     */
    MyKpiDbDto getMyDb(MyKpiDbDto myDbDto, String userid);

    /**
     * 提交考核指标
     * @param ppmKpiPmDto 对象
     * @return int 对象
     */
    int submitKpiDetail(PpmKpiPmDto ppmKpiPmDto);

    /**
     * 查询考核主记录信息
     * @param ppmKpiPmInfoDto 对象
     * @return PpmKpiPmInfoDto 对象
     */
    PpmKpiPmInfoDto queryKpiPmByCondition(PpmKpiPmInfoDto ppmKpiPmInfoDto);

    /**
     * 查询待审批数据
     * @param ppmKpiPmInfoDto 对象
     * @param pager 对象
     */
    void queryApprovePager(PpmKpiPmInfoDto ppmKpiPmInfoDto, Pager pager);

    /**
     * 考核评价整体提交
     * @param ppmKpiPmInfoDto 对象
     * @param userid 对象
     * @return int 对象
     */
    int totalSubmit (PpmKpiPmInfoDto ppmKpiPmInfoDto,String userid);
}
