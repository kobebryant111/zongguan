package com.work.ppm.application.services.integratedmanager.impl;

import com.work.ppm.application.dto.flowmanager.BpsWorkFlowDto;
import com.work.ppm.application.dto.integratedmanager.PpmKqCalendarTodoDto;
import com.work.ppm.application.enums.WorkflowConclusionEnums;
import com.work.ppm.application.model.integratedmanager.PpmKqCalendarTodo;
import com.work.ppm.application.model.integratedmanager.PpmOutsourcingLzsq;
import com.work.ppm.application.services.integratedmanager.IPpmKqCalendarTodoService;
import com.work.ppm.commons.constant.BusinessConstant;
import com.work.ppm.commons.dao.BaseDaoSupper;
import com.work.ppm.commons.dao.IMyBatisDao;
import com.work.ppm.commons.exception.BizException;
import com.work.ppm.commons.pager.Pager;
import com.work.ppm.commons.utils.CamundaUtils;
import com.work.ppm.commons.utils.JsonUtil;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.service.WorkFlowHttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 考勤日历任务表 接口实现类
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service("ppmKqCalendarTodoService")
public class PpmKqCalendarTodoService extends BaseDaoSupper implements IPpmKqCalendarTodoService {
    /**
     * 实体类路径
     */
    private static String PPMKQCALENDARTODO = "com.work.ppm.application.model.integratedmanager.PpmKqCalendarTodo";
    // 流程定义ID
    private static final String FLOW_ID = "jjrsjdr";
    //流程名称
    private static final String FLOW_TITLE = "节假日数据导入";

    //工作流服务
    private final WorkFlowHttpClientService workFlowHttpClientService;
    //数据接口
    private final IMyBatisDao myBatisDao;

    @Override
    public void queryPager(PpmKqCalendarTodoDto ppmKqCalendarTodoDto, Pager pager) {
        Map<String, Object> map = JsonUtil.objectTOMap(ppmKqCalendarTodoDto, PpmKqCalendarTodoDto.class);
        myBatisDao.selectPager(PPMKQCALENDARTODO, map, pager, "queryPagerList");
    }

    @Override
    public int addPpmKqCalendarTodo(PpmKqCalendarTodoDto ppmKqCalendarTodoDto) {
        PpmKqCalendarTodo ppmKqCalendarTodo = JsonUtil.jsonToClass(ppmKqCalendarTodoDto, PpmKqCalendarTodo.class);
        // 如果需要请set 主键id
        ppmKqCalendarTodo.setId(getNextSequence(PpmKqCalendarTodo.class));
        myBatisDao.executionPojo(PPMKQCALENDARTODO, ppmKqCalendarTodo, IMyBatisDao.SQL.INSTER, "addPojo");
        return 1;
    }

    @Override
    public PpmKqCalendarTodoDto queryDetail(PpmKqCalendarTodoDto ppmKqCalendarTodoDto) {
        PpmKqCalendarTodo ppmKqCalendarTodo = JsonUtil.jsonToClass(ppmKqCalendarTodoDto, PpmKqCalendarTodo.class);
        PpmKqCalendarTodo data = myBatisDao.selectClass(PPMKQCALENDARTODO, ppmKqCalendarTodo,
                "queryDetail");
        PpmKqCalendarTodoDto item = JsonUtil.jsonToClass(data, PpmKqCalendarTodoDto
                .class);
        return item;
    }

    @Override
    public int updatePpmKqCalendarTodo(PpmKqCalendarTodoDto ppmKqCalendarTodoDto) {
        PpmKqCalendarTodo ppmKqCalendarTodo = JsonUtil.jsonToClass(ppmKqCalendarTodoDto, PpmKqCalendarTodo.class);
        return myBatisDao.executionPojo(PPMKQCALENDARTODO, ppmKqCalendarTodo, IMyBatisDao.SQL.UPDATE, "updatePojo");
    }

