package xyz.ericlamm.toolkits.reflectionproxy.data;

import java.lang.reflect.Method;

public record MethodInvoker(
		Method method,
		Class<?> returnType
) {
}
