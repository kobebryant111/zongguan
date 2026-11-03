package com.work.ppm.commons.annotaion;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类注解
 * 主要用于 eosuniqueTable 生成主键
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
    /**
     * value
     * @return string
     */
    String value() default "";
}
