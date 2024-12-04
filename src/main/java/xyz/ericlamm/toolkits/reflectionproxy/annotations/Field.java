package xyz.ericlamm.toolkits.reflectionproxy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a method is associated with a specific field.
 * This annotation is used in metaprogramming frameworks for Java reflection to specify
 * the field name that a method is related to, following the pattern of getXXX, isXXX, or setXXX.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

    /**
     * Specifies the field name associated with the method.
     * The default value follows the pattern of getXXX, isXXX, or setXXX.
     *
     * @return the field name
     */
    String value() default "";
}
