package com.tansun.wf.mocktest.query;

import com.tansun.wf.extend.db.WfBaseQuery;

public class WfTestInsQuery extends WfBaseQuery {

    private String planCode;
    private String processInstanceId;
    private String processDefkey;

    public WfTestInsQuery planCode(String planCode){
        this.planCode = planCode;
        return this;
    }
    public WfTestInsQuery processInstanceId(String processInstanceId){
        this.processInstanceId = processInstanceId;
        return this;
    }
    public WfTestInsQuery processDefkey(String processDefkey){
        this.processDefkey = processDefkey;
        return this;
    }

}
