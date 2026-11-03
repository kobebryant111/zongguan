package com.work.ppm.application.services.banktxmanager.impl;

import com.work.ppm.application.dto.banktxmanager.IntegInfoDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.banktxmanager.PpmNumAprovalDto;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.model.onlinemanager.ext.PpmTaskplanInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.services.banktxmanager.IEmpTxUnrecogIntegService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.commons.utils.BankTxUtil;
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
public class EmpTxUnrecogIntegService implements IEmpTxUnrecogIntegService {
    /**
     * 实体类路径
     */
    private static String INTEGINFO = "com.work.ppm.application.model.banktxmanager.IntegInfo";
    /**
     * 节假日信息实体类路径
     */
    private static String PPMKQCALENDAR = "com.work.ppm.application.model.integratedmanager.PpmKqCalendar";
    /**
     * 员工实体类路径
     */
    private static String COMMEMPLOYEE = "com.work.ppm.application.model.systemmanager.CommEmployee";
    /**
     * 实体类路径
     */
    private static String DETAILSLEAVE = "com.work.ppm.application.model.banktxmanager.PpmKepAprovalDto";


    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 分页查询
     *
     * @param integInfoDto 对象
     * @param pager        分页对象
     * @param user
     */
    @Override
    public void queryPager(IntegInfoDto integInfoDto, Pager pager, CommOperatorDoLoginDto user) {
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
            map.put("state", "0");
            map.put("empName", user.getOperatorname());
            map.put("badge", user.getUserid());
            myBatisDao.selectPager(INTEGINFO, map, pager, "queryPagerList");
        } catch (Exception e) {
            log.error("未确认积分查询异常,错误信息为:{}。", e);
        }
    }

    /**
     * 删除数据
     *
     * @param detailId 对象
     * @return
     */
    @Override
    public int delete(String detailId) {
        int i = 0;
        //修改状态
        try {
            i = updateState(detailId, "9");
        } catch (Exception e) {
            log.error("数据删除出现异常,异常信息为:{}", e);
        }
        return i;
    }

    /**
     * 保存修改信息
     *
     * @param integInfoDto 对象
     * @return
     */
    @Override
    public int saveInfo(IntegInfoDto integInfoDto) {
        Map<String, Object> queryMap = JsonUtil.objectTOMap(integInfoDto, IntegInfoDto.class);
        int i = 0;
        try {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String format = now.format(formatter);
            queryMap.put("updateTime", format);
            //判断结束日期是否会超过第二天早上5点
            String nextDayMoring5 = DateUtil.getNextDayMorning(queryMap.get("startTime").toString(), 5, 1);
            long differenceTime = BankTxUtil.getTimeDifference(queryMap.get("endTime").toString(),
                    nextDayMoring5, "yyyy-MM-dd HH:mm:ss");
            if (differenceTime < 0) {
                return -1;
            }
            //判断是否为节假日
            String isHoliday = judgIsHoliday(integInfoDto.getStartTime());
            queryMap.put("dataType", isHoliday);
            //计算积分
            Map resultMap = BankTxUtil.computeIntegNum(integInfoDto.getStartTime(), integInfoDto.getEndTime(), isHoliday);
            queryMap.put("integNum", resultMap.get("integNum"));
            queryMap.put("state", "0");
            // 判断积分是否已经够了.
            Map<String, Object> integMap = new HashMap<>();
            integMap.put("state", '2');
            integMap.put("badge", integInfoDto.getBadge());
            integMap.put("startFormatData", DateUtil.strToFormatDate(queryMap.get("startTime").toString(),
                    "yyyy-MM-dd HH:mm:ss", "yyyy"));
            PpmNumAprovalDto ppmNumAprovalDto = myBatisDao.selectClass(DETAILSLEAVE, integMap, "getIntegNum");
            if (ppmNumAprovalDto.getGenerateLeave() >= 10 ||
                    (ppmNumAprovalDto.getGenerateLeave() + Double.parseDouble(queryMap.get("integNum").toString())) > 10) {
                return 2;
            }
            i = myBatisDao.executionPojo(INTEGINFO, queryMap, IMyBatisDao.SQL.UPDATE, "updateInfo");
        } catch (Exception e) {
            log.error("未确认积分编辑保存数据异常,异常信息为:{}", e);
        }
        return i;
    }

    /**
     * 补登记插入信息
     *
     * @param integInfoDto 对象
     * @return
     */
    @Override
    public int insertInfo(IntegInfoDto integInfoDto) {
        Map<String, Object> queryMap = JsonUtil.objectTOMap(integInfoDto, IntegInfoDto.class);
        int i = 0;
        //添加主键信息
        queryMap.put("detailId", UUID.randomUUID().toString());
        try {
            //添加创建时间
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String format = now.format(formatter);
            queryMap.put("createTime", format);
            //添加用户姓名和工号
            queryMap.put("empName", integInfoDto.getEmpName());
            queryMap.put("badge", integInfoDto.getBadge());
            queryMap.put("state", "0");
            //判断结束日期是否会超过第二天早上5点
            String nextDayMoring5 = DateUtil.getNextDayMorning(queryMap.get("startTime").toString(), 5, 1);
            long differenceTime = BankTxUtil.getTimeDifference(queryMap.get("endTime").toString(),
                    nextDayMoring5, "yyyy-MM-dd HH:mm:ss");
            if (differenceTime < 0) {
                return -1;
            }
            //判断是否为节假日
            String isHoliday = judgIsHoliday(integInfoDto.getStartTime());
            //计算积分
            Map resultMap = BankTxUtil.computeIntegNum(integInfoDto.getStartTime(), integInfoDto.getEndTime(), isHoliday);
            queryMap.put("integNum", resultMap.get("integNum"));
            queryMap.put("dataType", isHoliday);

            queryMap.put("startFormatData", DateUtil.strToFormatDate(queryMap.get("startTime").toString(),
                    "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
            // 判断积分是否已经够了.
            Map<String, Object> integMap = new HashMap<>();
            integMap.put("state", '2');
            integMap.put("badge", integInfoDto.getBadge());
            integMap.put("startFormatData", DateUtil.strToFormatDate(queryMap.get("startTime").toString(),
                    "yyyy-MM-dd HH:mm:ss", "yyyy"));
            PpmNumAprovalDto ppmNumAprovalDto = myBatisDao.selectClass(DETAILSLEAVE, integMap, "getIntegNum");
            if (ppmNumAprovalDto.getGenerateLeave() >= 10 ||
                    (ppmNumAprovalDto.getGenerateLeave() + Double.parseDouble(queryMap.get("integNum").toString())) > 10) {
                return 2;
            }
            i = myBatisDao.executionPojo(INTEGINFO, queryMap, IMyBatisDao.SQL.INSTER, "insertInfo");
        } catch (ParseException e) {
            log.error("加班积分计算异常,异常信息为:{}", e);
        }
        return i;
    }


    /**
     * 批量提交修改状态
     *
     * @param ids
     * @return
     */
    @Override
    public int submitInfo(String ids) {
        int i = 0;
        //修改状态
        try {
            i = updateState(ids, "1");
            //提交添加上级部门领导及工号
            if (StringUtil.isStringNotEmpty(ids) && ids.contains(",")) {
                String[] split = ids.split(",");
                for (String id : split) {
                    getDepartMent(id);
                }
            } else if (StringUtil.isStringNotEmpty(ids) && !ids.contains(",")) {
                getDepartMent(ids);
            }
        } catch (RuntimeException runE) {
            log.error("状态修改出现异常,异常信息为:{}", runE);
        }
        return i;
    }

    /**
     * 提交修改信息
     *
     * @param detailId 对象
     * @return
     */
    @Override
    public int submit(String detailId) {
        int i = 0;
        try {
            //修改状态
            i = updateState(detailId, "1");
            //修改审批人与审批人工号
            getDepartMent(detailId);
        } catch (RuntimeException runE) {
            log.error("状态修改出现异常,异常信息为:{}", runE);
        }
        return i;
    }

    /**
     * 添加审批人
     *
     * @param detailId
     * @return
     */
    public int getDepartMent(String detailId) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("detailId", detailId);
        List<CommEmployee> getReportto = myBatisDao.selectListT(COMMEMPLOYEE, queryMap, "getReportto");
        if (getReportto != null && getReportto.size() > 0) {
            queryMap.put("integCurrentApprover", getReportto.get(0).getEmpname());
        }
        return myBatisDao.executionPojo(INTEGINFO, queryMap, IMyBatisDao.SQL.UPDATE, "updateInfo");
    }

    /**
     * 修改状态
     *
     * @param ids
     * @param state
     * @return
     */
    public int updateState(String ids, String state) throws RuntimeException {
        Map<String, Object> queryMap = new HashMap<>();
        List<String> idList = new ArrayList<>();

        if (StringUtil.isStringNotEmpty(ids) && ids.contains(",")) {
            String[] split = ids.split(",");
            idList = Arrays.asList(split);
        } else if (StringUtil.isStringNotEmpty(ids) && !ids.contains(",")) {
            idList.add(ids);
        }
        queryMap.put("idList", idList);
        queryMap.put("state", state);
        return myBatisDao.executionPojo(INTEGINFO, queryMap, IMyBatisDao.SQL.UPDATE, "updateState");
    }

    /**
     * 判断是否为节假日
     *
     * @param date
     * @return
     */
    @Override
    public String judgIsHoliday(String date) {
        if (date == null && "".equals(date)) {
            return date;
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = inputFormat.parse(date);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formatDate = outputFormat.format(parse);
            String[] split = formatDate.split("-");
            //查询节假日信息表,判断当前时间是否为节假日信息
            Map queryMap = new HashMap<>();
            queryMap.put("yearOfCalendar", split[0]);
            queryMap.put("monthOfYear", split[1]);
            queryMap.put("dayOfMonth", split[2]);
            List<PpmKqCalendar> queryIsHoliday = myBatisDao.selectListT(PPMKQCALENDAR, queryMap, "queryIsHoliday");
            if (queryIsHoliday != null && queryIsHoliday.size() != 0) {
                String isHoliday = queryIsHoliday.get(0).getIsHoliday();
                if (StringUtil.isStringNotEmpty(isHoliday) && "N".equals(isHoliday)) {
                    return "0";
                } else if (StringUtil.isStringNotEmpty(isHoliday) && "Y".equals(isHoliday)) {
                    return "1";
                }
            }
        } catch (ParseException e) {
            log.error("判断节假日信息出现异常:{}", e);
        }
        return "";
    }


}
