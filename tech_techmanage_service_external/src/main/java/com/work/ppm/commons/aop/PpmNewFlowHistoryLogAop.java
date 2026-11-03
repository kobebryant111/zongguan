package com.work.ppm.commons.aop;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.flowmanager.PpmFlowLogDto;
import com.work.ppm.application.dto.flowmanager.PpmNewflowHistorylogDto;
import com.work.ppm.application.model.flowmanager.PpmFlowLog;
import com.work.ppm.application.services.flowmanager.IPpmFlowLogService;
import com.work.ppm.application.services.flowmanager.IPpmNewflowHistorylogService;
import com.work.ppm.application.services.systemmanager.ICommEmployeeService;
import com.work.ppm.commons.ApiCode;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.utils.BpsUtil;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.JsonUtil;
import com.tansun.workflow.model.TaskBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 历史流程日志
 *
 */
@Component
@Aspect
@Slf4j
public class PpmNewFlowHistoryLogAop {
    /**
     * 员工接口
     */
    @Autowired
    private ICommEmployeeService commEmployeeService;
    /**
     * 历史流程日志接口
     */
    @Autowired
    private IPpmNewflowHistorylogService ppmNewflowHistorylogService;
    /**
     * 流程日志接口
     */
    @Autowired
    private IPpmFlowLogService ppmFlowLogService;

    /**
     * 切入点
     */
    @Pointcut("execution(* com.work.ppm.application.services.flowmanager.*.saveFlowLog(..))")
    public void pointCut() {
    }

    /**
     * 切入点
     */
    @Pointcut("execution(* com.work.ppm.application.services.flowmanager.*.addFlowLog(..))")
    public void pointCut2() {
    }

    /**
     * 后置通知
     *
     * @param point 对象
     */
    @Transactional("defaultTx")
    @After("pointCut()")
    public void addHistoryLog(JoinPoint point) {
        BpsWorkFlowDto bps = (BpsWorkFlowDto) point.getArgs()[0];
        String status = BpsUtil.getProcessInstState(bps.getProcessInstId(), bps.getUserId());
        if (!"7".equals(status)) {
            return;
        }
        //  根据流程id 查询流程日志

        List<PpmFlowLogDto> logDtoList = ppmFlowLogService.queryDetaiList(bps.getProcessInstId());
        // 未查询到日志
        if (null == logDtoList || logDtoList.isEmpty()) {
            throw new BizException(ApiCode.SELECT_DATA_FAIL, "流程结束后未查询到流程日志：" + bps.getProcessInstId());
        }
        logDtoList.stream().forEach(ii -> {
            PpmNewflowHistorylogDto dto = JsonUtil.jsonToClass(ii, PpmNewflowHistorylogDto.class);
            ppmNewflowHistorylogService.addPpmNewflowHistorylog(dto);
        });
        // 删除 流程日志的日志
        PpmFlowLog log = new PpmFlowLog();
        log.setFlowcaseno(bps.getProcessInstId());
        ppmFlowLogService.deletePpmFlowLog(log);

    }

    /**
     * 后置通知
     *
     * @param point 对象
     */
    @After("pointCut2()")
    @Transactional("defaultTx")
    public void addHistoryLog2(JoinPoint point) {
        BpsWorkFlowDto bps = (BpsWorkFlowDto) point.getArgs()[0];
        //  根据流程id 查询流程日志
        TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
        if (!taskBean.getIsFinished().equals("Y")) {
            return;
        }
        // 获取流程日志
        List<PpmFlowLogDto> logDtoList = ppmFlowLogService.queryDetaiList(bps.getProcessInstId());

        if (CollectionUtils.isNotEmpty(logDtoList)) {
            logDtoList.stream().forEach(ii -> {
                PpmNewflowHistorylogDto dto = JsonUtil.jsonToClass(ii, PpmNewflowHistorylogDto.class);
                ppmNewflowHistorylogService.addPpmNewflowHistorylog(dto);
            });
            // 删除 流程日志的日志
            PpmFlowLog log = new PpmFlowLog();
            log.setFlowcaseno(bps.getProcessInstId());
            ppmFlowLogService.deletePpmFlowLog(log);
        }
    }
}
