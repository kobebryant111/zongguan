package com.tansun.wf.platform.service;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.model.LeaveTransferEntity;
import com.tansun.wf.activiti.service.WorkflowLeaveTransferService;
import com.tansun.wf.extend.db.WfBaseManager;
import com.tansun.wf.platform.mapper.WfLeaveTransferMapper;
import com.tansun.wf.platform.model.WfActBean;
import com.tansun.wf.platform.model.WfLeaveTransferEntity;
import com.tansun.wf.platform.query.WfLeaveTransferQuery;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.model.WfLeaveTransfer;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/9
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfLeaveTransferService extends WfBaseManager<WfLeaveTransferMapper, WfLeaveTransferEntity> {

    public static final String SUCCEED = "0";
    public static final String FAILED = "1";
    @Autowired
    WfClassifyService wfClassifyService;

    @Autowired
    WorkFlowClientService workFlowClientService;

    @Autowired
    WfLeaveTransferMapper wfLeaveTransferMapper;

    @Autowired
    private WorkflowLeaveTransferService workflowLeaveTransferService;

    public PageInfo<WfLeaveTransferEntity> getPageList(WfLeaveTransferQuery wfLeaveTransferQuery, PageInfo pageInfo) {
        return this.findPage(wfLeaveTransferQuery,pageInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save (List<WfLeaveTransferEntity> wfLeaveTransferEntityList){
        for(WfLeaveTransferEntity wfLeaveTransferEntity:wfLeaveTransferEntityList){
            WfLeaveTransfer wfLeaveTransfer = new WfLeaveTransfer();
            BeanUtils.copyProperties(wfLeaveTransferEntity,wfLeaveTransfer);
            // 季昂写的代码逻辑需要这个 SetStatus 状态，要注意流程实例id 流程分类id 不能都有
            if(StringUtils.isNotBlank(wfLeaveTransfer.getProcInstId())){
                wfLeaveTransfer.setSetStatus("2");
            }
            if(StringUtils.isNotBlank(wfLeaveTransfer.getClassifyId())){
                wfLeaveTransfer.setSetStatus("1");
            }
            TaskBean taskBean = workflowLeaveTransferService.saveLeaveTransfer(wfLeaveTransfer);
            if(FAILED.equals(taskBean.getRetCode())){
                throw new RuntimeException(taskBean.getRetMsg());
            }
        }
    }


    public Map<String, Object> stopLeaveTransfers(Map<String, Object> params){
        Map<String, Object> retMap = new HashMap<>();
        String itemsStr = MapUtils.getString(params, "items");
        List<WfLeaveTransferEntity> wfLeaveTransferEntities = JSON.parseArray(itemsStr, WfLeaveTransferEntity.class);
        for(WfLeaveTransferEntity wfLeaveTransferEntity:wfLeaveTransferEntities){
            String status = wfLeaveTransferEntity.getStatus();
            String isTransfer = wfLeaveTransferEntity.getIsTransfer();
            if ("1".equals(status)) {
                throw new RuntimeException("强制终止失败，所选数据存在移交失效数据");
            }
            if ("1".equals(isTransfer)) {
                throw new RuntimeException("强制终止失败，所选数据存在永久移交的数据");
            }
        }
        for(WfLeaveTransferEntity wfLeaveTransferEntity:wfLeaveTransferEntities){
            stopLeaveTransfer(wfLeaveTransferEntity);
        }
        retMap.put("SUCCEED", "强制终止成功");
        return retMap;
    }

    public Map<String, Object> startLeaveTransfers(Map<String, Object> params){
        Map<String, Object> retMap = new HashMap<String, Object>();
        String itemsStr = MapUtils.getString(params, "items");
        List<WfLeaveTransferEntity> wfLeaveTransferEntities = JSON.parseArray(itemsStr, WfLeaveTransferEntity.class);
        for(WfLeaveTransferEntity wfLeaveTransferEntity:wfLeaveTransferEntities){
            String status = wfLeaveTransferEntity.getStatus();
            if ("1".equals(status)) {
                throw new RuntimeException("启动授权失败，所选数据存在移交失效数据");
            }
        }
        for(WfLeaveTransferEntity wfLeaveTransferEntity:wfLeaveTransferEntities){
            startLeaveTransfer(wfLeaveTransferEntity);
        }
        retMap.put("SUCCEED", "启动授权成功");
        return retMap;
    }

    /**
     * 启动授权
     */
    public void startLeaveTransfer(WfLeaveTransferEntity leaveTransfer){
        Date startTime = leaveTransfer.getStartTime();
        Date now = new Date();
        // 还没到起始时间，不能开始授权
        if (now.before(startTime)) {
            throw new RuntimeException("启动授权失败，所选数据存在未到起始时间的数据");
        }
        // 启动未执行的配置
        if("0".equals(leaveTransfer.getStartStatus())){
            if (StringUtils.isBlank(leaveTransfer.getRootId())) {
                LeaveTransferEntity leaveTransferEntity = copyToEntity(leaveTransfer);
                workflowLeaveTransferService.startTransfer(leaveTransferEntity);
            }
        }
    }



    /**
     * 终止移交
     */
    public void stopLeaveTransfer(WfLeaveTransferEntity leaveTransfer){
        String id = leaveTransfer.getId();
        String classifyId = leaveTransfer.getClassifyId();
        String user = leaveTransfer.getUserId();
        Date startTime = leaveTransfer.getStartTime();
        Date endTime = leaveTransfer.getEndTime();
        Date now = new Date();
        // 若当前时间在 开始时间 之前，终止离岗移交：把状态改为失效
        if (now.before(startTime)) {
            leaveTransfer.setStatus("1");
            this.update(leaveTransfer);
        }
        // 若当前时间 在离岗移交的时间范围内，强制终止流程
        if (now.after(startTime) && now.before(endTime)) {
            TaskInfo taskInfo = new TaskInfo();
            Set<String> fromUsers = new HashSet<>();
            fromUsers.add(user);
            taskInfo.setFromUsers(fromUsers);
            taskInfo.setLeaveTransferId(id);
            taskInfo.setClassifyId(classifyId);
            if (StringUtils.isBlank(leaveTransfer.getRootId())) {
                LeaveTransferEntity leaveTransferEntity = copyToEntity(leaveTransfer);
                workflowLeaveTransferService.stopTransfer(leaveTransferEntity);
            }else {
                Set<String> toUsers = new HashSet<String>();
                toUsers.add(user);
                taskInfo.setToUsers(toUsers);
            }
        }
    }



    /**
     * 时间校验
     */
    public WfActBean checkData(WfLeaveTransferEntity wfLeaveTransfer) {
        Date newStartTime = wfLeaveTransfer.getStartTime();
        Date newEndTime = wfLeaveTransfer.getEndTime();
        String newStatus = wfLeaveTransfer.getStatus();
        WfActBean wf = new WfActBean();
        //已执行的离岗移交操作无法修改
        WfActBean checkWorkedData = checkWorkedData(wfLeaveTransfer);
        if (FAILED.equals(checkWorkedData.getRetCode())) {
            return checkWorkedData;
        };
        //正在执行离岗操作不得修改
        WfActBean checkWorkingData = checkWorkingData(wfLeaveTransfer);
        if (FAILED.equals(checkWorkingData.getRetCode())) {
            return checkWorkingData;
        };
        if ("1".equals(newStatus)) {
            wf.setRetCode(SUCCEED);
            return wf;
        }
        wf = checkDate(newStartTime, newEndTime);
        if (FAILED.equals(wf.getRetCode())) {
            return wf;
        }
        WfActBean checkUpdateData = checkUpdateData(wfLeaveTransfer);
        if (FAILED.equals(checkUpdateData.getRetCode())) {
            return checkUpdateData;
        }
        wf.setRetCode(SUCCEED);
        return wf;
    }

    //修改
    public WfActBean checkUpdateData(WfLeaveTransferEntity wfLeaveTransfer){
        WfActBean wflt = new WfActBean();
        WfLeaveTransferEntity hiWf = new WfLeaveTransferEntity();
        hiWf.setClassifyId(wfLeaveTransfer.getClassifyId());
        hiWf.setStatus(wfLeaveTransfer.getStatus());
        List<WfLeaveTransferEntity> hiWfs = new ArrayList<WfLeaveTransferEntity>();
        if (StringUtils.isNotBlank(wfLeaveTransfer.getRootId())) {
            hiWf.setRootId(wfLeaveTransfer.getRootId());
            hiWfs = wfLeaveTransferMapper.findDimissionLeaveTransfer(hiWf);
        }else {
            hiWfs = wfLeaveTransferMapper.findLeaveTransfer(hiWf);
        }
        //修改的本条数据classifyId变与不变逻辑一致
        for(WfLeaveTransferEntity wf:hiWfs){
            if (wfLeaveTransfer.getId() == null ||
                    !wfLeaveTransfer.getId().equals(wf.getId())) {
                WfActBean checkHiDate = checkHiDate(wfLeaveTransfer,wf);
                if (FAILED.equals(checkHiDate.getRetCode())) {
                    return checkHiDate;
                };
            }
        }
        wflt.setRetCode(SUCCEED);
        return wflt;
    }

    //已执行的离岗移交操作无法修改
    public WfActBean checkWorkedData(WfLeaveTransferEntity wfLeaveTransfer){
        WfActBean wflt = new WfActBean();
        String id = wfLeaveTransfer.getId();
        if (StringUtils.isNotBlank(id)) {
            WfLeaveTransferEntity hiWf = new WfLeaveTransferEntity();
            hiWf.setId(id);
            //验证是否是新版离岗移交操作/是则验证新版离岗移交
            List<WfLeaveTransferEntity> hiWfs = new ArrayList<WfLeaveTransferEntity>();
            if (StringUtils.isNotBlank(wfLeaveTransfer.getRootId())) {
                hiWf.setRootId(wfLeaveTransfer.getRootId());
                hiWfs = wfLeaveTransferMapper.findDimissionLeaveTransfer(hiWf);
            }else {
                hiWfs = wfLeaveTransferMapper.findLeaveTransfer(hiWf);
            }
            for(WfLeaveTransferEntity wf:hiWfs){
                if ("1".equals(wf.getStartStatus())) {
                    wflt.setRetCode(FAILED);
                    wflt.setRetMsg("保存失败，不能修改已执行过的移交设置");
                    return wflt;
                }
            }
        }
        return wflt;
    }

    //新数据基本自检
    public WfActBean checkDate(Date newStartTime, Date newEndTime){
        WfActBean wflt = new WfActBean();
        if (newStartTime.getTime() == newEndTime.getTime() || newStartTime.after(newEndTime)) {
            wflt.setRetCode(FAILED);
            wflt.setRetMsg("保存失败，起始时间与结束时间一致或起始时间大于结束时间");
            return wflt;
        }
        Date now = new Date();
        Calendar nowDate = Calendar.getInstance();
        nowDate.setTime(now);
        if (newStartTime.before(now)) {
            wflt.setRetCode(FAILED);
            wflt.setRetMsg("保存失败，起始时间小于当前时间");
            return wflt;
        }
        wflt.setRetCode(SUCCEED);
        return wflt;
    }
    //与历史记录校验
    public WfActBean checkHiDate(WfLeaveTransferEntity newlt, WfLeaveTransferEntity hilt) {
        WfActBean wflt = new WfActBean();
        Date newStartTime = newlt.getStartTime();
        Date newEndTime = newlt.getEndTime();
        Date startTime = hilt.getStartTime();
        Date endTime = hilt.getEndTime();
        if (newStartTime.getTime() == startTime.getTime()
                || newStartTime.getTime() == endTime.getTime()) {
            wflt.setRetCode(FAILED);
            wflt.setRetMsg("保存失败，起始时间与历史记录起始时间或结束时间一致");
            return wflt;
        }
        if (newEndTime.getTime() == startTime.getTime()
                || newEndTime.getTime() == endTime.getTime()) {
            wflt.setRetCode(FAILED);
            wflt.setRetMsg("保存失败，结束时间与历史记录结束时间或起始时间一致");
            return wflt;
        }

        Calendar newStartDate = Calendar.getInstance();
        newStartDate.setTime(newStartTime);

        Calendar newEndDate = Calendar.getInstance();
        newEndDate.setTime(newEndTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (newStartDate.after(end)) {
            wflt.setRetCode(SUCCEED);
            return wflt;
        } else if (newEndDate.before(begin)){
            wflt.setRetCode(SUCCEED);
            return wflt;
        } else {
            wflt.setRetCode(FAILED);
            wflt.setRetMsg("保存失败,起始或结束时间与历史记录时间重叠");
            return wflt;
        }
    }

    public WfActBean checkWorkingData(WfLeaveTransferEntity wfLeaveTransfer){
        WfActBean wflt = new WfActBean();
        if (StringUtils.isNotBlank(wfLeaveTransfer.getId())) {
            WfLeaveTransferEntity wf = new WfLeaveTransferEntity();
            wf.setId(wfLeaveTransfer.getId());
            List<WfLeaveTransferEntity> hiWfs = new ArrayList<WfLeaveTransferEntity>();
            if (StringUtils.isNotBlank(wfLeaveTransfer.getRootId())) {
                wf.setRootId(wfLeaveTransfer.getRootId());
                hiWfs = wfLeaveTransferMapper.findDimissionLeaveTransfer(wf);
            }else {
                hiWfs = wfLeaveTransferMapper.findLeaveTransfer(wf);
            }
            if (hiWfs.size() == 0) {
                wflt.setRetCode(FAILED);
                wflt.setRetMsg("保存失败，请清除缓存，重新加载");
                return wflt;
            }
            for(WfLeaveTransferEntity hiWf:hiWfs){
                WfActBean checkWorkingDataTime = checkWorkingDataTime(hiWf);
                if (FAILED.equals(checkWorkingDataTime.getRetCode())) {
                    return checkWorkingDataTime;
                };
            }
        }
        wflt.setRetCode(SUCCEED);
        return wflt;
    }

    public WfActBean checkWorkingDataTime(WfLeaveTransferEntity hiWf){
        WfActBean wflt = new WfActBean();
        Date startTime = hiWf.getStartTime();
        Date endTime = hiWf.getEndTime();
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        Date nowDate = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);

        //校验当前时间是否正在离岗执行时间
        if ((now.after(begin) && now.before(end))
                || (nowDate == startTime || nowDate == endTime)) {
            if ("0".equals(hiWf.getStatus())) {
                wflt.setRetCode(FAILED);
                wflt.setRetMsg("修改失败，当前时间不得在起始时间与结束时间之间");
                return wflt;
            }
            wflt.setRetCode(FAILED);
            wflt.setRetMsg("修改失败，该条数据正在执行中");
            return wflt;
        }
        wflt.setRetCode(SUCCEED);
        return wflt;
    }

    private LeaveTransferEntity copyToEntity(WfLeaveTransferEntity leaveTransfer) {
        LeaveTransferEntity leaveTransferEntity = new LeaveTransferEntity();
        leaveTransferEntity.setId(leaveTransfer.getId());
        leaveTransferEntity.setUserId(leaveTransfer.getUserId());
        leaveTransferEntity.setTransferId(leaveTransfer.getTransferId());
        leaveTransferEntity.setStartStatus(leaveTransfer.getStartStatus());
        leaveTransferEntity.setStatus(leaveTransfer.getStatus());
        leaveTransferEntity.setClassifyId(leaveTransfer.getClassifyId());
        leaveTransferEntity.setClassifyName(leaveTransfer.getClassifyName());
        leaveTransferEntity.setIsPower(leaveTransfer.getIsPower());
        leaveTransferEntity.setIsTransfer(leaveTransfer.getIsTransfer());
        leaveTransferEntity.setIsTransferHistoricalTasks(leaveTransfer.getIsTransferHistoricalTasks());
        return leaveTransferEntity;
    }

}
