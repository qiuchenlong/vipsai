package com.vs.vipsai.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: cynid
 * Created on 3/13/18 10:53 AM
 * Description:
 *
 * 字段
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String column();

    boolean isNotNull() default false;
}
