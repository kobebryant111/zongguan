package com.tansun.wf.activiti.manager;

import com.tansun.wf.activiti.model.HiIdentityLinkEntity;
import org.camunda.bpm.engine.impl.db.DbEntity;
import org.camunda.bpm.engine.impl.persistence.AbstractManager;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HiIdentityLinkManager extends AbstractManager {

    public void insertHiIdentityLink(DbEntity object) {
        getDbSqlSession().getSqlSession().update("insertHiIdentityLink", object);
    }

    public List<HiIdentityLinkEntity> selectHiIdentityLinks(String taskId,String userId,String groupId,String type) {
        Map<String,String> parameter = new HashMap();
        parameter.put("taskId",taskId);
        parameter.put("userId",userId);
        parameter.put("groupId",groupId);
        parameter.put("type",type);
        return getDbSqlSession().getSqlSession().selectList("selectHiIdentityLinks", parameter);
    }
}
