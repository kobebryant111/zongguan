package com.work.ppm.application.services.systemmanager.impl;

import com.work.ppm.application.model.rdmanager.CommSysPwDto;
import com.work.ppm.application.model.rdmanager.SyspicListDto;
import com.work.ppm.application.model.rdmanager.SystemListDto;
import com.work.ppm.application.services.systemmanager.ISystemListService;
import com.work.ppm.commons.dao.IMyBatisDao;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SystemListService")
public class SystemListService implements ISystemListService {

    /**
     * 实体类路径
     */
    private static String COMMDEPARTMENT = "com.work.ppm.application.model.rdmanager.SyspicListDto";

    private static int BATCHSIZE = 1000;
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public Integer deleteSystemListDto(SystemListDto systemListDto) {
        return myBatisDao.executionPojo(COMMDEPARTMENT, systemListDto, IMyBatisDao.SQL.DELETE, "deleteSystemListDto");
    }

    @Override
    public void insertSystemListDto(List<SystemListDto> systemListDto) {
        int totalSize = systemListDto.size();

        for (int i = 0; i < totalSize; i += BATCHSIZE) {
            List<SystemListDto> subList = systemListDto.subList(i, Math.min(i + BATCHSIZE, totalSize));
            myBatisDao.executionPojo(COMMDEPARTMENT, subList, IMyBatisDao.SQL.INSTER, "addSystemListDto");
        }

    }

    @Override
    public Integer deleteSyspicListDto(SyspicListDto syspicListDto) {
        return myBatisDao.executionPojo(COMMDEPARTMENT, syspicListDto, IMyBatisDao.SQL.DELETE, "deleteSyspicListDto");
    }

    @Override
    public void insertSyspicListDto(List<SyspicListDto> syspicListDtos) {
        int totalSize = syspicListDtos.size();

        for (int i = 0; i < totalSize; i += BATCHSIZE) {
            List<SyspicListDto> subList = syspicListDtos.subList(i, Math.min(i + BATCHSIZE, totalSize));
            myBatisDao.executionPojo(COMMDEPARTMENT, subList, IMyBatisDao.SQL.INSTER, "addSyspicListDto");
        }
    }

    @Override
    public List<CommSysPwDto> selectCommSysPwDto(int page) {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("startRow", (page - 1) * BATCHSIZE);
        map.put("endRow", page * BATCHSIZE);
        return myBatisDao.selectListT(COMMDEPARTMENT, map, "selectCommSysPwDto");
    }

    @Override
    public void updateCommSysPwDto(CommSysPwDto sysPwDto) {
        myBatisDao.executionPojo(COMMDEPARTMENT, sysPwDto, IMyBatisDao.SQL.DELETE, "commSysPwd");
        myBatisDao.executionPojo(COMMDEPARTMENT, sysPwDto, IMyBatisDao.SQL.DELETE, "commSytpsdKey");
    }
}
