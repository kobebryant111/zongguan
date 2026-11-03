package com.tansun.workflow.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkflowApiField {

    /**
     * 字段名
     * @return
     */
    String key();

    /**
     * 描述
     * @return
     */
    String value() default "";

    /**
     * 字段类型，非string请填全类名，便于理解
     * @return
     */
    String type() default "";

    /**
     * 例子
     * @return
     */
    String example() default "";


}
