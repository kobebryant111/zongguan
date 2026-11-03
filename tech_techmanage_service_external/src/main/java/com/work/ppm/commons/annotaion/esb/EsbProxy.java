package com.work.ppm.commons.annotaion.esb;

import java.lang.annotation.*;

/**
 * esb透传接口
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EsbProxy {

    /**
     * 服务名称
     * 默认取字典  ESB_ADDRESS  svcNm
     * @return String
     */
    public String svcNm() default "";

    /**
     * 跳转地址
     * @return String
     */
    public String url();

    /**
     * 发送类型
     * @return String
     */
    public String sendTp();

    /**
     * 透传标志
     * @return String
     */
    public String psThgFlg();

    /**
     * 交易代码
     * @return String
     */
    public String txnCd();
}
