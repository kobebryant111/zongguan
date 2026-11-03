package com.work.ppm.application.services.banktxmanager.impl;

import com.work.ppm.application.dto.banktxmanager.IntegInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.services.banktxmanager.IEmpTxRecogDetailService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Map;


@Slf4j
@Service
public class EmpTxRecogDetailService implements IEmpTxRecogDetailService {
    /**
     * 实体类路径
     */
    private static String INTEGINFO = "com.work.ppm.application.model.banktxmanager.IntegInfo";

    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 分页查询
     * @param integInfoDto 对象
     * @param pager        分页对象
     * @param user
     */
    @Override
    public void queryPagerForRecog(IntegInfoDto integInfoDto, Pager pager, CommOperatorDoLoginDto user) {
        try {
            // 将时间查询条件转换为日期对象
            if (!ObjectUtils.isEmpty(integInfoDto.getEntryTime())) {
                integInfoDto.setEntryStartDate(DateUtil.strToDate(integInfoDto.getEntryTime()[0], "yyyy-MM-dd"));
                Date entryEndDate = DateUtil.strToDate(integInfoDto.getEntryTime()[1], "yyyy-MM-dd");
                integInfoDto.setEntryEndDate(DateUtil.addOrSubHour(entryEndDate, 23));
            } else {
                integInfoDto.setEntryStartDate(DateUtil.getFirstDayByMonth());
                integInfoDto.setEntryEndDate(DateUtil.getLasttDayByMonth());
            }
            Map<String, Object> map = JsonUtil.objectTOMap(integInfoDto, IntegInfoDto.class);
            map.put("state" , "2");
            map.put("empName" , user.getOperatorname());
            map.put("badge" , user.getUserid());
            myBatisDao.selectPager(INTEGINFO, map, pager, "queryPagerList");
        } catch (Exception e) {
            log.error("已确认积分查询异常,错误信息为:{}。", e);
        }
    }

}
