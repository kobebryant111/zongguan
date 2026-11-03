package com.tansun.workflow.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkflowApiReqeust {

    /**
     * 字段名
     * @return
     */
    String value();

    /**
     * 是否必输
     * @return
     */
    boolean require() default false;

    /**
     * 字段描述
     * @return
     */
    String notes() default "";

    String type() default "";

    WorkflowApiField[] fields() default {};
}
