package com.work.ppm.application.services.insertinfo.impl;

import com.work.ppm.application.model.interpoints.CommInterPointsDto;
import com.work.ppm.application.services.insertinfo.IDeployInsertInfoService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 研发项目管理
 *
 */
@Service
@Slf4j
public class DeployInsertInfoService implements IDeployInsertInfoService {
    /**
     * 实体类路径
     */
    private static String INSERTINFO = "com.work.ppm.application.model.interpoints.CommInterPointsDto";
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public List<CommInterPointsDto> insertInfo(CommInterPointsDto commInterPointsDto){
        Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commInterPointsDto, CommInterPointsDto.class);
        return myBatisDao.selectListT(INSERTINFO, commRdEmployeeMap, "insertInfo");
    }

    @Override
    public void keepIhfo(CommInterPointsDto commInterPointsDto){
        if (StringUtil.isNotEmpty(commInterPointsDto.getPkId())) {
            Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commInterPointsDto, CommInterPointsDto.class);
            myBatisDao.executionPojo(INSERTINFO, commRdEmployeeMap, IMyBatisDao.SQL.UPDATE, "keepIhfo");
        }
    }

    @Override
    public void deleteIhfo(CommInterPointsDto commInterPointsDto){
        if (StringUtil.isNotEmpty(commInterPointsDto.getPkId())){
            Map<String, Object> commRdEmployeeMap = StringUtil.objectTOMap(commInterPointsDto, CommInterPointsDto.class);
            myBatisDao.executionPojo(INSERTINFO,commRdEmployeeMap,IMyBatisDao.SQL.DELETE,"deleteIhfo");
        }

    }
}
