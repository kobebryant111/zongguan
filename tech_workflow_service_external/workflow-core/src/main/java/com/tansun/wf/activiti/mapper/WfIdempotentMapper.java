package com.tansun.wf.activiti.mapper;

import com.tansun.wf.activiti.model.WfIdempotentEntity;
import org.apache.ibatis.annotations.Param;

public interface WfIdempotentMapper {

    /**
     * 根据交易id查询记录
     * 没有，删掉
     * @param transactionId
     * @return
     */
    WfIdempotentEntity findByTransactionId(@Param("transactionId") String transactionId);
}
