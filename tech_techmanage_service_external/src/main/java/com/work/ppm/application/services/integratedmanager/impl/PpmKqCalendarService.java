package com.work.ppm.application.services.integratedmanager.impl;

import com.work.imex.util.StrUtils;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarExcelDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarHistoryDto;
import com.work.ppm.application.dto.systemmanager.CommOperatorDoLoginDto;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendar;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.ExcelUtil;
import com.work.ppm.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 考勤日历表 接口实现类
 *
 */
@Slf4j
@Service("ppmKqCalendarService")
public class PpmKqCalendarService extends BaseDaoSupper implements IPpmKqCalendarService {
    /**
     * 实体类路径
     */
    private static String PPMKQCALENDAR = "com.work.ppm.application.model.integratedmanager.PpmKqCalendar";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;

    /**
     * 考勤日历修改记录
     */
    @Autowired
    private PpmKqCalendarHistoryService ppmKqCalendarHistoryService;

    @Override
    public void queryPager(PpmKqCalendarDto ppmKqCalendarDto, Pager pager) {
        // 将时间查询条件转换为日期对象
        if (!ObjectUtils.isEmpty(ppmKqCalendarDto.getEntryTime())) {
            ppmKqCalendarDto.setEntryStartDate(DateUtil.strToDate(ppmKqCalendarDto.getEntryTime()[0], "yyyy-MM-dd"));
            Date entryEndDate = DateUtil.strToDate(ppmKqCalendarDto.getEntryTime()[1], "yyyy-MM-dd");
            ppmKqCalendarDto.setEntryEndDate(DateUtil.addOrSubHour(entryEndDate, 23));
        } else {
            ppmKqCalendarDto.setEntryStartDate(DateUtil.getFirstDayByMonth());
            ppmKqCalendarDto.setEntryEndDate(DateUtil.getLasttDayByMonth());
        }
        Map<String, Object> map = JsonUtil.objectTOMap(ppmKqCalendarDto, PpmKqCalendarDto.class);
        myBatisDao.selectPager(PPMKQCALENDAR, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmKqCalendar(PpmKqCalendarDto ppmKqCalendarDto) {
        PpmKqCalendar ppmKqCalendar = JsonUtil.jsonToClass(ppmKqCalendarDto, PpmKqCalendar.class);
        myBatisDao.executionPojo(PPMKQCALENDAR, ppmKqCalendar, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmKqCalendarDto queryDetail(PpmKqCalendarDto ppmKqCalendarDto) {
        PpmKqCalendar ppmKqCalendar = JsonUtil.jsonToClass(ppmKqCalendarDto, PpmKqCalendar.class);
        PpmKqCalendar data = myBatisDao.selectClass(PPMKQCALENDAR, ppmKqCalendar, "queryDetail");
        PpmKqCalendarDto item = JsonUtil.jsonToClass(data, PpmKqCalendarDto.class);
        return item;
    }

    @Override
    public int updatePpmKqCalendar(PpmKqCalendarDto ppmKqCalendarDto) {
        PpmKqCalendar ppmKqCalendar = JsonUtil.jsonToClass(ppmKqCalendarDto, PpmKqCalendar.class);
        return myBatisDao.executionPojo(PPMKQCALENDAR, ppmKqCalendar, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmKqCalendar(PpmKqCalendarDto ppmKqCalendarDto) {
        PpmKqCalendar ppmKqCalendar = JsonUtil.jsonToClass(ppmKqCalendarDto, PpmKqCalendar.class);
        return myBatisDao.executionPojo(PPMKQCALENDAR, ppmKqCalendar, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    public boolean importExcel(MultipartFile file, CommOperatorDoLoginDto user) {
        try {
            List<PpmKqCalendarExcelDto> data = ExcelUtil.importExcel(file, PpmKqCalendarExcelDto.class);
            //去除重复项，并清理无效数据
            data = data.stream().distinct().filter(excelDto -> null != excelDto.getCalendarDate()
                    && null != excelDto.getDayOfCalendar()
                    && null != excelDto.getYearOfCalendar()).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(data)) {
                throw new BizException(ApiCode.FAIL, "导入失败，请检查导入模板！");
            }
            //添加操作历史
            this.addPpmKqCalendarHistory(user, "进行节假日数据导入");
            //处理导入信息
            int year = DateUtil.getYear();
            for (PpmKqCalendarExcelDto excelDto : data) {
                // 只导入当年之内的数据
                if (StrUtils.isNotBlank(excelDto.getIsHoliday()) && excelDto.getYearOfCalendar().intValue() >= year) {
                    PpmKqCalendar ppmKqCalendar = JsonUtil.jsonToClass(excelDto, PpmKqCalendar.class);
                    PpmKqCalendar entity = myBatisDao.selectClass(PPMKQCALENDAR, ppmKqCalendar, "queryDetail");
                    PpmKqCalendarDto ppmKqCalendarDto = JsonUtil.jsonToClass(excelDto, PpmKqCalendarDto.class);
                    if (ObjectUtils.isEmpty(entity)) {
                        this.addPpmKqCalendar(ppmKqCalendarDto);
                    } else {
                        if (null != ppmKqCalendarDto.getIsHoliday()) {
                            if (!entity.getIsHoliday().equals(ppmKqCalendarDto.getIsHoliday())) {
                                this.updatePpmKqCalendar(ppmKqCalendarDto);
                                String dateStr = DateUtil.dateToStr(ppmKqCalendarDto.getCalendarDate(), "yyyy-MM-dd");
                                String transferStr = entity.getIsHoliday().equals("Y") ? "从节假日修改为工作日" : "从工作日修改为节假日";
                                //添加操作历史
                                this.addPpmKqCalendarHistory(user, "将" + dateStr + transferStr);
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            throw new BizException(ApiCode.FAIL, "导入失败，请检查导入模板！");
        }
    }

    @Async
    @Override
    public void addPpmKqCalendarHistory(CommOperatorDoLoginDto user, String content) {
        PpmKqCalendarHistoryDto history = new PpmKqCalendarHistoryDto();
        history.setId(getNextSequence(PpmKqCalendar.class));
        history.setUserId(user.getUserid());
        history.setUserName(user.getOperatorname());
        history.setUpdateDate(new Date());
        history.setContent(content);
        ppmKqCalendarHistoryService.addPpmKqCalendarHistory(history);
    }
}
