package com.tansun.workflow.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkflowApiResponse {

    /**
     * 字段名
     * @return
     */
    String value();

    /**
     * 描述
     * @return
     */
    String notes() default "";

    /**
     * 多用于指定复杂字段的类型，如List<Map<String,Object>>
     * @return
     */
    String type() default "";

    WorkflowApiField[] fields() default {};
}
