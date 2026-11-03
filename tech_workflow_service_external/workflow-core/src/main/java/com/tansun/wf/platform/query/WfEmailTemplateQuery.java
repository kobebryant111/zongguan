package com.tansun.wf.platform.query;

import com.tansun.wf.extend.db.WfBaseQuery;

public class WfEmailTemplateQuery extends WfBaseQuery {

    /** ID */
    private String id;
    /** 所属用户号 */
    private String tenantId;
    /** 模板内容 */
    private String tempBody;
    /** 删除标记（0：正常；1：删除） */
    private String delFlag;
    /** 状态1失效0生效 */
    private String tempStatus;
    /** 模板名称 */
    private String tempName;
    /** 模板标签 */
    private String tempTag;

    public String getTempTag() {
        return tempTag;
    }

    public void setTempTag(String tempTag) {
        this.tempTag = tempTag;
    }

    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTempBody() {
        return tempBody;
    }

    public void setTempBody(String tempBody) {
        this.tempBody = tempBody;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getTempStatus() {
        return tempStatus;
    }

    public void setTempStatus(String tempStatus) {
        this.tempStatus = tempStatus;
    }

}
