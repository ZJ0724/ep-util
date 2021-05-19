package com.easipass.util.entity.po;

import com.zj0724.common.component.jdbc.AccessDatabaseJdbc;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    String name();

    AccessDatabaseJdbc.FieldType type();

}