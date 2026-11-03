package com.tansun.wf.platform.model;

import com.tansun.wf.extend.db.WfBaseEntity;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/5/7
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfDefinedButtonEntity extends WfBaseEntity {
    private static final long serialVersionUID = 8500487415751927704L;

    /**
     * 按钮key
     */
    private String key;

    /**
     * 按钮名称
     */
    private String name;

    /**
     * 按钮别名
     */
    private String alias;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序
     */
    private Integer buttonSort;

    /**
     * 租户
     * @return
     */
    private String tenantId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getButtonSort() {
        return buttonSort;
    }

    public void setButtonSort(Integer buttonSort) {
        this.buttonSort = buttonSort;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
