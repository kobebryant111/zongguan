package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.flowmanager.PpmNewflowHistorylogDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.model.flowmanager.PpmNewflowHistorylog;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.flowmanager.IPpmNewflowHistorylogService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
  * 历史流程日志
  */
@Service("ppmNewflowHistorylogService")
public class PpmNewflowHistorylogService extends BaseDaoSupper implements IPpmNewflowHistorylogService {
    /**
      * 实体类路径
      */
    private static String PPMNEWFLOWHISTORYLOG = "com.work.ppm.application.model.flowmanager.PpmNewflowHistorylog";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 新应用流程日志接口
     */
    @Autowired
    private IPpmFlowLogService ppmFlowLogService;

    @Override
    public int addPpmNewflowHistorylog(PpmNewflowHistorylogDto ppmNewflowHistorylogDto) {
        PpmNewflowHistorylog ppmNewflowHistorylog = JsonUtil.jsonToClass(ppmNewflowHistorylogDto, PpmNewflowHistorylog.class);
        Long id = getNextSequence(PpmNewflowHistorylog.class);
        // 如果需要请set 主键id
        ppmNewflowHistorylog.setId(-id);
        myBatisDao.executionPojo(PPMNEWFLOWHISTORYLOG, ppmNewflowHistorylog, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public int deletePpmNewflowHistorylog(PpmNewflowHistorylogDto ppmNewflowHistorylogDto) {
        return this.updatePpmNewflowHistorylog(ppmNewflowHistorylogDto);
    }
    @Transactional("defaultTx")
    @Override
    public int addHisLog(String flowcaseno) {
        List<PpmFlowLogDto> list = ppmFlowLogService.queryDetaiList(flowcaseno);
        if(null == list || list.isEmpty()) {
            return 0;
        }
        list.stream().forEach(ii -> {
            PpmNewflowHistorylogDto dto = JsonUtil.jsonToClass(ii, PpmNewflowHistorylogDto.class);
            this.addPpmNewflowHistorylog(dto);
        });
        PpmFlowLog flowLog = new PpmFlowLog();
        flowLog.setFlowcaseno(flowcaseno);
        ppmFlowLogService.deletePpmFlowLog(flowLog);
        return 1;
    }

    @Override
    public int updatePpmNewflowHistorylog(PpmNewflowHistorylogDto ppmNewflowHistorylogDto) {
        // 更新
        if(StringUtil.isNotEmpty(ppmNewflowHistorylogDto.getId())) {
            myBatisDao.executionPojo(PPMNEWFLOWHISTORYLOG, ppmNewflowHistorylogDto, IMyBatisDao.SQL.UPDATE, "updatePojo");
            return 2;
        }
        this.addPpmNewflowHistorylog(ppmNewflowHistorylogDto);
        return 1;
    }

    @Override
    public int deletePpmNewflowHistorylog(String ids) {
        if(!StringUtil.isNotBlank(ids)) {
            return -1;
        }
        String[] idList = ids.split(",");
        PpmNewflowHistorylogDto dto = new PpmNewflowHistorylogDto();
        for (int i = idList.length - 1; i >= 0; i--) {
            dto.setId(Long.parseLong(idList[i]));
            dto.setIsdel(1);
            this.updatePpmNewflowHistorylog(dto );
        }
        return 1;
    }

    @Override
    public List<PpmNewflowHistorylogDto> queryList(String flowcaseno) {
        PpmNewflowHistorylog log = new PpmNewflowHistorylog();
        log.setFlowcaseno(flowcaseno);
        List<PpmNewflowHistorylog> list = myBatisDao.selectListT(PPMNEWFLOWHISTORYLOG, log, "getLogList");
        List<PpmNewflowHistorylogDto> dtos = JsonUtil.jsonToList(list, PpmNewflowHistorylogDto.class);
        return dtos;
    }

    @Override
    public int deleteByHisNullLog(PpmNewflowHistorylog log) {
        myBatisDao.executionPojo(PPMNEWFLOWHISTORYLOG, log, IMyBatisDao.SQL.DELETE, "deleteByHisNullLog");
        return 1;
    }
}
