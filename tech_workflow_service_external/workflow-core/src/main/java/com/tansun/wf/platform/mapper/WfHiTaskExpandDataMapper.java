package com.tansun.wf.platform.mapper;

import com.tansun.wf.activiti.model.HiTaskExpandDataEntity;
import com.tansun.wf.activiti.query.WfHiTaskQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WfHiTaskExpandDataMapper {

    List<HiTaskExpandDataEntity> selectByHiTaskId(WfHiTaskQuery wfHiTaskQuery);

}
