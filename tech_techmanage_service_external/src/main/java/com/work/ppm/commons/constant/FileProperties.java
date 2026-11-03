package com.work.ppm.commons.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件属性
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    /**
     * 上传存放路径
     */

    private String uploadPath;
    /**
     * 模板目录
     */
    private String templatePath;
    /**
     * 临时目录
     */
    private String tempPath;
    /**
     * 虚拟路径
     */

    private String staticAccessPath;
    /**
     * 域名
     */

    private String host;
    /**
     * 端口
     */
    private String port;
    /**
     * 预览
     */
    private String preview;
}
