package com.tansun.workflow.api;

import java.util.List;
import java.util.Map;

public interface IWorkflowUserOrgRoleService {


    List<Map<String,Object>> findOrgTaskCount(Map<String,Object> params);
}