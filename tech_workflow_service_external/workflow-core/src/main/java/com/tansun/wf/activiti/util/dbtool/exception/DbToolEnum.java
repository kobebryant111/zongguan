package com.tansun.wf.activiti.util.dbtool.exception;

public enum DbToolEnum {

    ERROR(500, "内部异常"),
    ;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    DbToolEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
