package com.work.ppm.commons.annotaion.esb;

import java.lang.annotation.*;

/**
 * 校验esb结果，失败抛异常
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EsbCheckResult {

}
