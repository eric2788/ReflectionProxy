package xyz.ericlamm.toolkits.reflectionproxy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a class is associated with a specific value.
 * This annotation is used in metaprogramming frameworks for Java reflection to specify
 * the class that a particular value is related to.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForClass {

    /**
     * Specifies the value associated with the class.
     *
     * @return the value associated with the class
     */
    String value();
}
