package com.tansun.wf.platform.model;

import com.tansun.wf.extend.db.WfBaseEntity;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

public class WfEmailTemplateEntity extends WfBaseEntity {

    /** ID */
    private String id;
    /** 所属用户号 */
    private String tenantId;
    /** 模板内容 */
    private String tempBody;
    /** 创建时间 */
    private Date createDate;
    /** 修改时间 */
    private Date updateDate;
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

    @Override
    public String getId() {
        return id;
    }

    @Override
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public WfEmailTemplateEntity setProperties(Map<String, Object> params) {
        if (!StringUtils.isEmpty(MapUtils.getString(params, "tempBody"))) {
            this.tempBody = MapUtils.getString(params, "tempBody");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "tenantId"))) {
            this.tenantId = MapUtils.getString(params, "tenantId");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "tempName"))) {
            this.tempName = MapUtils.getString(params, "tempName");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "tempTag"))) {
            this.tempTag = MapUtils.getString(params, "tempTag");
        }
        Date date = new Date();
        //修改
        if (!StringUtils.isEmpty(MapUtils.getString(params, "id"))) {
            this.id = MapUtils.getString(params, "id");
            this.updateDate = date;
            if (!StringUtils.isEmpty(MapUtils.getString(params, "delFlag"))) {
                this.delFlag = MapUtils.getString(params, "delFlag").equals("0")?"0":"1";
            }
            if (!StringUtils.isEmpty(MapUtils.getString(params, "tempStatus"))) {
                this.tempStatus = MapUtils.getString(params, "tempStatus").equals("0")?"0":"1";
            }
        } else {  //新增
            this.createDate = date;
            this.updateDate = date;
            this.delFlag = "0";
            this.tempStatus = "0";
        }
        return this;
    }
}
