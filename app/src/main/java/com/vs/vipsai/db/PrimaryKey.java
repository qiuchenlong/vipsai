package com.vs.vipsai.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: cynid
 * Created on 3/13/18 10:52 AM
 * Description:
 *
 * 主键
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
    boolean autoincrement() default true;

    String column();
}
