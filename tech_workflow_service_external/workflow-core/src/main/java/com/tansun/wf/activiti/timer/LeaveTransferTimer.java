package com.tansun.wf.activiti.timer;

import com.alibaba.fastjson.JSON;
import com.tansun.wf.activiti.constants.LeaveTransferConstants;
import com.tansun.wf.activiti.model.LeaveTransferEntity;
import com.tansun.wf.activiti.service.WorkflowLeaveTransferService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class LeaveTransferTimer {

    private Logger logger = LoggerFactory.getLogger(getClass());
    // 是否开启定时离岗移交功能
    protected boolean isOpen;

    @Autowired
    private WorkflowLeaveTransferService workflowLeaveTransferService;

    /**
     * 执行定时启动授权和终止授权
     * 默认每隔一个小时执行一次
     */
    public void leaveTransferTimer(){
        if(isOpen){
            // 启动授权
            // 获取所有生效，未执行的离岗移交配置
            List<LeaveTransferEntity> starts = workflowLeaveTransferService.getLeaveTransfer(null,null,null,null, LeaveTransferConstants.START_STATUS_UN_BEGIN);
            if (starts != null) {
                for(LeaveTransferEntity leaveTransfer : starts){
                    // 判断是否已到可执行的时间
                    if (checkBeginTime(leaveTransfer) ) {
                        logger.info("leaveTransferTimer begin (fromUser -> toUser) ....." + JSON.toJSONStringWithDateFormat(leaveTransfer,"yyyy-MM-dd HH:mm:ss"));
                        // 开始移交
                        workflowLeaveTransferService.startTransfer(leaveTransfer);
                    }
                }
            }
            // 终止授权
            // 获取所有生效，已执行的离岗移交配置
            List<LeaveTransferEntity> ends = workflowLeaveTransferService.getLeaveTransfer(null,null,null,null,LeaveTransferConstants.START_STATUS_IS_BEGIN);
            if (ends != null) {
                for(LeaveTransferEntity leaveTransfer : ends){
                    // 判断结束时间已过，可以终止授权（强制终止时可不校验）
                    if (checkEndTime(leaveTransfer)) {
                        logger.info("leaveTransferTimer begin (toUser -> fromUser) ....." + JSON.toJSONStringWithDateFormat(leaveTransfer,"yyyy-MM-dd HH:mm:ss"));
                        if (StringUtils.isBlank(leaveTransfer.getRootId())) {
                            // 终止移交
                            workflowLeaveTransferService.stopTransfer(leaveTransfer);
                        }
                    }
                }
            }
        }
    }

    /**
     * 离岗移交执行条件验证，逻辑和终止条件验证类似
     * starTime <= nowTime
     */
    public boolean checkBeginTime(LeaveTransferEntity leaveTransferEntity){
        Date startTime = leaveTransferEntity.getStartTime();
        Date nowTime = new Date();
        if (nowTime == startTime || nowTime.after(startTime)){
            return true;
        }
        return false;
    }

    /**
     * 离岗终止条件验证
     * endTime <= nowTime
     */
    public boolean checkEndTime(LeaveTransferEntity leaveTransfer){
        Date endTime = leaveTransfer.getEndTime();
        Date nowTime = new Date();
        if (nowTime == endTime || nowTime.after(endTime)) {
            return true;
        }
        return false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
