package com.tansun.wf.activiti.model;

/**
 * 交接request Vo
 */
public class WfHandoverQo {

    private String transferor;

    private String receiver;

    public String getTransferor() {
        return transferor;
    }

    public void setTransferor(String transferor) {
        this.transferor = transferor;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
