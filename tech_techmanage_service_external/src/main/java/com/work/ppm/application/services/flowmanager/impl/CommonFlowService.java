package com.work.ppm.application.services.flowmanager.impl;

import com.work.ppm.application.dto.systemmanager.CommDepartmentDto;
import com.work.ppm.application.model.flowmanager.PpmProjectFlow;
import com.work.ppm.application.quartz.core.ExecutorManager;
import com.work.ppm.application.services.flowmanager.ICommonDeleteFlowService;
import com.work.ppm.commons.factory.BpsFlowFactory;
import com.work.ppm.commons.lang.esb.EsbAction;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.JsonUtil;
import com.work.ppm.commons.utils.StringUtil;
import com.work.ppm.application.dto.commonmanager.CommTreeDto;
import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmProjectFlowDto;
import com.work.ppm.application.model.flowmanager.PpmFlowShouquan;
import com.work.ppm.application.model.systemmanager.EosDictEntry;
import com.work.ppm.application.services.flowmanager.ICommonFlowService;
import com.work.ppm.application.services.flowmanager.IPpmFlowShouquanService;
import com.work.ppm.application.services.flowmanager.IPpmProjectFlowService;
import com.work.ppm.application.services.systemmanager.ICommDepartmentService;
import com.work.ppm.application.services.systemmanager.IEosDictEntryService;
import com.work.workflow.client.soentity.BPSWorkItem;
import com.work.workflow.client.soentity.WSPageCond;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *公共流程接口
 */
@Slf4j
@Service("commonFlowService")
public class CommonFlowService implements ICommonFlowService {
    /**
     * 业务字典
     */
    @Autowired
    private IEosDictEntryService eosDictEntryService;
    /**
     * 流程授权
     */
    @Autowired
    private IPpmFlowShouquanService ppmFlowShouquanService;
    /**
     * 流程对象接口
     */
    @Autowired
    private IPpmProjectFlowService ppmProjectFlowService;
    /**
     * 部门接口
     */
    @Autowired
    private ICommDepartmentService commDepartmentService;

    @Override
    public List<BPSWorkItem> getdbWorkItem(BpsWorkFlowDto dto) {
        EosDictEntry eos = eosDictEntryService.getTypeidForDetail("NO_GET_PROCESS_USERS", "01");
        if(null != eos && eos.getDictname().indexOf(dto.getUserId()) > -1) {
            return null;
        }
        List<BPSWorkItem> bpsList = new ArrayList<>();
        // 流程定义名称 不为空
        if(StringUtil.isNotBlank(dto.getProcessDefName())) {
            String[] defNames = dto.getProcessDefName().split(",");
            for (String str: defNames) {
                List<BPSWorkItem> temList = getBpsWorkItem(dto.getProcessDefName(),dto.getUserId());
                bpsList.addAll(temList);
            }
            return bpsList;
        }
        // 流程定义名称 为空
        List<BPSWorkItem> temList = getBpsWorkItem(dto.getProcessDefName(),dto.getUserId());
        if(null == temList || temList.isEmpty()) {
            return null;
        }

        return temList;
    }

    @Override
    public List<BPSWorkItem> getybWorkItem(BpsWorkFlowDto dto) {
        if(StringUtil.isNotBlank(dto.getProcessDefName())) {
            String[] names = dto.getProcessDefName().split(",");
            List<BPSWorkItem> bpsList = new ArrayList<>();
            for (String item: names) {
                List<BPSWorkItem> list = this.geFinishedtBPSWorkItem(dto.getProcessInstName(),item,dto.getUserId());
                bpsList.addAll(list);
            }
            return bpsList;
        } else {
            List<BPSWorkItem> list = this.geFinishedtBPSWorkItem(dto.getProcessInstName(), dto.getProcessDefName(), dto.getUserId());
            return list;
        }
    }

    @Override
    public void getYbWorkItem(PpmProjectFlowDto dto, Pager pager) {
        dto.setIsdel(0);
        ppmProjectFlowService.queryPager(dto,pager);
    }

    @Override
    public void getGdWorkItem(PpmProjectFlowDto dto, Pager pager) {
        dto.setIsdel(0);
        dto.setFlowstatus("已结束");
        ppmProjectFlowService.queryPager(dto,pager);
    }

    @Override
    public List<CommTreeDto> getAuthList(PpmProjectFlowDto dto) {
        CommDepartmentDto departmentDto = commDepartmentService.queryParentDeptId(dto.getDeptNo());
        List<CommTreeDto> list;
        if(departmentDto.getLocation().indexOf("分行") != -1) {
            list = commDepartmentService.queryAuth(departmentDto.getCompid(),dto.getUserData(),
                    dto.getUsers());
        } else {
            list = commDepartmentService.queryAuth(dto.getDeptNo(),dto.getUserData(),
                    dto.getUsers());
        }

        return list;
    }

    @Override
    public String doAuth(BpsWorkFlowDto dto) {
        if(StringUtil.isNotBlank(dto.getProcessDefName())) {
            dto.setProcessDefName(dto.getProcessDefName().replaceAll("@STR@", "\\\\"));
        }
        BpsUtil.reassignWorkItem(dto);
        String msg = BpsUtil.queryProcessInfo(dto);
        return msg;
    }

