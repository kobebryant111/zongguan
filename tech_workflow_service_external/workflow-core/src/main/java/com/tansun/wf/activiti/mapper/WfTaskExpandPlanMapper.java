package com.tansun.wf.activiti.mapper;

import com.tansun.wf.activiti.model.TaskExpandPlanEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WfTaskExpandPlanMapper {

    List<TaskExpandPlanEntity> getExpandPlanByClassifyCodes(@Param("classifyCodes") List<String> classifyCodes);

    /**
     * 获取所有分类数量
     * @return
     */
    Long getAllExpandPlanClassifyCount();
}
