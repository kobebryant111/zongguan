package com.tansun.wf.platform.service;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.service.WorkflowTaskExpandPlanService;
import com.tansun.wf.activiti.util.ConvertGenreUtil;
import com.tansun.wf.platform.mapper.WfClassifyMapper;
import com.tansun.wf.platform.model.WfClassifyEntity;
import com.tansun.workflow.model.ProcessBean;
import com.tansun.workflow.model.ProcessInfo;
import com.tansun.workflow.model.TaskBean;
import com.tansun.workflow.model.TaskInfo;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/6/1
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@Service
public class WfCompleteTaskService {

    @Autowired
    WorkFlowClientService workFlowClientService;

    @Autowired
    WfClassifyMapper wfClassifyMapper;

    @Autowired
    private WorkflowTaskExpandPlanService workflowTaskExpandPlanService;

    public PageInfo<Map> findWfCompleteTaskPage(TaskInfo taskInfo,PageInfo pageInfo) throws Exception{
        int pageNum = pageInfo.getPageNum() == 0  ? 1:pageInfo.getPageNum();
        int pageSize = pageInfo.getPageSize() == 0  ? 999: pageInfo.getPageSize();
        taskInfo.setCurrentPage(String.valueOf(pageNum));
        taskInfo.setPageSize(String.valueOf(pageSize));
        TaskBean taskBean = workFlowClientService.findTaskList(taskInfo);
        List<Map> taskList = taskBean.getTaskList();
        if (taskList != null) {
            for(Map task:taskList){
                if (StringUtils.isNotBlank(taskInfo.getWfClassifyCode())) {
                    task.put("isShow", "Y");
                    task.put("plans", workflowTaskExpandPlanService.getExpandPlanByClassifyCode(taskInfo.getWfClassifyCode(),taskInfo.gettUserId()));
                }else {
                    task.put("isShow", "N");
                }
                task.put("processList", findProcessList());
            }
        }
        PageInfo<Map> resultData = new PageInfo<>(taskList);
        resultData.setPageSize(pageSize);
        resultData.setTotal(StringUtils.isNumeric(taskBean.getTotal())?Long.parseLong(taskBean.getTotal()):0);
        return resultData;
    }

    public TaskBean executeOperation(TaskBean taskBean) throws Exception{
        return workFlowClientService.rebackTask(taskBean);
    }

    public Map<String, Object> getWfCompleteTaskButton(TaskInfo taskInfo){
        Map<String, Object> map = new HashMap<>(2);
        //默认调用分页查询方法。
        TaskBean taskBean = workFlowClientService.findButtonPower(taskInfo);
        //判断是否存在数据
        String actButton = taskBean.getActButton();
        if (StringUtils.isBlank(actButton)) {
            return map;
        }
        List<Map<String, Object>> listMap = ConvertGenreUtil.convertJsonStrArrayListToListMap(actButton);
        List<Map<String, Object>> maps = new ArrayList<>();
        for(Map<String, Object> m:listMap){
            if ("getBack".equals(m.get("key"))) {
                maps.add(m);
            }
        }
        map.put("plans", workflowTaskExpandPlanService.getExpandPlanByClassifyCode("",taskInfo.getTenantId()));
        map.put("buttons", maps);
        return map;

    }

    public List<Map<Object,Object>> findProcessList(){
        ProcessBean bean = workFlowClientService.processDefinitionList(new ProcessInfo());
        List<Map<Object,Object>> list = new ArrayList<>();
        if(bean != null && bean.getResultList() != null && bean.getResultList().size() > 0){
            for(Map<String, Object> rmap : bean.getResultList()){
                Map<Object,Object> map = new HashMap<>();
                if(rmap.get("processDefineName") != null){
                    map.put("processDefineId", rmap.get("processDefineId"));
                    map.put("processDefineName", rmap.get("processDefineName"));
                    list.add(map);
                }
            }
        }
        return list;
    }


}
