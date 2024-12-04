package xyz.ericlamm.toolkits.reflectionproxy.data;

import java.lang.reflect.Method;
import java.util.Map;

public record PreparedProxy(
		Class<?> clazz,
		Map<Method, MethodInvoker> methodMap,
		Map<Method, FieldInvoker> fieldMap
) {
}
