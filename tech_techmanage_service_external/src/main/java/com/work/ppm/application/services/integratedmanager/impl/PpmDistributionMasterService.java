package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmDistributionMasterDto;
import com.work.ppm.application.model.integratedmanager.PpmDistributionMaster;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.application.services.integratedmanager.IPpmDistributionMasterService;
import com.work.ppm.application.services.integratedmanager.IPpmOnboardDistributionService;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
  * 入职部门分配主表
  */
@Service("ppmDistributionMasterService")
public class PpmDistributionMasterService extends BaseDaoSupper implements IPpmDistributionMasterService, ICommonDeleteFlowService {
    /**
      * 实体类路径
      */
    private static String PPMDISTRIBUTIONMASTER = "" +
            "com.work.ppm.application.model.integratedmanager.PpmDistributionMaster";
    /**
     * 流程名称
     */
    private static String FLOW_NAME = "com.work.ppm.bps.onboardDistribution";
    /**
      * 数据接口
      */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 入职部门分配子表接口
     */
    @Autowired
    private IPpmOnboardDistributionService ppmOnboardDistributionService;


    @Override
    public int addPpmDistributionMaster(PpmDistributionMasterDto ppmDistributionMasterDto) {
        PpmDistributionMaster ppmDistributionMaster = JsonUtil.jsonToClass(ppmDistributionMasterDto, PpmDistributionMaster.class);
        Long id = getNextSequence(PpmDistributionMaster.class);

        // 如果需要请set 主键id
        ppmDistributionMaster.setId(id.intValue());
        ppmDistributionMasterDto.setId(ppmDistributionMaster.getId());
        myBatisDao.executionPojo(PPMDISTRIBUTIONMASTER, ppmDistributionMaster, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }
    @Override
    public PpmDistributionMasterDto queryDetail(PpmDistributionMasterDto ppmDistributionMasterDto) {
        PpmDistributionMaster ppmDistributionMaster = JsonUtil.jsonToClass(ppmDistributionMasterDto, PpmDistributionMaster.class);
        PpmDistributionMaster data = myBatisDao.selectClass(PPMDISTRIBUTIONMASTER, ppmDistributionMaster,
        "queryDetail");
        PpmDistributionMasterDto item = JsonUtil.jsonToClass(data, PpmDistributionMasterDto
        .class);
        return item;
    }

    @Override
    public int updatePpmDistributionMaster(PpmDistributionMasterDto ppmDistributionMasterDto) {
        PpmDistributionMaster ppmDistributionMaster = JsonUtil.jsonToClass(ppmDistributionMasterDto, PpmDistributionMaster.class);
        return myBatisDao.executionPojo(PPMDISTRIBUTIONMASTER, ppmDistributionMaster, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Transactional("defaultTx")
    @Override
    public String liuzhuan(PpmDistributionMasterDto ppmDistributionMasterDto) {
        BpsWorkFlowDto bps = ppmDistributionMasterDto.getBps();
        if(null == bps) {
            bps = new BpsWorkFlowDto();
            bps.setUserId(ppmDistributionMasterDto.getInitiateId());
        } else {
            bps.setUserId(ppmDistributionMasterDto.getInitiateId());
        }
        Map<String,String> bpsMap = new HashMap<>();
        bpsMap.put("users", ppmDistributionMasterDto.getUpcomBadge());
        bpsMap.put("status", "-1");
        bps.setRelaData(bpsMap);
        bps.setProcessInstName("【"+ ppmDistributionMasterDto.getInitiateName() +"】新入职部门分配申请");
        bps.setProcessDefName(FLOW_NAME);
        bps.setProcessInstDesc("新入职部门分配申请");
        bps.setApplydesc("新入职部门分配申请");
        bps.setFlowUrl("onboardDistribution");
        bps.setConclusion("01");
        bps.setSuggest("同意");
        String xid = "";
        try{
            xid = BpsUtil.transactionBegin(bps.getUserId());

            if(StringUtil.isNotBlank(bps.getProcessInstId())) {
                BpsUtil.finshWorkItem(bps,xid);
            } else {
                String instanceId = BpsUtil.createProcessAndStart(bps,xid);
                ppmDistributionMasterDto.setInstanceId(instanceId);
                ppmDistributionMasterDto.setIsdel("0");
                ppmDistributionMasterDto.setAddDate(new Date());
                this.addPpmDistributionMaster(ppmDistributionMasterDto);
            }
            int id = -1;
            if(null == ppmDistributionMasterDto.getId()) {
                ppmDistributionMasterDto.setInstanceId(bps.getProcessInstId());
                PpmDistributionMasterDto item = this.queryDetail(ppmDistributionMasterDto);
                if(null != item ) {
                    id = item.getId();
                }
            } else {
                id = ppmDistributionMasterDto.getId();
            }
            ppmOnboardDistributionService.updateInfoForId(ppmDistributionMasterDto.getResumeIds(),
                    id);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e){
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Transactional("defaultTx")
    @Override
    public String processApproval(PpmDistributionMasterDto ppmDistributionMasterDto) {
        BpsWorkFlowDto bps = ppmDistributionMasterDto.getBps();
        Map<String,String> bpsMap = new HashMap<>();
        if("01".equals(bps.getConclusion())) {
            bpsMap.put("status", "-1");
        } else {
            bpsMap.put("status", "02");
        }
        String xid = "";
        try{
            xid = BpsUtil.transactionBegin(bps.getUserId());
            bps.setRelaData(bpsMap);
            bps.setApplydesc("处理人审批");
            BpsUtil.finshWorkItem(bps, xid);
            BpsUtil.transactionCommit(bps.getUserId(), xid);
            BpsUtil.flowLog(bps);
        }catch (Exception e) {
            BpsUtil.transactionRollback(bps.getUserId(), xid);
            throw new BizException(e);
        }
        return BpsUtil.queryProcessInfo(bps);
    }

    @Override
    public void doDelete(BpsWorkFlowDto bps) {
        PpmDistributionMasterDto item = new PpmDistributionMasterDto();
        item.setInstanceId(bps.getProcessInstId());
        item.setIsdel("1");
        this.updatePpmDistributionMaster(item);
    }
}