    @Override
    public int deletePpmKqCalendarTodo(PpmKqCalendarTodoDto ppmKqCalendarTodoDto) {
        PpmKqCalendarTodo ppmKqCalendarTodo = JsonUtil.jsonToClass(ppmKqCalendarTodoDto, PpmKqCalendarTodo.class);
        return myBatisDao.executionPojo(PPMKQCALENDARTODO, ppmKqCalendarTodo, IMyBatisDao.SQL.DELETE, "deleteById");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String start(PpmKqCalendarTodoDto info, BpsWorkFlowDto bps) {
        ProcessBean processBean = null;
        try {
            Long businessKey = getNextSequence(PpmOutsourcingLzsq.class);
            // 流程实例参数设置
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setTenantId(BusinessConstant.TENANT_ID); //租户ID
            processInfo.setProcessDefineKey(FLOW_ID);// 流程键值
            processInfo.setUserId(bps.getUserId());// 提交人id
            processInfo.setStartUserId(bps.getUserId());// 流程发起人id
            processInfo.setStartUserName(bps.getUserName()); //流程发起人名称
            processInfo.setBusinessKey(String.valueOf(businessKey));

            // 开始并提交流程
            processBean = workFlowHttpClientService.startAndSubmitProcess(processInfo);
            info.setId(businessKey);
            info.setInstanceid(processBean.getProcessInstanceId());
            info.setFlag(0L);
            info.setCreatedate(new Date());
            info.setCreateid(bps.getUserId());
            info.setTitle("导入最新节假日数据【同步数据中心】");
            info.setFlowid(FLOW_ID);
            this.addPpmKqCalendarTodo(info);

            // 日志处理
            bps.setProjectNo(String.valueOf(businessKey));
            bps.setConclusion(WorkflowConclusionEnums.PASS.getCode());
            bps.setSuggest("提交申请");
            bps.setApplydesc("节假日数据导入申请");
            bps.setRelaData(null);
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(processBean.getProcessInstanceId());
            bps.setActivityInstId(processBean.getCurrentActivityId());
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstName("导入最新节假日数据【同步数据中心】");
            bps.setProcessInstDesc("节假日数据导入流程发起");
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(processBean.getProcessInstanceId());
            CamundaUtils.addFlowLog(bps, taskBean);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            CamundaUtils.deleteProcessInstance(processBean.getProcessInstanceId());
            log.error("添加并发起流程失败！");
            throw new BizException(e);
        }
    }

    @Override
    public String approve(PpmKqCalendarTodoDto ppmKqCalendarTodoDto, BpsWorkFlowDto bps) {
        try {
            TaskBean param = new TaskBean();
            param.setTenantId(BusinessConstant.TENANT_ID);// 租户
            param.setTaskId(bps.getTaskId());// 任务id
            param.setUserId(bps.getUserId());// 处理人
            param.setComment(bps.getSuggest());// 审批意见
            Map mPara = new HashMap();
            mPara.put("status", "return");
            // 通过状态
            if (WorkflowConclusionEnums.PASS.getCode().equals(bps.getConclusion())) {
                mPara.put("status", "pass");
                ppmKqCalendarTodoDto.setFlag(1L);
                ppmKqCalendarTodoDto.setCompletedate(new Date());
                this.updatePpmKqCalendarTodo(ppmKqCalendarTodoDto);
            }
            param.setmPara(mPara);
            // 提交任务
            workFlowHttpClientService.submitTask(param);
            // 日志处理
            bps.setProcessDefName(FLOW_ID);
            bps.setProcessInstId(bps.getProcessInstId());
            bps.setApplydesc("节假日数据导入");
            bps.setRelaData(mPara);
            // 获取流程信息
            TaskBean taskBean = CamundaUtils.getTasksByProcessInstanceId(bps.getProcessInstId());
            CamundaUtils.addFlowLog(bps, taskBean);
            // 返回下一节点信息
            return CamundaUtils.findNextTaskInfo(taskBean, null);
        } catch (Exception e) {
            log.error("导入数据审批失败！");
            throw new BizException(e);
        }
    }
}
