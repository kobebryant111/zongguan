package com.tansun.wf.extend.db;

/**
 * 查询条件基础类
 */
public abstract class WfBaseQuery {
    private String id;



    public WfBaseQuery id(String id) {
        this.id = id;
        return this;
    }


}
