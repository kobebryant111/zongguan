package com.work.ppm.commons.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统配置
 *
 */
@Data
@Component
public class SysConfig {

    /**
     * 文件上传路径
     */
    @Value("${file.upload-path}")
    private String fileUploadPath;
}
