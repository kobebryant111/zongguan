package com.tansun.wf.mocktest.query;

import com.tansun.wf.extend.db.WfBaseQuery;

public class WfSkipTaskConfigQuery extends WfBaseQuery {

    private String processDefKey;
    private String isOpen;
    private String isGlobal;
    private String tenantId;

    public WfSkipTaskConfigQuery processDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
        return this;
    }


    public WfSkipTaskConfigQuery isOpen(String isOpen) {
        this.isOpen = isOpen;
        return this;
    }

    public WfSkipTaskConfigQuery isGlobal(String isGlobal) {
        this.isGlobal = isGlobal;
        return this;
    }

    public WfSkipTaskConfigQuery tenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }
}
