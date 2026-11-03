package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.dto.systemmanager.AapSystemResponsibilityDto;
import com.work.ppm.application.model.systemmanager.AapSystemResponsibility;
import com.work.ppm.application.services.systemmanager.IAapSystemResponsibilityService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 系统负责人
 */
@Service("aapSystemResponsibilityService")
public class AapSystemResponsibilityService extends BaseDaoSupper implements IAapSystemResponsibilityService {
    /**
      * 实体类路径
      */
    private static String AAPSYSTEMRESPONSIBILITY = "" +
            "com.work.ppm.application.model.systemmanager.AapSystemResponsibility";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;


    @Override
    public AapSystemResponsibilityDto queryDetail(String sysNo) {
        if(!StringUtil.isNotBlank(sysNo)) {
            return null;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("sysNo", sysNo);
        String uniqueName = "getkejiByResId";
        AapSystemResponsibility item = myBatisDao.selectClass(AAPSYSTEMRESPONSIBILITY,map, uniqueName);
        AapSystemResponsibilityDto dto = JsonUtil.jsonToClass(item, AapSystemResponsibilityDto.class);
        return dto;
    }

    @Override
    public AapSystemResponsibilityDto queryDetail(AapSystemResponsibilityDto aapSystemResponsibilityDto) {
        AapSystemResponsibility aapSystemResponsibility =
                JsonUtil.jsonToClass(aapSystemResponsibilityDto, AapSystemResponsibility.class);
        AapSystemResponsibility data = myBatisDao.selectClass(AAPSYSTEMRESPONSIBILITY, aapSystemResponsibility,
        "queryDetail");
        AapSystemResponsibilityDto item = JsonUtil.jsonToClass(data, AapSystemResponsibilityDto
        .class);
        return item;
    }

    @Override
    public List<AapSystemResponsibilityDto> querySystemHeadAb(String sysNo,String instanceid) {
        Map<String,Object> map = new HashMap<>();
        if(!StringUtil.isNotEmpty(sysNo)) {
            return null;
        }
        StringJoiner joiner = new StringJoiner("','","'","'");
        String[] sysNoStr = sysNo.split(",");
        for (int i = sysNoStr.length - 1; i >= 0; i--) {
            joiner.add(sysNoStr[i]);
        }
        map.put("systemId", joiner.toString());
        map.put("refinstanceid", instanceid);
        List<AapSystemResponsibility> item = myBatisDao.selectListT(AAPSYSTEMRESPONSIBILITY, map,"getSystemHeadAB");
        List<AapSystemResponsibilityDto> dto = JsonUtil.jsonToList(item,AapSystemResponsibilityDto.class);
        return dto;
    }

    @Override
    public List<AapSystemResponsibilityDto> querySystemHeadAb(String sysNo) {
        return querySystemHeadAb(sysNo,"");
    }

    @Override
    public AapSystemResponsibilityDto getSysKeji(AapSystemResponsibilityDto aapSystemResDto) {
        AapSystemResponsibility aapSystemResponsibility =
                JsonUtil.jsonToClass(aapSystemResDto, AapSystemResponsibility.class);
        AapSystemResponsibility data = myBatisDao.selectClass(AAPSYSTEMRESPONSIBILITY, aapSystemResponsibility,
                "getSysKeji");
        AapSystemResponsibilityDto item = JsonUtil.jsonToClass(data, AapSystemResponsibilityDto
                .class);
        return item;
    }

}
