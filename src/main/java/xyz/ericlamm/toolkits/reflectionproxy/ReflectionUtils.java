package xyz.ericlamm.toolkits.reflectionproxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for reflection-related operations.
 */
public final class ReflectionUtils {

    /**
     * Retrieves all declared methods of a class up to (but not including) a specified parent class.
     *
     * @param startClass the class to start searching from
     * @param exclusiveParent the parent class to stop searching at
     * @return a list of declared methods
     */
    public static List<Method> getDeclaredMethodsUpTo(Class<?> startClass, Class<?> exclusiveParent) {
        List<Method> currentClassMethods = new ArrayList<>(Arrays.asList(startClass.getDeclaredMethods()));
        Class<?> parentClass = startClass.getSuperclass();
        if (parentClass != null && !(parentClass.equals(exclusiveParent))) {
            List<Method> parentClassFields = getDeclaredMethodsUpTo(parentClass, exclusiveParent);
            currentClassMethods.addAll(parentClassFields);
        }
        return currentClassMethods;
    }

    /**
     * Finds a declared method in a class up to (but not including) a specified parent class.
     *
     * @param startClass the class to start searching from
     * @param exclusiveParent the parent class to stop searching at
     * @param methodName the name of the method to find
     * @param parameterTypes the parameter types of the method to find
     * @return the found method, or null if not found
     */
    public static Method findDeclaredMethodUpTo(Class<?> startClass, Class<?> exclusiveParent, String methodName, Class<?>... parameterTypes) {
        for (Method method : startClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName) && Arrays.equals(method.getParameterTypes(), parameterTypes)) {
                return method;
            }
        }
        Class<?> parentClass = startClass.getSuperclass();
        if (parentClass != null && !(parentClass.equals(exclusiveParent))) {
            return findDeclaredMethodUpTo(parentClass, exclusiveParent, methodName, parameterTypes);
        }
        return null;
    }

    /**
     * Retrieves all declared fields of a class up to (but not including) a specified parent class.
     *
     * @param startClass the class to start searching from
     * @param exclusiveParent the parent class to stop searching at
     * @return a list of declared fields
     */
    public static List<Field> getDeclaredFieldsUpTo(Class<?> startClass, Class<?> exclusiveParent) {
        List<Field> currentClassFields = new ArrayList<>(Arrays.asList(startClass.getDeclaredFields()));
        Class<?> parentClass = startClass.getSuperclass();
        if (parentClass != null && !(parentClass.equals(exclusiveParent))) {
            List<Field> parentClassFields = getDeclaredFieldsUpTo(parentClass, exclusiveParent);
            currentClassFields.addAll(parentClassFields);
        }
        return currentClassFields;
    }

    /**
     * Finds a declared field in a class up to (but not including) a specified parent class.
     *
     * @param startClass the class to start searching from
     * @param exclusiveParent the parent class to stop searching at
     * @param fieldName the name of the field to find
     * @return the found field, or null if not found
     */
    public static Field findDeclaredFieldUpTo(Class<?> startClass, Class<?> exclusiveParent, String fieldName) {
        for (Field field : startClass.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> parentClass = startClass.getSuperclass();
        if (parentClass != null && !(parentClass.equals(exclusiveParent))) {
            return findDeclaredFieldUpTo(parentClass, exclusiveParent, fieldName);
        }
        return null;
    }
}
