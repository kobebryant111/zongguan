package com.work.ppm.commons.annotaion.esb;

import java.lang.annotation.*;

/**
 * esb接口配置
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EsbConfig {

    /**
     * 服务码
     * @return String
     */
    public String serviceCode();

    /**
     * 渠道码
     * @return String
     */
    public String serviceScene();

    /**
     * 交易码
     * @return String
     */
    public String tranCode();
}
