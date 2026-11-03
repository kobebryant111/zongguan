package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.model.flowmanager.PpmFlowShouquan;
import com.work.ppm.application.model.integratedmanager.PpmManagercenterWork;
import com.work.ppm.application.services.flowmanager.IPpmFlowShouquanService;
import com.work.ppm.application.services.integratedmanager.IPpmManagercenterWorkService;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.workflow.client.soentity.BPSWorkItem;
import com.work.workflow.client.soentity.WSPageCond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 要是督办审批
 */
@Slf4j
@Service("ppmManagercenterWorkService")
public class PpmManagercenterWorkService implements IPpmManagercenterWorkService {
    /**
     * 实体类路径
     */
    private static String PPMMANAGERCENTERWORK = "com.work.ppm.application.model.integratedmanager.PpmManagercenterWork";
    /**
     * 数据接口
     */
    @Autowired
    private IMyBatisDao myBatisDao;
    /**
     * 流程授权接口
     */
    @Autowired
    private IPpmFlowShouquanService ppmFlowShouquanService;

    @Override
    public String startWorkFlow() {
        BpsWorkFlowDto bps = new BpsWorkFlowDto();
        bps.setProcessDefName("com.work.ppm.bps.glzxzdgz");
        bps.setProcessInstName("【182430】申领结项");
        bps.setProcessInstDesc("牵头负责人结项申请");
        bps.setUserId("182402");
        String processInstID = "";//流程id
        String processInfo = "";//流程信息
        Map<String, String> relaData = new HashMap<String, String>();
        relaData.put("users", "182430@孙嘉蕾"); //下一步处理人
        relaData.put("status", "-1");
        bps.setRelaData(relaData);
        // 日志参数
        bps.setSuggest("同意");
        bps.setConclusion("01");
        bps.setApplydesc("申领结项");
        processInstID = BpsUtil.createProcessAndFinishFirst(bps);   //发起流程,processInstID为返回的流程id 唯一
        processInfo = BpsUtil.queryProcessInfo(processInstID);      //processInfo-流程信息描述
        return processInfo;

    }

    @Override
    public String dbApprove(BpsWorkFlowDto bps) {
        Map<String,String> map = new HashMap<>();
        map.put("users","182402@洪晨阳"); // 下个处理人
        map.put("status", "-1");
        bps.setRelaData(map);
//        bps.setDestActDefId("manualActivity2");
        // 日志参数
        bps.setSuggest("同意");
        bps.setConclusion("01");
        bps.setApplydesc("督办领导审批");
        BpsUtil.finshWorkItem(bps);
        String processInfo = BpsUtil.queryProcessInfo(bps.getProcessInstId());
        return processInfo;
    }

    @Override
    public String zjlApprove(BpsWorkFlowDto bps) {
        String processInfo1 = BpsUtil.queryProcessInfo(bps.getProcessInstId());
        Map<String,String> map = new HashMap<>();
        bps.setRelaData(map);
        bps.setProcessDefName("总经理审批");
        // 日志参数
        bps.setSuggest("同意");
        bps.setConclusion("01");
        bps.setApplydesc("总经理审批");
        BpsUtil.finshWorkItem(bps);
        String processInfo = BpsUtil.queryProcessInfo(bps.getProcessInstId());
        return processInfo;
    }



    @Override
    public String sqApprove(BpsWorkFlowDto bps) {
        Map<String,String> map = new HashMap<>();
        map.put("users","182402@洪晨阳"); // 下个处理人
        map.put("status", "-1");
        bps.setRelaData(map);
        // 日志参数
        bps.setSuggest("同意");
        bps.setConclusion("01");
        bps.setApplydesc("申请结项");
        BpsUtil.finshWorkItem(bps);
        String processInfo = BpsUtil.queryProcessInfo(bps.getProcessInstId());
        return processInfo;
    }



