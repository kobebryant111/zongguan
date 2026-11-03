package com.work.ppm.application.oss.mercury;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 对象存储接入参数
 *
 */
@Component
@ConfigurationProperties(prefix = "mercuryoa.oss")
public class Mercuryoa {

    /**
     * ak
     */
    private String accessKey;
    /**
     * sk
     */
    private String secretKey;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }



}
