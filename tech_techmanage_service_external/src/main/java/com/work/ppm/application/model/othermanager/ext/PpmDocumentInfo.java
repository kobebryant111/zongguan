package com.work.ppm.application.model.othermanager.ext;

import com.work.ppm.application.model.othermanager.PpmDocument;
import lombok.Data;

/**
 * 扩展
 */
@Data
public class PpmDocumentInfo extends PpmDocument {
    /**
     * 文档唯一编号，多个逗号分开
     */
    private String documentnos;
}
