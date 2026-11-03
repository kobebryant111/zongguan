package com.tansun.wf.platform.query;

import com.tansun.wf.extend.db.WfBaseQuery;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/14
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfTaskExpandPlanQuery extends WfBaseQuery {
    /**  流程分类ID */
    private String wfClassId;
    /**  拓展字段键值 */
    private String fieldKey;
    /**  拓展字段名称 */
    private String fieldName;
    /**  状态 */
    private String fieldStatus;
    /**  流程分类编码 */
    private String wfClassCode;
    /** 租户id */
    private String tenantId;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldStatus() {
        return fieldStatus;
    }

    public void setFieldStatus(String fieldStatus) {
        this.fieldStatus = fieldStatus;
    }

    public String getWfClassId() {
        return wfClassId;
    }

    public void setWfClassId(String wfClassId) {
        this.wfClassId = wfClassId;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getWfClassCode() {
        return wfClassCode;
    }

    public void setWfClassCode(String wfClassCode) {
        this.wfClassCode = wfClassCode;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
