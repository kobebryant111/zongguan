package com.work.ppm.application.dto.systemmanager;

import com.component.work.auth.domain.LoginPojo;
import lombok.Data;

/**
 * 登录
 */
@Data
public class LoginDto extends LoginPojo {
    /**
     * token
     */
    private String token;
    /**
     * ip
     */
    private String ip;
    /**
     * 单点登录类型 1：仅当天有效登录，默认：1, 2：永远有效
     */
    private String loginType;
}
