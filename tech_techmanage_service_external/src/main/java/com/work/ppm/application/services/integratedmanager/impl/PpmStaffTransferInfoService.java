package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.integratedmanager.PpmStaffTransferInfoDto;
import com.work.ppm.application.model.integratedmanager.PpmStaffTransferInfo;
import com.work.ppm.application.model.integratedmanager.ext.PpmStaffTransferInfoExt;
import com.work.ppm.application.services.integratedmanager.IPpmStaffTransferInfoService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
  *  人员移交详情信息 接口实现类
  */
@Service("ppmStaffTransferInfoService")
public class PpmStaffTransferInfoService extends BaseDaoSupper implements IPpmStaffTransferInfoService {
    /**
      * 实体类路径
      */
    private static String PPMSTAFFTRANSFERINFO =
            "com.work.ppm.application.model.integratedmanager.PpmStaffTransferInfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;



    @Override
    public int insertPpmStaffTransferInfo(PpmStaffTransferInfoDto transferInfoDto) {
        PpmStaffTransferInfo transferInfo = JsonUtil.jsonToClass(transferInfoDto,PpmStaffTransferInfo.class);
        transferInfo.setId(getNextSequence(PpmStaffTransferInfo.class));
        return myBatisDao.executionPojo(
                PPMSTAFFTRANSFERINFO, transferInfo, IMyBatisDao.SQL.INSTER, "addPojo");
    }

    @Override
    public int addPpmStaffTransferInfo(List<PpmStaffTransferInfoDto> transferInfoDtos) {
        if (transferInfoDtos == null || transferInfoDtos.isEmpty()) {
            return -1;
        }
        transferInfoDtos.stream().forEach(item -> {
            PpmStaffTransferInfo transferInfo = JsonUtil.jsonToClass(item,PpmStaffTransferInfo.class);
            if (transferInfo.getId() == null) {
                // 如果需要请set 主键id
                transferInfo.setId(getNextSequence(PpmStaffTransferInfo.class));
                myBatisDao.executionPojo(
                        PPMSTAFFTRANSFERINFO, transferInfo, IMyBatisDao.SQL.INSTER, "addPojo");
            } else {
                this.updatePpmStaffTransferInfo(item);
            }
        });

        return 1;
    }


    @Override
    public List<PpmStaffTransferInfoDto> queryListForInstanceId(String instanceId) {
        PpmStaffTransferInfoDto staffTransferInfoDto = new PpmStaffTransferInfoDto();
        staffTransferInfoDto.setInstanceid(instanceId);
        return queryBaseList(staffTransferInfoDto);
    }

    @Override
    public List<PpmStaffTransferInfoDto> queryDetailList(PpmStaffTransferInfoDto ppmStaffTransferInfoDto) {
        PpmStaffTransferInfo ppmStaffTransferInfo =
                JsonUtil.jsonToClass(ppmStaffTransferInfoDto, PpmStaffTransferInfo.class);
        List<PpmStaffTransferInfo> data = myBatisDao.selectListT(PPMSTAFFTRANSFERINFO, ppmStaffTransferInfo,"getList");
        return JsonUtil.jsonToList(data,PpmStaffTransferInfoDto.class);
    }

    @Override
    public List<PpmStaffTransferInfoDto> queryBaseList(PpmStaffTransferInfoDto ppmStaffTransferInfoDto) {
        List<PpmStaffTransferInfoExt> list =
                myBatisDao.selectListT(PPMSTAFFTRANSFERINFO, ppmStaffTransferInfoDto, "queryBaseInfoList");
        return JsonUtil.jsonToList(list, PpmStaffTransferInfoDto.class);
    }

    @Override
    public int updatePpmStaffTransferInfo(PpmStaffTransferInfoDto ppmStaffTransferInfoDto) {
        PpmStaffTransferInfo ppmStaffTransferInfo =
                JsonUtil.jsonToClass(ppmStaffTransferInfoDto, PpmStaffTransferInfo.class);
        return myBatisDao.executionPojo(
                PPMSTAFFTRANSFERINFO, ppmStaffTransferInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deleteByInstanceId(String instanceId) {
        PpmStaffTransferInfo ppmStaffTransferInfo = new PpmStaffTransferInfo();
        ppmStaffTransferInfo.setInstanceid(instanceId);
        return myBatisDao.executionPojo(
                PPMSTAFFTRANSFERINFO, ppmStaffTransferInfo, IMyBatisDao.SQL.DELETE, "deleteByInstanceId");
    }

    @Override
    public Integer deleteInJobId(List<PpmStaffTransferInfoDto> infoDtos, String instanceId) {
        StringBuffer delVal = new StringBuffer();
        infoDtos.stream().filter(infoDto -> infoDto.getId() != null).collect(Collectors.toList()).
                forEach(infoDto -> delVal.append("'").append(infoDto.getId()).append("'").append(","));


        PpmStaffTransferInfoDto staffTransferInfoDto = new PpmStaffTransferInfoDto();
        if(StringUtils.isNotEmpty(delVal)){
            staffTransferInfoDto.setDelVal(delVal.substring(0,delVal.length()-1));
        }else {
            staffTransferInfoDto.setDelVal(null);
        }

        staffTransferInfoDto.setInstanceid(instanceId);

        return myBatisDao.executionPojo(
                PPMSTAFFTRANSFERINFO, staffTransferInfoDto, IMyBatisDao.SQL.DELETE, "deleteInJobId");
    }
}
