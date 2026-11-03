package com.work.ppm.application.services.projectmanager.impl;

import com.work.ppm.application.dto.projectmanager.AapSystemPropertyDto;
import com.work.ppm.application.model.projectmanager.ext.AapSystemPropertyInfo;
import com.work.ppm.application.model.systemmanager.CommEmployee;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.projectmanager.IAapSystemPropertyService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 系统属性
  */
@Service("aapSystemPropertyService")
public class AapSystemPropertyService extends BaseDaoSupper implements IAapSystemPropertyService {
    /**
      * 实体类路径
      */
    private static String AAPSYSTEMPROPERTY = "com.work.ppm.application.model.projectmanager.AapSystemProperty";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 人员
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;

    @Override
    public List<AapSystemPropertyDto> queryList(String userId) {
        CommEmployee employeeDto = commEmployeeService.getCommEmployeeInfo(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("deptId", employeeDto.getDepid());
        // 查询业务字典
        List<EosDictEntry> list = eosDictEntryService.getTypeidForList("PPM_PWD_HQQX");
        list.stream().forEach(ii -> {
            map.put(ii.getDictid(),ii.getDictid());
        });

        List<AapSystemPropertyInfo> data = myBatisDao.selectListT(AAPSYSTEMPROPERTY, map,
        "getSystemsForUserId");
        List<AapSystemPropertyDto> item = JsonUtil.jsonToList(data, AapSystemPropertyDto
        .class);
        return item;
    }

}
