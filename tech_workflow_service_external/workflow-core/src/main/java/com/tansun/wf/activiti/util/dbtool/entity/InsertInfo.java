package com.tansun.wf.activiti.util.dbtool.entity;

import java.util.List;

public class InsertInfo {

    /** 表名称 */
    private String name;
    /** 列名称 */
    private String[] cs;
    /** 列值 */
    private List<Object> va;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCs() {
        return cs;
    }

    public void setCs(String[] cs) {
        this.cs = cs;
    }

    public List<Object> getVa() {
        return va;
    }

    public void setVa(List<Object> va) {
        this.va = va;
    }
}
