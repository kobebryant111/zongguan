package com.work.ppm.commons.annotaion.esb;

import java.lang.annotation.*;

/**
 * 返回esb body
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface EsbBody {

}
