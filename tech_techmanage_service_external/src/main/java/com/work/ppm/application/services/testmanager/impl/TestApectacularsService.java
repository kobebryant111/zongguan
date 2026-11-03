package com.work.ppm.application.services.testmanager.impl;

import com.work.ppm.application.dto.testmanager.TestApectacularsDto;
import com.work.ppm.application.dto.testmanager.TestApectacularsInfoExportDto;
import com.work.ppm.application.services.testmanager.ITestApectacularsService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.DateUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * packageName com.work.ppm.application.services.testmanager.impl
 *
 * @version JDK 8
 * @className TestApectacularsService
 * @date 2024/7/30
 * @description 测试看板服务类
 */
@Service("testApectacularsService")
@Slf4j
public class TestApectacularsService implements ITestApectacularsService {
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 实体类路径
     */
    private static String TESTAPECTACULARS = "com.work.ppm.application.model.testmanager.TestApectaculars";

    /**
     * @param testApectacularsDto: 对象
     * @param pager: 分页参数
     * @return void
     * @date 2024/7/30
     * @version JDK 8
     * @description 分页查询
     */
    @Override
    public void queryPager(TestApectacularsDto testApectacularsDto, Pager pager) {
        try {
            // 将时间查询条件转换为日期对象
            if (!ObjectUtils.isEmpty(testApectacularsDto.getEntryTime())) {
                testApectacularsDto.setEntryStartDate(DateUtil.strToDate(testApectacularsDto.getEntryTime()[0], "yyyy-MM-dd"));
                Date entryEndDate = DateUtil.strToDate(testApectacularsDto.getEntryTime()[1], "yyyy-MM-dd");
                testApectacularsDto.setEntryEndDate(DateUtil.addOrSubHour(entryEndDate, 23));
            } else {
                testApectacularsDto.setEntryStartDate(DateUtil.getFirstDayByMonth());
                testApectacularsDto.setEntryEndDate(DateUtil.getLasttDayByMonth());
            }
            Map<String,Object> map = JsonUtil.objectTOMap(testApectacularsDto, TestApectacularsDto.class);
            myBatisDao.selectPager(TESTAPECTACULARS, map,pager,"queryPagerList");
        } catch (Exception e) {
            log.error("测试看板信息查询异常,错误信息为:{}。", e);
        }
    }

    /**
     * @param testApectacularsDto: 对象
     * @return List < TestApectacularsInfoExportDto>
     * @date 2024/9/11
     * @version JDK 8
     * @description 测试看板导出
     */
    @Override
    public List<TestApectacularsInfoExportDto> queryPagerExcel(TestApectacularsDto testApectacularsDto) {
        List<TestApectacularsInfoExportDto> repsJson = null;
        try {
            // 将时间查询条件转换为日期对象
            if (!ObjectUtils.isEmpty(testApectacularsDto.getEntryTime())) {
                testApectacularsDto.setEntryStartDate(DateUtil.strToDate(testApectacularsDto.getEntryTime()[0], "yyyy-MM-dd"));
                Date entryEndDate = DateUtil.strToDate(testApectacularsDto.getEntryTime()[1], "yyyy-MM-dd");
                testApectacularsDto.setEntryEndDate(DateUtil.addOrSubHour(entryEndDate, 23));
            } else {
                testApectacularsDto.setEntryStartDate(DateUtil.getFirstDayByMonth());
                testApectacularsDto.setEntryEndDate(DateUtil.getLasttDayByMonth());
            }
            Map<String,Object> map = JsonUtil.objectTOMap(testApectacularsDto, TestApectacularsDto.class);
            List<TestApectacularsDto> testApectacularsDtoList = myBatisDao.selectListT(TESTAPECTACULARS, map, "queryPagerList");
            repsJson = JsonUtil.jsonToList(testApectacularsDtoList, TestApectacularsInfoExportDto.class);
        } catch (Exception e) {
            log.error("未确认积分导出异常,错误信息为:{}。", e);
        }
        return repsJson;
    }
}
