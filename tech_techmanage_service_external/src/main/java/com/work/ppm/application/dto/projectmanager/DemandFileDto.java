package com.work.ppm.application.dto.projectmanager;

import lombok.Data;

/**
 * 科管审批附件
 *
 */
@Data
public class DemandFileDto {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件真实名称
     */
    private String fileRealName;

    /**
     * 文件类型
     * value: {需求,采购}
     */
    private String type;

}
