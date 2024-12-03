package com.ericlam.me.reflectionproxy;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public final class ReflectionProxy {

	private ReflectionProxy(){}

	private static final Logger logger = Logger.getLogger(ReflectionProxy.class.getName());

	public static Builder builder() {
		return new Builder(new ProxyPrepareManager());
	}

	public static Builder builder(ProxyPrepareManager prepareManager) {
		return new Builder(prepareManager);
	}

	public static ProxyPrepareManager newPrepareManager(){
		return new ProxyPrepareManager();
	}

	public static class Builder {

		private final ProxyPrepareManager prepareManager;

		private final Set<Class<?>> proxies = new HashSet<>();
		private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		public Builder(ProxyPrepareManager prepareManager) {
			this.prepareManager = prepareManager;
		}

		public Builder setClassLoader(ClassLoader classLoader) {
			this.classLoader = classLoader;
			return this;
		}

		public Builder prepare(Class<?> clazz) {
			proxies.add(clazz);
			return this;
		}

		public ReflectionService build() {
			proxies.forEach(proxy -> {
				try {
					prepareManager.prepareFor(proxy);
				} catch (Exception e) {
					logger.severe("Error while preparing proxy for " + proxy.getName() + ": " + e.getMessage());
					logger.throwing(ReflectionProxy.class.getName(), "build", e);
				}
			});
			return new ReflectionManager(classLoader, prepareManager);
		}


	}
}
