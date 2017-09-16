/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.ai.btree.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface TaskAttribute {
    public String name() default "";

    public boolean required() default false;
}

