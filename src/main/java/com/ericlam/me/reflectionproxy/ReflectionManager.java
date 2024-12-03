package com.ericlam.me.reflectionproxy;

import java.lang.reflect.Proxy;

import com.ericlam.me.reflectionproxy.data.ProxyObject;

public final class ReflectionManager implements ReflectionService {

	private final ClassLoader classLoader;
	private final ProxyPrepareService prepareService;

	public ReflectionManager(ClassLoader classLoader, ProxyPrepareService prepareService) {
		this.classLoader = classLoader;
		this.prepareService = prepareService;
	}

	@Override
	public <T> T createProxy(Class<T> clazz) throws Exception {
		return this.createProxyForInstance(clazz, null);
	}

	@Override
	public <T> T createProxyForInstance(Class<T> clazz, Object instance) throws Exception {
		var preparedProxy = prepareService.prepareFor(clazz);
		if (instance == null) {
			instance = preparedProxy.clazz().getConstructor().newInstance();
		}
		var proxyObject = new ProxyObject(preparedProxy.clazz(), instance, preparedProxy.methodMap(), preparedProxy.fieldMap());
		return clazz.cast(Proxy.newProxyInstance(
				classLoader,
				new Class<?>[]{ clazz },
				new ReflectionInvocationHandler(proxyObject, this))
		);
	}


}
