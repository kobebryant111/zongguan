package com.tansun.wf.platform.model;

import com.tansun.wf.extend.db.WfBaseEntity;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/7/7
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfFormDetailEntity extends WfBaseEntity {
    /** 租户id*/
    private String tenantId;
    /** 删除标记（0：正常；1：删除）*/
    private String delFlag;
    /** 备注*/
    private String remarks;
    /** 修改时间*/
    private Date updateDate;
    /** 修改人*/
    private String updateBy;
    /** 创建时间*/
    private Date createDate;
    /** 创建人*/
    private String createBy;
    /** 字典类型*/
    private String dictType;

    private String showType;
    /** 可编辑字段(Y:可编辑，N:不可编辑)*/
    private String editable;
    /** 是否可为空*/
    private String defaultValue;
    /** 字段类型*/
    private String fieldType;
    /** 描述*/
    private String comments;
    /** 字段名称*/
    private String name;
    /** 表单主键*/
    private String formId;
    /**  排序（升序）*/
    private String sort;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public static WfFormDetailEntity coverToEntity(Map<String,Object> params, WfFormDetailEntity entity) {
        if (entity == null) {
            entity = new WfFormDetailEntity();
        }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "formId"))){
                entity.setFormId(MapUtils.getString(params,"formId"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "name"))){
                entity.setName(MapUtils.getString(params,"name"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "comments"))){
                entity.setComments(MapUtils.getString(params,"comments"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "fieldType"))){
                entity.setFieldType(MapUtils.getString(params,"fieldType"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "defaultValue"))){
                entity.setDefaultValue(MapUtils.getString(params,"defaultValue"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "editable"))){
                entity.setEditable(MapUtils.getString(params,"editable"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "showType"))){
                entity.setShowType(MapUtils.getString(params,"showType"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "dictType"))){
                entity.setDictType(MapUtils.getString(params,"dictType"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "sort"))){
                entity.setSort(MapUtils.getString(params,"sort"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "createBy"))){
                entity.setCreateBy(MapUtils.getString(params,"createBy"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "updateBy"))){
                entity.setUpdateBy(MapUtils.getString(params,"updateBy"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "remarks"))){
                entity.setRemarks(MapUtils.getString(params,"remarks"));
            }
            if(!StringUtils.isEmpty(MapUtils.getString(params, "delFlag"))){
                entity.setDelFlag(MapUtils.getString(params,"delFlag"));
            }
            //BaseEntity
            if(!StringUtils.isEmpty(MapUtils.getString(params, "id"))){
                entity.setId(MapUtils.getString(params,"id"));
            }
            return entity;
    }
}
