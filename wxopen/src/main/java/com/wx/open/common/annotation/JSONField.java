package com.wx.open.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *  字段格式化注解
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface JSONField {
	String name() default "";
	String format() default "";
	boolean serialize() default true;
	boolean deserialize() default true;
	boolean uppercase() default false;
	boolean lowercase() default false;
}
