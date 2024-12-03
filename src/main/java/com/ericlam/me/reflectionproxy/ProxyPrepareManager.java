package com.ericlam.me.reflectionproxy;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ericlam.me.reflectionproxy.annotations.Declared;
import com.ericlam.me.reflectionproxy.annotations.Field;
import com.ericlam.me.reflectionproxy.annotations.ForClass;
import com.ericlam.me.reflectionproxy.data.FieldInvoker;
import com.ericlam.me.reflectionproxy.data.MethodInvoker;
import com.ericlam.me.reflectionproxy.data.PreparedProxy;

public final class ProxyPrepareManager implements ProxyPrepareService {

	private final Map<Class<?>, PreparedProxy> prepared = new ConcurrentHashMap<>();

	@Override
	public PreparedProxy prepareFor(Class<?> clazz) throws Exception {
		if (!prepared.containsKey(clazz)) {
			prepareProxy(clazz);
		}
		return prepared.get(clazz);
	}

	private void prepareProxy(Class<?> proxy) throws Exception {
		if (!proxy.isAnnotationPresent(ForClass.class)) {
			throw new IllegalStateException("Class " + proxy.getName() + " is not annotated with @ForClass");
		}
		var forClass = proxy.getAnnotation(ForClass.class);
		var realClazz = Class.forName(forClass.value());
		var methodMap = new ConcurrentHashMap<Method, MethodInvoker>();
		var fieldMap = new ConcurrentHashMap<Method, FieldInvoker>();

		for (var method : ReflectionUtils.getDeclaredMethodsUpTo(proxy, Object.class)) {
			if (method.isAnnotationPresent(Field.class)) {
				fieldMap.put(method, prepareField(realClazz, method));
			} else {
				methodMap.put(method, prepareMethod(realClazz, method));
			}
		}

		prepared.put(proxy, new PreparedProxy(realClazz, methodMap, fieldMap));
	}

	private MethodInvoker prepareMethod(Class<?> realClazz, Method method) throws Exception {
		Method realMethod;
		if (method.isAnnotationPresent(Declared.class)) {
			var upTo = method.getAnnotation(Declared.class).upTo();
			realMethod = ReflectionUtils.findDeclaredMethodUpTo(realClazz, upTo, method.getName(), method.getParameterTypes());
			if (realMethod != null) {
				realMethod.setAccessible(true);
			}
		} else {
			realMethod = realClazz.getMethod(method.getName(), method.getParameterTypes());
		}
		if (realMethod == null) {
			throw new NoSuchMethodException("Method " + method.getName() + " not found in class " + realClazz.getName());
		}
		return new MethodInvoker(realMethod, method.getReturnType());
	}

	private FieldInvoker prepareField(Class<?> realClazz, Method method) throws Exception {
		java.lang.reflect.Field realField;
		FieldInvoker.Type realFieldType;
		String fieldName;

		if (method.getName().startsWith("get")) {
			realFieldType = FieldInvoker.Type.GETTER;
			fieldName = method.getName().substring(3);
		} else if (method.getName().startsWith("is")) {
			realFieldType = FieldInvoker.Type.GETTER;
			fieldName = method.getName().substring(2);
		} else if (method.getName().startsWith("set")) {
			realFieldType = FieldInvoker.Type.SETTER;
			fieldName = method.getName().substring(3);
		} else {
			throw new IllegalStateException("Field method must start with get, is or set");
		}

		var field = method.getAnnotation(Field.class);
		if (!field.value().isBlank()) {
			fieldName = field.value();
		} else {
			// make first letter lower case
			fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
		}

		if (method.isAnnotationPresent(Declared.class)) {
			var upTo = method.getAnnotation(Declared.class).upTo();
			realField = ReflectionUtils.findDeclaredFieldUpTo(realClazz, upTo, fieldName);
			if (realField != null) {
				realField.setAccessible(true);
			}
		} else {
			realField = realClazz.getDeclaredField(method.getName());
		}
		if (realField == null) {
			throw new NoSuchFieldException("Field " + method.getName() + " not found in class " + realClazz.getName());
		}
		return new FieldInvoker(realField, realFieldType, method.getReturnType());
	}
}
