package com.ericlam.me.reflectionproxy.data;

import java.lang.reflect.Field;

public record FieldInvoker(
		Field field,
		Type type,
		Class<?> returnType
) {
	public enum Type {
		SETTER, GETTER
	}
}
