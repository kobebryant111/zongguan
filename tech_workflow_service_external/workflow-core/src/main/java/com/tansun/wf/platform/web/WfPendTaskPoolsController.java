package com.tansun.wf.platform.web;

import com.github.pagehelper.PageInfo;
import com.tansun.wf.activiti.util.PageUtils;
import com.tansun.wf.extend.vo.WfResponseVO;
import com.tansun.wf.platform.model.WfUserTaskEntity;
import com.tansun.wf.platform.service.PlatformEditorService;
import com.tansun.workflow.model.*;
import com.tansun.workflow.service.WorkFlowClientService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/21
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
@RestController
@RequestMapping("/wf/wfPendTaskPools")
public class WfPendTaskPoolsController {



    @Autowired
    WorkFlowClientService workFlowClientService;

    @Autowired
    PlatformEditorService platformEditorService;

    @ResponseBody
    @RequestMapping("/getUserTaskByProcessId")
    public WfResponseVO getAllClassifyPendTasks(@RequestParam Map<String,Object> reqMap, PageInfo page) throws Exception {
        String ProcessInstanceId = MapUtils.getString(reqMap,"processInstanceId");
        if(StringUtils.isBlank(ProcessInstanceId)){
            return WfResponseVO.errorResponse("流程实例ID为空");
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setProcessInstanceId(ProcessInstanceId);
        TaskBean taskBean = workFlowClientService.findTaskList(taskInfo);
        List<Map> list = taskBean.getTaskList();
        if(CollectionUtils.isEmpty(list)){
            return WfResponseVO.errorResponse("流程ID无效");
        }
        // 因为是按流程实例id 有且仅有一条
        Map map = list.get(0);
        List<String> allLoginUsersNameList = (List<String>)map.get("powerUsers");
        List<String> loginUsersNameList = PageUtils.subList(allLoginUsersNameList,page.getPageNum(), page.getPageSize());
        List<WfUserTaskEntity> userTaskEntityList = new ArrayList<>();
        Map<String,Object> resultMap = new HashMap<>();
        PageInfo<WfUserTaskEntity> pageInfo = new PageInfo<>();
        if(CollectionUtils.isEmpty(loginUsersNameList)){
            pageInfo.setList(userTaskEntityList);
            pageInfo.setTotal(allLoginUsersNameList.size());
            resultMap.put("grid",pageInfo);
           return WfResponseVO.successResponse(resultMap);
        }else{
            EditorInfo editorInfo = new EditorInfo();
            editorInfo.setUserLoginNames(loginUsersNameList);
            List<Map<String, Object>> userList = platformEditorService.findUsersList(editorInfo).getUserList();
            taskInfo.setAssignees(loginUsersNameList);
//            List<Map> listUserTaskCountList = workFlowClientService.findTaskCounts(taskInfo).getTaskList();
            HashMap<String,Object> userMap = new HashMap();
//            for(int i = 0;i < listUserTaskCountList.size(); i++){
//                userMap.put(listUserTaskCountList.get(i).get("assignee").toString(),listUserTaskCountList.get(i));
//            }
            for(int i = 0;i < userList.size(); i++){
                WfUserTaskEntity userTaskEntity = new WfUserTaskEntity();
                String userLoginName = userList.get(i).get("userLoginName").toString();
                userTaskEntity.setLoginUserName(userLoginName);
                userTaskEntity.setRealUserName(userList.get(i).get("userName").toString());
                String deptName = userList.get(i).get("deptName") == null ? "":userList.get(i).get("deptName").toString();
                String roleName = userList.get(i).get("roleName") == null ? "":userList.get(i).get("roleName").toString();
                String orgName = userList.get(i).get("orgName") == null ? "":userList.get(i).get("orgName").toString();
                Integer pendTaskCount = 0;
                Integer completeTaskCount = 0;
                if(userMap.get(userLoginName) != null){
                    Map<String,Object> userTask = (Map<String,Object>)userMap.get(userLoginName);
//                    pendTaskCount = userTask.get("taskCount") == null ? 0:Integer.parseInt(listUserTaskCountList.get(i).get("taskCount").toString());
//                    completeTaskCount = userTask.get("hiEndTaskCount") == null ? 0:Integer.parseInt(listUserTaskCountList.get(i).get("hiEndTaskCount").toString());
                }
                userTaskEntity.setDeptName(deptName);
                userTaskEntity.setRoleName(roleName);
                userTaskEntity.setOrgName(orgName);
                userTaskEntity.setPendTaskCount(pendTaskCount);
                userTaskEntity.setCompleteTaskCount(completeTaskCount);
                userTaskEntityList.add(userTaskEntity);
            }
            pageInfo.setList(userTaskEntityList);
            pageInfo.setTotal(allLoginUsersNameList.size());
            resultMap.put("grid",pageInfo);
            return WfResponseVO.successResponse(resultMap);
        }

    }
}
