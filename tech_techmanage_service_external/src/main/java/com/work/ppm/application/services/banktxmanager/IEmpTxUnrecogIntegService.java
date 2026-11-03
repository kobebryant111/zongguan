package com.work.ppm.application.services.banktxmanager;


import com.work.ppm.application.dto.banktxmanager.IntegInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.commons.pager.Pager;

/**
 * 行员调休信息表 接口描述
 *
 */
public interface IEmpTxUnrecogIntegService {
    /**
     * 分页
     * @param integInfoDto 对象
     * @param pager        分页对象
     */
    void queryPager(IntegInfoDto integInfoDto, Pager pager, CommOperatorDoLoginDto user);

    /**
     * 删除
     * @param detailId 对象
     */
    int delete(String detailId);

    /**
     * 保存信息
     * @param integInfoDto 对象
     */
    int saveInfo(IntegInfoDto integInfoDto);

    /**
     * 添加信息
     * @param integInfoDto 对象
     */
    int insertInfo(IntegInfoDto integInfoDto);

    /**
     * 提交积分信息
     * @param ids
     * @return
     */
    int submitInfo(String ids);

    /**
     * 数据提交
     * @param detailId 对象
     */
    int submit(String detailId);

    /**
     * 判断是否是节假日
     * @param startTime 对象
     */
    String judgIsHoliday(String startTime);
}
