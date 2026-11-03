package com.work.ppm.application.services.banktxmanager.impl;

import com.work.ppm.application.dto.banktxmanager.IntegInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.banktxmanager.IEmpTxApproverDetailService;
import com.work.ppm.application.services.banktxmanager.IEmpTxUnrecogIntegService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Slf4j
@Service
public class EmpTxApproverDetailService implements IEmpTxApproverDetailService {
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
    public void queryPagerForApprover(IntegInfoDto integInfoDto, Pager pager, CommOperatorDoLoginDto user) {
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
            map.put("state" , "1");
            map.put("empName" , user.getOperatorname());
            map.put("badge" , user.getUserid());
            myBatisDao.selectPager(INTEGINFO, map, pager, "queryPagerList");
        } catch (Exception e) {
            log.error("待审核积分查询异常,错误信息为:{}。", e);
        }
    }

}
