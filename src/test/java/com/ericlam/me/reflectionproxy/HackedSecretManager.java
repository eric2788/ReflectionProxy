package com.ericlam.me.reflectionproxy;

import com.ericlam.me.reflectionproxy.annotations.Declared;
import com.ericlam.me.reflectionproxy.annotations.Field;
import com.ericlam.me.reflectionproxy.annotations.ForClass;

@ForClass("com.ericlam.me.reflectionproxy.SecretManager")
public interface HackedSecretManager {

	@Declared
	@Field
	String getSecretName();

	@Declared
	@Field
	void setSecretName(String name);

	void printSecret();

	@Declared
	void shuffSecretName();

}
