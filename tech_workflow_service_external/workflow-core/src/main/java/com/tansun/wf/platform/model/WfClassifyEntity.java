package com.tansun.wf.platform.model;

import com.tansun.wf.extend.db.WfBaseEntity;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/4/27
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfClassifyEntity extends WfBaseEntity {

    private static final long serialVersionUID = -2390646882887647663L;

    private String parentId;

    private String parentIds;

    private String name;

    private Integer isDefault;

    private int sort;
    /**
     * 机构编码
     */
    private String code;

    /**
     *  机构类型（1：公司；2：部门；3：小组）
      */
    private String type;
    /**
     * 是否可用
     */
    private String useable;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String remarks;

    private String delFlag;
    
    private  String  tenantId;

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    
    


    public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public WfClassifyEntity setProperties(Map<String, Object> params) {
        if (!StringUtils.isEmpty(MapUtils.getString(params, "code"))) {
            this.code = MapUtils.getString(params, "code");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "name"))) {
            this.name = MapUtils.getString(params, "name");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "parentId"))) {
            this.parentId = MapUtils.getString(params, "parentId");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "sort"))) {
            this.sort = MapUtils.getIntValue(params, "sort");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "type"))) {
            this.type = MapUtils.getString(params, "type");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "useable"))) {
            this.useable = MapUtils.getString(params, "useable");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "remarks"))) {
            this.remarks = MapUtils.getString(params, "remarks");
        }
        if (!StringUtils.isEmpty(MapUtils.getString(params, "isDefault"))) {
            this.isDefault =Integer.valueOf(MapUtils.getString(params, "isDefault"));
        }

        if (!StringUtils.isEmpty(MapUtils.getString(params, "tenantId"))) {
            this.tenantId = MapUtils.getString(params, "tenantId");
        }

        Date date = new Date();
        String loingName = "null";
        //修改
        if (!StringUtils.isEmpty(MapUtils.getString(params, "id"))) {
            this.id = MapUtils.getString(params, "id");
            this.updateDate = date;
            this.updateBy = loingName;
        } else {  //新增
            this.createDate = date;
            this.updateDate = date;
            this.createBy = loingName;
            this.updateBy = loingName;
            this.delFlag = "0";
            //新增分类
            if ("0".equals(this.parentId)) {
                this.parentIds = this.parentId + ",";
            } else {
                //新增子分类
                this.parentIds = MapUtils.getString(params, "parentIds") + this.parentId + ",";
            }
        }
        this.sort = 0;
        this.type = "1";
        return this;
    }
}
