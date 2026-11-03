package com.tansun.wf.platform.mapper;


import com.tansun.wf.activiti.model.TaskExpandDataEntity;
import com.tansun.wf.extend.db.WfBaseMapper;
import com.tansun.wf.platform.model.WfTaskExpandDataEntity;
import com.tansun.workflow.model.TaskInfo;
import org.apache.ibatis.annotations.Param;
import org.camunda.bpm.engine.impl.db.DbEntity;

import java.util.List;
import java.util.Map;


/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/25
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public interface WfTaskExpandDataMapper extends WfBaseMapper<WfTaskExpandDataEntity> {

    public void insertTaskExpandData(DbEntity object);

    public List<Map> userTaskCount(@Param("userId") String userId);

    TaskExpandDataEntity findTaskUrlAndNameInfo(TaskInfo taskInfo);
}
