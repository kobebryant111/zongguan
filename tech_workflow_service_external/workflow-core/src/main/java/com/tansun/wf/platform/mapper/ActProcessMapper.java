package com.tansun.wf.platform.mapper;

import com.tansun.workflow.model.ProcessInfo;
import org.apache.ibatis.annotations.Param;

public interface ActProcessMapper {
    Integer findIsTemplate(@Param("processInfo") ProcessInfo processInfo);

    void updateTemplateStatus(@Param("modelId") String modelId, @Param("isTemplate") Integer isTemplate);

    void updateProcessClasstify(@Param("classtify") String classtify, @Param("id") String id);

    void updateProcessClasstifyXml(@Param("bytes") byte[] bytes, @Param("deploymentId") String deploymentId);
}
