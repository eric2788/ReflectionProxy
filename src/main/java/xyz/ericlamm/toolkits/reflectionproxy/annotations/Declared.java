package xyz.ericlamm.toolkits.reflectionproxy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a method should be declared up to a specified class.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Declared {

    /**
     * Specifies the class up to which the method should be declared.
     *
     * @return the class up to which the method should be declared
     */
    Class<?> upTo() default Object.class;
}
