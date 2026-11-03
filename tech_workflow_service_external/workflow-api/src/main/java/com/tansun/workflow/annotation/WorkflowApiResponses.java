package com.tansun.workflow.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkflowApiResponses {
    /**
     * 降低代码冗余程度，若只显示字段且数量多，使用该属性可降低代码量，提高可读性
     * @return
     */
    String[] simpleResponse() default {};

    /**
     * 返回字段，需要设置类型、描述等功能时使用
     * @return
     */
    WorkflowApiResponse[] value() default {};
}
