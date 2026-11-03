package com.tansun.workflow.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkflowApiReqeusts {
    /**
     * 降低代码冗余程度，若只显示字段且数量多，使用该属性可降低代码量，提高可读性
     * @return
     */
    String[] simpleRequest() default {};

    /**
     * 请求字段，需要设置必输、描述等功能时使用
     * @return
     */
    WorkflowApiReqeust[] value() default {};
}
