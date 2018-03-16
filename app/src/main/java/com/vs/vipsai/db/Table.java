package com.vs.vipsai.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: cynid
 * Created on 3/13/18 10:55 AM
 * Description:
 *
 * 表
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public  @interface Table {
    String tableName();
}
