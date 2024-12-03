package com.ericlam.me.reflectionproxy.data;

import java.lang.reflect.Method;

public record MethodInvoker(
		Method method,
		Class<?> returnType
) {
}
