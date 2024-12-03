package com.ericlam.me.reflectionproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import com.ericlam.me.reflectionproxy.annotations.Field;
import com.ericlam.me.reflectionproxy.annotations.ForClass;
import com.ericlam.me.reflectionproxy.data.FieldInvoker;
import com.ericlam.me.reflectionproxy.data.MethodInvoker;
import com.ericlam.me.reflectionproxy.data.ProxyObject;

/* (from CHATGPT)
	Highest performance cost: getDeclaredField, getDeclaredMethod, getMethod, getField
	Moderate performance cost: getType, getReturnType
	Lower performance cost: get, set, invoke
	Lowest performance cost: isInterface, isAnnotationPresent
 */
public final class ReflectionInvocationHandler implements InvocationHandler {

	private final ReflectionService reflectionService;

	private final Class<?> mainClass;
	private final Object ins;

	private final Map<Method, MethodInvoker> methodMap;
	private final Map<Method, FieldInvoker> fieldMap;

	public ReflectionInvocationHandler(ProxyObject proxyObject, ReflectionService reflectionService) {
		this.mainClass = proxyObject.mainClass();
		this.ins = proxyObject.ins();
		this.methodMap = proxyObject.methodMap();
		this.fieldMap = proxyObject.fieldMap();

		this.reflectionService = reflectionService;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if (methodMap.containsKey(method)) {
			return invokeMethod(methodMap.get(method), args);
		} else if (fieldMap.containsKey(method)) {
			return invokeField(fieldMap.get(method), args);
		} else {
			if (method.isAnnotationPresent(Field.class)) {
				throw new NoSuchFieldException("Field " + method.getName() + " not found in class " + mainClass.getName());
			} else {
				throw new NoSuchMethodException("Method " + method.getName() + " not found in class " + mainClass.getName());
			}
		}
	}

	private Object wrap(Object ins, Class<?> returnType) throws Throwable {
		if (returnType == void.class || returnType == Void.class) {
			return null;
		} else if (!returnType.isInterface() || !returnType.isAnnotationPresent(ForClass.class)) {
			return ins;
		}
		return reflectionService.createProxyForInstance(returnType, ins);
	}

	private Object invokeField(FieldInvoker field, Object[] args) throws Throwable {
		var f = field.field();
		if (field.type() == FieldInvoker.Type.GETTER) {
			return wrap(f.get(this.ins), field.returnType());
		} else if (args.length == 1) {
			f.set(this.ins, args[0]);
			return null;
		} else {
			throw new IllegalArgumentException("Setter method must have exact one argument.");
		}
	}

	private Object invokeMethod(MethodInvoker method, Object[] args) throws Throwable {
		var m = method.method();
		return wrap(m.invoke(this.ins, args), method.returnType());
	}
}
