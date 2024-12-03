package com.ericlam.me.reflectionproxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ReflectionUtils {

	public static List<Method> getDeclaredMethodsUpTo(Class<?> startClass, Class<?> exclusiveParent) {
		List<Method> currentClassMethods = new ArrayList<>(Arrays.asList(startClass.getDeclaredMethods()));
		Class<?> parentClass = startClass.getSuperclass();
		if (parentClass != null && !(parentClass.equals(exclusiveParent))) {
			List<Method> parentClassFields = getDeclaredMethodsUpTo(parentClass, exclusiveParent);
			currentClassMethods.addAll(parentClassFields);
		}
		return currentClassMethods;
	}

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

	public static List<Field> getDeclaredFieldsUpTo(Class<?> startClass, Class<?> exclusiveParent) {
		List<Field> currentClassFields = new ArrayList<>(Arrays.asList(startClass.getDeclaredFields()));
		Class<?> parentClass = startClass.getSuperclass();
		if (parentClass != null && !(parentClass.equals(exclusiveParent))) {
			List<Field> parentClassFields = getDeclaredFieldsUpTo(parentClass, exclusiveParent);
			currentClassFields.addAll(parentClassFields);
		}
		return currentClassFields;
	}

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
