package com.wx.open.controller.frame;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *  业务Service依赖自动装配
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ServiceAutowired {
	String value();
	String desc() default "";
}
