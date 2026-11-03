package com.tansun.wf.platform.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WfUserOrgRoleMapper {
    
    List<Map<String,Object>> findOrgTaskCount(Map<String,Object> params);
}
