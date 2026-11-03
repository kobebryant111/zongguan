package com.work.ppm.application.dto.inforTechOut;

import lombok.Data;

@Data
public class XKFileUploadDto {
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 关联id
     */
    private String outReqid;
    /**
     * 上传人
     */
    private String badge;
    /**
     * 姓名
     */
    private String empname;
}
