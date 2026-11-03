package com.tansun.workflow.model;

import java.io.Serializable;
import java.util.List;

/**
 * @作者: 卓福深 zhuofushen@tansun.com.cn
 * @类描述
 * @创建时间 2020/4/21
 * @版本 v1.01
 * @修改记录 版本 修改人 修改时间 修改内容描述
 * ----------------------------------------------
 * ----------------------------------------------
 */
public class BatchExecuteParam implements Serializable {
    private static final long serialVersionUID = 1L;
    private List argList;
    private String methodName;




    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List getArgList() {
        return argList;
    }

    public void setArgList(List argList) {
        this.argList = argList;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


}
