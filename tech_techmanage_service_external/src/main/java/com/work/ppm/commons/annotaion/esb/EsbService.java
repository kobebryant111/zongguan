package com.work.ppm.commons.annotaion.esb;

import com.work.ppm.commons.handler.EsbHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * esb自定义实现
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(EsbHandler.class)
public @interface EsbService {
}