    /**
     * 查询已经结束的工作流
     * @param processInstName 流程实例名称
     * @param processDefName 流程定义名称
     * @param userId 工号
     * @return List<BPSWorkItem>
     */
    private List<BPSWorkItem> geFinishedtBPSWorkItem(String processInstName, String processDefName, String userId) {
        BPSWorkItem item = new BPSWorkItem();
        item.setSortField("createTime");
        item.setSortOrder("desc");
        if(StringUtil.isNotBlank(processDefName)) {
            item.setProcessDefName(processDefName);
        }
        List<BPSWorkItem> bpsList = BpsUtil.queryWorkItemInApprovedAndActivityProcessInst(item,userId);
        List<BPSWorkItem> itemList = getShouquanDaiban(processDefName,userId);
        for (BPSWorkItem workItem: itemList) {
            bpsList.add(workItem);
        }
        // 删除相同的数据
        if(StringUtil.isNotBlank(processDefName)) {
            Iterator<BPSWorkItem> iterator = bpsList.iterator();
            while (iterator.hasNext()) {
                BPSWorkItem tempItem = iterator.next();
                if(!tempItem.getProcessDefName().equals(processDefName)) {
                    iterator.remove();
                }
            }
        }
        List<BPSWorkItem> linkList = new LinkedList<>();
        for(int i =0;i<bpsList.size();i++)
        {
            BPSWorkItem bpsWorkItem = bpsList.get(i);
            if(processInstName!=null&&!"".equals(processInstName))
            {
                if(bpsWorkItem.getProcessInstName().toUpperCase().
                        indexOf(processInstName.toUpperCase())==-1){
                    continue;
                }
            }
            if(bpsWorkItem.getActionURL()!=null&&bpsWorkItem.getActionURL().indexOf("addrIndex=")>-1)
            {
                continue;
            }

            linkList.add(bpsWorkItem);
        }
        List<Long> names = new ArrayList<>();
        // 去重
        return linkList.stream().filter(
                v->{
                    boolean flag = !names.contains(v.getProcessInstID());
                    names.add(v.getProcessInstID());
                    return flag;
                }
        ).collect(Collectors.toList());
    }

    /**
     * 授权待办
     * @param userId  工号
     * @param processDefName 流程定义流程
     * @return  List<BPSWorkItem>
     */
    private List<BPSWorkItem> getShouquanDaiban(String processDefName,String userId) {
        PpmFlowShouquan shouquan = new PpmFlowShouquan();
        shouquan.setShouquanren(userId);
        if(StringUtil.isNotBlank(processDefName)) {
            shouquan.setFlowid(processDefName);
        }
        List<PpmFlowShouquan> list = ppmFlowShouquanService.getPpmFlowShouquanList(shouquan);
        if(null == list && list.isEmpty()) {
            return null;
        }
        BPSWorkItem workItem = new BPSWorkItem();
        List<BPSWorkItem> workItemsList = new ArrayList<>();
        for (PpmFlowShouquan item: list) {
            workItem.setSortOrder("desc");
            workItem.setSortField("createTime");
            workItem.setProcessInstID(Long.parseLong(item.getProcessinstid()));
            List<BPSWorkItem> temList = BpsUtil.queryWorkItemInApprovedAndActivityProcessInst(workItem,item.getBeishouquanren());
            if(null != temList && !temList.isEmpty()) {
                workItemsList.add(temList.get(0));
            }
        }
        return workItemsList;
    }

    /**
     * 获取工作流流程信息
     * @param processDefName 流程定义名称
     * @param userId 工号
     * @return List<BPSWorkItem>
     */
    private List<BPSWorkItem> getBpsWorkItem( String processDefName, String userId) {
        WSPageCond page = new WSPageCond();
        page.setIsCount(true);
        page.setIsPage(false);
        BPSWorkItem item = new BPSWorkItem();
        if(null != processDefName && StringUtil.isNotBlank(processDefName)) {
            item.setProcessDefName(processDefName);
        }
        item.setSortField("createTime");
        item.setSortOrder("desc");
        return BpsUtil.getWorkItem(item, userId);
    }

    @Override
    public PpmManagercenterWork queryDetail(PpmManagercenterWork ppmManagercenterWork) {
        return myBatisDao.selectClass(PPMMANAGERCENTERWORK, ppmManagercenterWork,"queryDetail");
    }

    @Override
    public Integer updatePpmManagercenterWork(PpmManagercenterWork ppmManagercenterWork) {
        return myBatisDao.executionPojo(PPMMANAGERCENTERWORK, ppmManagercenterWork, IMyBatisDao.SQL.UPDATE,"updatePojo");
    }

    @Override
    public Integer deletePpmManagercenterWork(PpmManagercenterWork ppmManagercenterWork) {
        return myBatisDao.executionPojo(PPMMANAGERCENTERWORK, ppmManagercenterWork, IMyBatisDao.SQL.DELETE,"deleteById");
    }


}
