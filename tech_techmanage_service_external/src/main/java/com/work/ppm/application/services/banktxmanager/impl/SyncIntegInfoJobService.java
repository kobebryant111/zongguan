package com.work.ppm.application.services.banktxmanager.impl;

import com.alibaba.fastjson.JSON;
import com.work.ppm.application.dto.bankmamager.PpmEmpKqRecordDto;
import com.work.ppm.application.model.bankmanager.PpmEmpKqRecord;
import com.work.ppm.application.model.banktxmanager.PpmNumAprovalDto;
import com.work.ppm.application.quartz.interfaces.ISyncIntegInfoJobService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.BankTxUtil;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service("syncIntegInfoJobService")
public class SyncIntegInfoJobService implements ISyncIntegInfoJobService {
    /**
     * 实体类路径
     */
    private static final String PPMEMPKQRECORD = "com.work.ppm.application.model.bankmanager.PpmEmpKqRecord";
    /**
     * 实体类路径
     */
    private static String INTEGINFO = "com.work.ppm.application.model.banktxmanager.IntegInfo";
    /**
     * 实体类路径
     */
    private static String DETAILSLEAVE = "com.work.ppm.application.model.banktxmanager.PpmKepAprovalDto";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public void syncKqInfoJob() throws IOException, ParseException {
        Map<String , Object> map = new HashMap<>();
        //获取前一天的日期
        String beforeDate = StringUtil.getbeforeDate(-1);
        map.put("attendanceDate" , beforeDate);
        //1.查询出当前的所有的考勤信息
        List<PpmEmpKqRecord> list = myBatisDao.selectListT(PPMEMPKQRECORD, map , "getEmpKqList");
        log.info("查询到待同步的考勤信息共:" + list.size() + "条。");
        if(list == null && list.size() <= 0) {
            return;
        }
        //添加创建时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = now.format(formatter);
        //2.根据条件判断插入积分信息表
        for (PpmEmpKqRecord emp : list) {
            try {
                Map<String, Object> queryMap = new HashMap<>();
                String startTime = StringUtil.formatDate(emp.getWorkingTime(), "yyyy-MM-dd HH:mm:ss");
                String endTime = StringUtil.formatDate(emp.getClosingTime(), "yyyy-MM-dd HH:mm:ss");
                String dataType = emp.getIsHoliday();
                String empName = emp.getUserName();
                String badge = emp.getJobId();
                queryMap.put("detailId", UUID.randomUUID().toString());
                queryMap.put("startTime", startTime);
                queryMap.put("endTime", endTime);

                //判断结束日期是否会超过第二天早上5点
                String nextDayMoring5 = DateUtil.getNextDayMorning(startTime , 5 , 1);
                long differenceTime = BankTxUtil.getTimeDifference(endTime,
                        nextDayMoring5, "yyyy-MM-dd HH:mm:ss");
                if (differenceTime < 0) {
                    log.error("同步考勤数据到积分表时出现异常,结束时间不正确,不能超过第二天凌晨五点!当前异常数据项为:{}" , JSON.toJSONString(emp));
                    continue;
                }
                queryMap.put("dataType", dataType);
                queryMap.put("empName", empName);
                queryMap.put("badge", badge);
                queryMap.put("registerData", StringUtil.formatDate(emp.getAttendanceDate(), "yyyy-MM-dd"));
                queryMap.put("state", "0");
                queryMap.put("createTime", format);
                Map<String, Object> resultMap = BankTxUtil.computeIntegNum(startTime, endTime, dataType);
                queryMap.put("integNum", resultMap.get("integNum"));
                queryMap.put("startFormatData", DateUtil.strToFormatDate(startTime ,
                        "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
                // 判断积分是否已经够了.
                Map<String, Object> integMap = new HashMap<>();
                integMap.put("state" , '2');
                integMap.put("badge" , badge);
                integMap.put("startFormatData" , DateUtil.strToFormatDate(queryMap.get("startTime").toString() ,
                        "yyyy-MM-dd HH:mm:ss" , "yyyy"));
                PpmNumAprovalDto ppmNumAprovalDto = myBatisDao.selectClass(DETAILSLEAVE, integMap, "getIntegNum");
                if (ppmNumAprovalDto.getGenerateLeave() >= 10 ||
                        (ppmNumAprovalDto.getGenerateLeave() + Double.parseDouble(queryMap.get("integNum").toString())) > 10) {
                    log.error("同步考勤数据到积分表时出现异常,当前已经超过积分添加上限 , 当前异常数据项为:{}" , JSON.toJSONString(emp));
                    continue;
                }
                myBatisDao.executionPojo(INTEGINFO, queryMap, IMyBatisDao.SQL.INSTER, "insertInfo");
            } catch (Exception e) {
                e.printStackTrace();
                log.error("同步考勤数据到积分表时出现异常,异常信息为:{} , 当前出现异常的数据项为:{}", e, JSON.toJSONString(emp));
            }
        }
    }
}
