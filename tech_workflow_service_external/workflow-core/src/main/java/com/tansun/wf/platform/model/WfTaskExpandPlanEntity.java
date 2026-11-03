package com.tansun.wf.platform.model;

import com.tansun.wf.extend.db.WfBaseEntity;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

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
public class WfTaskExpandPlanEntity  extends WfBaseEntity  {
    private static final long serialVersionUID = 6168942281724566051L;
    /**  流程分类ID */
    private String wfClassId;
    /** 流程分类名称 */
    private String wfClassName;
    /**  拓展字段键值 */
    private String fieldKey;
    /** 拓展字段名称 */
    private String fieldName;
    /** 拓展字段数值 */
    private String fieldValue;
    /** 类型 */
    private String fieldType;
    /** 链接 */
    private String fieldUrl;
    /** 是否隐藏 */
    private String fieldStatus;
    /** 租户id */
    private String tenantId;
    /** 是否作为查询条件 */
    private String isQueryCondition;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWfClassId() {
        return wfClassId;
    }

    public void setWfClassId(String wfClassId) {
        this.wfClassId = wfClassId;
    }

    public String getWfClassName() {
        return wfClassName;
    }

    public void setWfClassName(String wfClassName) {
        this.wfClassName = wfClassName;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldUrl() {
        return fieldUrl;
    }

    public void setFieldUrl(String fieldUrl) {
        this.fieldUrl = fieldUrl;
    }

    public String getFieldStatus() {
        return fieldStatus;
    }

    public void setFieldStatus(String fieldStatus) {
        this.fieldStatus = fieldStatus;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getIsQueryCondition() {
        return isQueryCondition;
    }

    public void setIsQueryCondition(String isQueryCondition) {
        this.isQueryCondition = isQueryCondition;
    }

    public WfTaskExpandPlanEntity coverToEntity(Map<String,Object> params, WfTaskExpandPlanEntity entity) {
        if (entity == null) {
            entity = new WfTaskExpandPlanEntity();
        }
        try {
            if(!StringUtils.isEmpty(MapUtils.getString(params, "wfClassId"))){
                entity.setWfClassId(MapUtils.getString(params,"wfClassId"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "wfClassName"))){
                entity.setWfClassName(MapUtils.getString(params,"wfClassName"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "fieldKey"))){
                entity.setFieldKey(MapUtils.getString(params,"fieldKey"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "fieldName"))){
                entity.setFieldName(MapUtils.getString(params,"fieldName"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "fieldValue"))){
                entity.setFieldValue(MapUtils.getString(params,"fieldValue"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "fieldType"))){
                entity.setFieldType(MapUtils.getString(params,"fieldType"));
            }
            if (!StringUtils.isEmpty(MapUtils.getString(params, "fieldUrl"))) {
                entity.setFieldUrl(MapUtils.getString(params,"fieldUrl"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "fieldStatus"))){
                entity.setFieldStatus(MapUtils.getString(params,"fieldStatus"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "isQueryCondition"))){
                entity.setIsQueryCondition(MapUtils.getString(params,"isQueryCondition"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "id"))){
                entity.setId(MapUtils.getString(params,"id"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "tenantId"))){
                entity.setTenantId(MapUtils.getString(params,"tenantId"));
            }
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("数据转换异常");
        }
    }
}
