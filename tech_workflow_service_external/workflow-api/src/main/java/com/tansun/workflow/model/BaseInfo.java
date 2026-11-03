package com.tansun.workflow.model;

import java.io.Serializable;
import java.util.Map;

public class BaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易id,每次交易id不一样,重试的交易一样
     * @ignore
     */
//    @ApiModelProperty
    private  String transactionId = null;

    /**
     * 分库表的切分字段值
     * @ignore
     */
    private String sliceValue;

    /**
     * 分片表的业务信息
     * @ignore
     */
    private Map<String,Object> sliceValueMap;


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setSliceValue(String sliceValue){
        this.sliceValue = sliceValue;
    }

    public String getSliceValue() {
        return sliceValue;
    }

    public Map<String, Object> getSliceValueMap() {
        return sliceValueMap;
    }

    public void setSliceValueMap(Map<String, Object> sliceValueMap) {
        Object slice_value = sliceValueMap.get("slice_value");
        if (slice_value instanceof String && this.sliceValue == null) {
            this.sliceValue = (String) slice_value;
        }
        this.sliceValueMap = sliceValueMap;
    }
}
