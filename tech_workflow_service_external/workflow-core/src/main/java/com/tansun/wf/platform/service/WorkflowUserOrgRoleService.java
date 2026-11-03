package com.tansun.wf.platform.service;

import com.tansun.wf.platform.mapper.WfUserOrgRoleMapper;
import com.tansun.workflow.api.IWorkflowUserOrgRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class WorkflowUserOrgRoleService implements IWorkflowUserOrgRoleService {

    @Autowired
    WfUserOrgRoleMapper wfUserOrgRoleMapper;

	@Override
	public List<Map<String, Object>> findOrgTaskCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.wfUserOrgRoleMapper.findOrgTaskCount(params);
	}
}
