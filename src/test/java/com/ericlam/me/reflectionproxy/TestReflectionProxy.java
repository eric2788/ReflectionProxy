package com.ericlam.me.reflectionproxy;

import org.junit.jupiter.api.Test;


public class TestReflectionProxy {

	private final ProxyPrepareManager prepareManager = ReflectionProxy.newPrepareManager();

	@Test
	void testSecretManager() throws Exception {
		var reflectionProxy = ReflectionProxy
				.builder(prepareManager)
				.prepare(HackedSecretManager.class)
				.build();

		var manager1 = new SecretManager("this is a secret");

		var proxy1 = reflectionProxy.createProxyForInstance(HackedSecretManager.class, manager1);
		var proxy2 = reflectionProxy.createProxy(HackedSecretManager.class);

		System.out.println("proxy1: " + proxy1.getSecretName());
		System.out.println("proxy2: " + proxy2.getSecretName());

		proxy1.printSecret();
		proxy2.printSecret();

		proxy1.setSecretName("this is a new secret");
		proxy2.setSecretName("this is a new default secret");

		proxy1.printSecret();
		proxy2.printSecret();

		proxy1.shuffSecretName();
		proxy2.shuffSecretName();

		System.out.println("proxy1: " + proxy1.getSecretName());
		System.out.println("proxy2: " + proxy2.getSecretName());

	}

	@Test
	void testRootManager() throws Exception {
		var reflectionProxy = ReflectionProxy
				.builder(prepareManager)
				.prepare(HackedRootManager.class)
				.prepare(HackedSecretManager.class)
				.build();

		var rootProxy = reflectionProxy.createProxy(HackedRootManager.class);
		HackedSecretManager secretManager = rootProxy.getSecretManager();
		secretManager.printSecret();
	}

}
