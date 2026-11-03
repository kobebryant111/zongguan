package com.tansun.wf.platform.mapper;

import com.tansun.wf.activiti.model.HiTaskEntity;
import com.tansun.workflow.model.ProcessInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author jali
 * @create 2019-08-23 10:03
 */
@Repository
public interface ActHiTaskinstMapper {

    /**
     * 获取带扩展字段的流程轨迹（已办）
     * @param processInfo
     * * processInstanceId 流程实列ID
     * @return
     */
    public List<Map<String,Object>> selectHiProcinstWithHiTaskExpand(@Param("processInfo") ProcessInfo processInfo, @Param("fieldMapping") List<String> fieldMapping);

    /**
     * 获取带扩展字段的流程轨迹（待办）
     * @param processInfo
     * * processInstanceId 流程实列ID
     * @return
     */
    public List<Map<String,Object>> selectHiProcinstWithTaskExpand(@Param("processInfo") ProcessInfo processInfo, @Param("fieldMapping") List<String> fieldMapping);

    /**
     * 获取任务节点上一步信息
     */
    public List<Map<String,Object>> selectPreHiTask(Map params);
    /**
     * 修改历史任务处理人
     */
    public void updateHiTaskAssignee(HiTaskEntity hiTaskEntity);

    public String querySliceValue(@Param("taskId") String taskId, @Param("processInstanceId") String processInstanceId, @Param("sliceColumnName") String sliceColumnName);
}
