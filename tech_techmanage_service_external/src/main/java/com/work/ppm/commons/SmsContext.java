package com.work.ppm.commons;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SmsContext implements Serializable {
    /**
     * 验证码
     */
    @Getter
    private String code;
    /**
     * 过期时间戳
     */
    private long expiryTime;
    /**
     * 当前已尝试次数
     */
    @Getter
    private int attempts;

    /**
     * 防抖时间戳
     */
    @Getter
    private LocalDateTime lastSendTime;
    /**
     * 最大尝试次数
     */
    private final int MAX_ATTEMPTS = 10;

    public SmsContext(String code, long durationInMinutes, LocalDateTime lastSendTime) {
        this.code = code;
        this.expiryTime = System.currentTimeMillis() + (durationInMinutes * 60 * 1000);
        this.attempts = 0;
        this.lastSendTime = lastSendTime;
    }

    // 递增尝试次数
    public void incrementAttempts() {
        this.attempts++;
    }

    // 判断是否超过最大尝试次数
    public boolean isExceeded() {
        return this.attempts >= MAX_ATTEMPTS;
    }

    // 判断是否过期
    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }

}
