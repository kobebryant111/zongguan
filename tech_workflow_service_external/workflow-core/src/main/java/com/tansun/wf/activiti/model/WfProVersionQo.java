package com.tansun.wf.activiti.model;

import java.util.Date;

/**
 * 流程版本统计qo
 */
public class WfProVersionQo {

    // 流程名称模糊查询
    private String modelNameLike;

    /** 流程类别ID */
    private String classifyId;

    /** 租户ID */
    private String tenantId;

    /** 页码 */
    private int page;

    /** 分页大小 */
    private int pageSize;

    public String getModelNameLike() {
        return modelNameLike;
    }

    public void setModelNameLike(String modelNameLike) {
        this.modelNameLike = modelNameLike;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
