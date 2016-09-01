package com.github.developframework.scanner.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性注解
 * 
 * @author qiuzhhenhao
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScanProperty {

	/**
	 * 别名
	 * 
	 * @return
	 */
	String alias();

	/**
	 * 默认值
	 * 
	 * @return
	 */
	String ifMissingOfValue() default "";
}
