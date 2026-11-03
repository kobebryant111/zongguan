package com.tansun.wf.activiti.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 工作流幂等操作注解，被注解的方法要支持幂等
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkflowIdempotent {

    /**
     * true 支持幂等，false不支持
     * @return
     */
    boolean value() default true;

}
