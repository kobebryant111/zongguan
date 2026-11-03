package com.tansun.wf.mocktest.query;

import com.tansun.wf.extend.db.WfBaseQuery;

public class WfTestPlanQuery extends WfBaseQuery {

    private String planCode;
    private String planName;
    private String tenantId;
    private String processDefKey;

    public WfTestPlanQuery planCode(String planCode){
        this.planCode = planCode;
        return this;
    }
    public WfTestPlanQuery planName(String planName){
        this.planName = planName;
        return this;
    }
    public WfTestPlanQuery tenantId(String tenantId){
        this.tenantId = tenantId;
        return this;
    }
    public WfTestPlanQuery processDefKey(String processDefKey){
        this.processDefKey = processDefKey;
        return this;
    }

}
