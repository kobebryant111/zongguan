package com.work.ppm.application.dto.inforTechOut;

import lombok.Data;

@Data
public class PurchaseXklx {
    /**
     * 综管项目编号
     */
    private String xmbh;
    /**
     * 综管项目名称
     */
    private String xmmc;
    /**
     * 文件名称(带后缀)
     */
    private String fileName;
    /**
     * 存储对象id
     */
    private String objId;
    /**
     * 项目经办人
     */
    private String xmjbr;
}
