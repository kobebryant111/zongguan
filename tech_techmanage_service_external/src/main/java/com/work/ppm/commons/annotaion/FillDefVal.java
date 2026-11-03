package com.work.ppm.commons.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 填充默认值
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FillDefVal {

    /**
     * 默认值
     * @return String
     */
    public String value();
}
