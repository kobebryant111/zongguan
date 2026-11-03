package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.application.model.flowmanager.PpmAppWorkflowInfo;
import com.work.ppm.application.dto.flowmanager.PpmAppWorkflowInfoDto;
import com.work.ppm.application.services.flowmanager.IPpmAppWorkflowInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
  *  对接手机平台信息 接口实现类
  */
@Service("ppmAppWorkflowInfoService")
public class PpmAppWorkflowInfoService extends BaseDaoSupper implements IPpmAppWorkflowInfoService {
    /**
      * 实体类路径
      */
    private static String PPMAPPWORKFLOWINFO = "com.work.ppm.application.model.flowmanager.PpmAppWorkflowInfo";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;

    @Override
    public int addPpmAppWorkflowInfo(PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto) {
        PpmAppWorkflowInfo ppmAppWorkflowInfo = JsonUtil.jsonToClass(ppmAppWorkflowInfoDto, PpmAppWorkflowInfo.class);
        // 如果需要请set 主键id
        // ppmAppWorkflowInfo.setId(getNextSequence(PpmAppWorkflowInfo.class));
        myBatisDao.executionPojo(PPMAPPWORKFLOWINFO, ppmAppWorkflowInfo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
//    @Override
//    public PpmAppWorkflowInfoDto queryDetail(PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto) {
//        PpmAppWorkflowInfo ppmAppWorkflowInfo = JsonUtil.jsonToClass(ppmAppWorkflowInfoDto, PpmAppWorkflowInfo.class);
//        PpmAppWorkflowInfo data = myBatisDao.selectClass(PPMAPPWORKFLOWINFO, ppmAppWorkflowInfo,"queryDetail");
//        return JsonUtil.jsonToClass(data, PpmAppWorkflowInfoDto.class);
//    }

    @Override
    public PpmAppWorkflowInfoDto queryDetailForTaskId(String taskId) {
        PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto = new PpmAppWorkflowInfoDto();
//        ppmAppWorkflowInfoDto.setUserId(userId);
        ppmAppWorkflowInfoDto.setTaskId(taskId);

        PpmAppWorkflowInfo ppmAppWorkflowInfo = JsonUtil.jsonToClass(ppmAppWorkflowInfoDto, PpmAppWorkflowInfo.class);
        PpmAppWorkflowInfo data = myBatisDao.selectClass(PPMAPPWORKFLOWINFO, ppmAppWorkflowInfo,"queryDetailForTaskId");
        return JsonUtil.jsonToClass(data, PpmAppWorkflowInfoDto.class);
    }

    @Override
    public List<PpmAppWorkflowInfoDto> queryDetailList(PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto) {
        PpmAppWorkflowInfo ppmAppWorkflowInfo = JsonUtil.jsonToClass(ppmAppWorkflowInfoDto, PpmAppWorkflowInfo.class);
        List<PpmAppWorkflowInfo> data = myBatisDao.selectListT(PPMAPPWORKFLOWINFO, ppmAppWorkflowInfo,"getList");
        return JsonUtil.jsonToList(data, PpmAppWorkflowInfoDto.class);
    }

    @Override
    public PpmAppWorkflowInfoDto queryDetailForPushState(String pushState,String instanceId) {
        PpmAppWorkflowInfoDto appWorkflowInfoDto = new PpmAppWorkflowInfoDto();
        appWorkflowInfoDto.setInstanceId(instanceId);
        appWorkflowInfoDto.setPushStatus(pushState);
        List<PpmAppWorkflowInfoDto> appWorkflowInfoDtos = queryDetailList(appWorkflowInfoDto);
        if (!CollectionUtils.isEmpty(appWorkflowInfoDtos)) {
            return appWorkflowInfoDtos.get(0);
        }else {
            return null;
        }
    }

    @Override
    public int updatePpmAppWorkflowInfo(PpmAppWorkflowInfoDto ppmAppWorkflowInfoDto) {
        PpmAppWorkflowInfo ppmAppWorkflowInfo = JsonUtil.jsonToClass(ppmAppWorkflowInfoDto, PpmAppWorkflowInfo.class);
        return myBatisDao.executionPojo(PPMAPPWORKFLOWINFO, ppmAppWorkflowInfo, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

}
