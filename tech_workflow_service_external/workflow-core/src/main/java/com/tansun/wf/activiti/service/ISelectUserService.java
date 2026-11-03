package com.tansun.wf.activiti.service;

import com.tansun.wf.activiti.model.SelectUserMode;
import com.tansun.workflow.model.TaskInfo;

public interface ISelectUserService {

	   public void selectUser(TaskInfo taskInfo ,SelectUserMode selectUserMode) ;
	
}
