package com.decerto.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogEntryExit {

    ChronoUnit unit() default ChronoUnit.MILLIS;

    boolean showArgs() default true;

    boolean showResult() default false;

    boolean showExecutionTime() default true;
}