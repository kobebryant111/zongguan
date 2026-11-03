package com.tansun.wf.platform.mapper;

import com.tansun.wf.extend.db.WfBaseMapper;
import com.tansun.wf.platform.model.WfJobEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WfJobMapper extends WfBaseMapper<WfJobEntity> {

    List<WfJobEntity> findErrorServiceTask(@Param("wfJobEntity") WfJobEntity wfJobEntity);
}
