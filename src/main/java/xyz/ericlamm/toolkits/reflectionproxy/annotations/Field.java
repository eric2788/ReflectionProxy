package xyz.ericlamm.toolkits.reflectionproxy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
	/**
	 * the field name, default following the pattern of getXXX or isXXX or setXXX
	 * @return the field name
	 */
	String value() default "";
}
