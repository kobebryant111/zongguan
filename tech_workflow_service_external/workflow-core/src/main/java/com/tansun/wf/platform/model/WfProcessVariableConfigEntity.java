package com.tansun.wf.platform.model;

import com.tansun.wf.extend.db.WfBaseEntity;

/**
 * @author: 卓福深 zhuofushen@tansun.com.cn
 * @类描述 <pre></pre>
 * @创建时间 2020/10/16
 * @版本 v1.01
 * @修改记录 <pre>
 * 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 * </pre>
 */
public class WfProcessVariableConfigEntity extends WfBaseEntity {
    /** 流程分类ID */
    private String classifyId;
    /** 流程分类名称 */
    private String classifyName;
    /** 变量编号 */
    private String varCode;
    /** 变量名称 */
    private String varName;
    /** 变量值类型（字符串、布尔、时间、数组、整数、Long、Double） */
    private String varType;
    /** 变量分类（系统变量，业务变量） */
    private String varClass;
    /** 是否有效 */
    private String useable;

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getVarCode() {
        return varCode;
    }

    public void setVarCode(String varCode) {
        this.varCode = varCode;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVarType() {
        return varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public String getVarClass() {
        return varClass;
    }

    public void setVarClass(String varClass) {
        this.varClass = varClass;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /** 租户 */
    private String tenantId;
}
