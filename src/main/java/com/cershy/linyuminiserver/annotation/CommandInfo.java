package com.cershy.linyuminiserver.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommandInfo {

    String description();

    String name();
}