    @Override
    public String doDel(BpsWorkFlowDto bps) {
        ICommonDeleteFlowService service = BpsFlowFactory.getDelFlowService(bps.getProcessDefName());
        if(null != service) {
            service.doDelete(bps);
        } else {
            log.warn("【新应用】删除业务流程信息失败，{}流程未找到", bps.getProcessDefName());
//            return "-1";
        }
        BpsUtil.terminateProcessInstance(bps);
        ExecutorManager.getExecutor().submit(()->{
            // OA 通知
            EsbAction action = new EsbAction();
            action.sendRemoveOA(bps);
        });
        return "1";
    }

    @Override
    public String drawbackWorkItem(BpsWorkFlowDto bps) {
        String info = BpsUtil.queryProcessInfo(bps);
        String[] infos = info.split(",");
        if(infos.length <= 2) {
            return "流程已结束";
        }
        bps.setConclusion("04");
        bps.setApplydesc(bps.getActivityInstName());
        String workItemId = bps.getWorkItemId();
        bps.setWorkItemID(bps.getDrawbackId());
        BpsUtil.drawbackWorkItem(bps);
        ExecutorManager.getExecutor().submit(()->{
            // OA 通知
            EsbAction action = new EsbAction();
            bps.setWorkItemID(workItemId);
            action.sendRemoveOA(bps);
            bps.setWorkItemID(bps.getDrawbackId());
            action.sendCreateOA(bps);
        });
        return "流程已撤回";
    }

    @Override
    public BpsWorkFlowDto doIsDrawbackEnable(BpsWorkFlowDto bps) {
        // 标记撤回，true：撤回，false，不撤回
        boolean falg ;
        BpsWorkFlowDto dto = new BpsWorkFlowDto();
        BPSWorkItem item = this.doFinishedLastWorkitem(bps);
        if (null == item){
            dto.setWorkItemId(bps.getWorkItemId());
            dto.setFalg(false);
            return dto;
        }
        bps.setWorkItemId(item.getWorkItemID() + "");
        // 查询上一节点是否存在多签，多签：false，反之：true
        List<BPSWorkItem> list = BpsUtil.queryWorkItemsByActivityInstID(item.getActivityInstID(),bps.getUserId());
        // 数据存在多条则为多签
        if(null != list && list.size() > 1) {
            falg = false;
        } else {
            falg = BpsUtil.isDrawbackEnable(bps);
        }
        // 需要撤回的workitemid
        dto.setDrawbackId(bps.getWorkItemId());
        dto.setFalg(falg);
        return dto;
    }

    @Override
    public BPSWorkItem doFinishedLastWorkitem(BpsWorkFlowDto bps) {
        BPSWorkItem item = BpsUtil.queryFinishedWorkItemInActivityProcessInst(bps);
        return item;
    }

    @Override
    public BPSWorkItem queryWorkItem(BpsWorkFlowDto bps) {
        List<BPSWorkItem> workItems = BpsUtil.getWorkItem(bps);
        BPSWorkItem item = null;
        if(null == workItems || workItems.isEmpty()) {
            return null;
        }
        for (int i = workItems.size() - 1; i >= 0; i--) {
            if(workItems.get(i).getParticipant().equals(bps.getUserId())) {
                item = workItems.get(i);
            }
        }
        if(null == item) {
            item = workItems.get(0);
        }
        return item;
    }

    @Override
    public PpmProjectFlowDto queryGdDetail(String instanceid) {
        PpmProjectFlow flow = ppmProjectFlowService.queryDetail(instanceid);
        PpmProjectFlowDto dto = JsonUtil.jsonToClass(flow, PpmProjectFlowDto.class);
        if(null != dto) {
            dto.setProcessDefName(flow.getFlowid());
        }
        return dto;
    }

    @Override
    public boolean getProcessInstState(BpsWorkFlowDto bps) {
        String state = BpsUtil.getProcessInstState(bps.getProcessInstId(),bps.getUserId());
        return "7".equals(state);
    }

    @Override
    public void delOaNotice(BpsWorkFlowDto bps) {
        EsbAction action = new EsbAction();
        action.sendRemoveOA(bps);
    }


    /**
     * 获取工作流流程信息
     * @param processDefName 流程定义名称
     * @param userId 工号
     * @return  List<BPSWorkItem>
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
        List<BPSWorkItem> list = BpsUtil.getWorkItem(item, userId);
        list.stream().forEach(ii -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date= null;
            try {
                date = simpleDateFormat.parse(ii.getCreateTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formatTime = simpleDateFormat1.format(date);
            ii.setCreateTime(formatTime);
        });
        return list;
    }

    /**
     * 查询已经结束的工作流
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
//        List<BPSWorkItem> itemList = getShouquanDaiban(processDefName,userId);
//        for (BPSWorkItem workItem: itemList) {
//            bpsList.add(workItem);
//        }
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
        List<BPSWorkItem> bpsList1 = linkList.stream().filter(
                v->{
                    boolean flag = !names.contains(v.getProcessInstID());
                    names.add(v.getProcessInstID());
                    return flag;
                }
        ).collect(Collectors.toList());
        bpsList1.stream().forEach(ii -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date= null;
            try {
                date = simpleDateFormat.parse(ii.getCreateTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formatTime = simpleDateFormat1.format(date);
            ii.setCreateTime(formatTime);
        });
        return bpsList1;
    }

    /**
     * 获取授权待办
     * @param processDefName 流程定义名称
     * @param userId 工号
     * @return List<BPSWorkItem>
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
}
