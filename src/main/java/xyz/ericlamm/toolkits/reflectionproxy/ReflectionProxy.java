package xyz.ericlamm.toolkits.reflectionproxy;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * A utility class for creating and managing reflection proxies.
 */
public final class ReflectionProxy {

    private ReflectionProxy() {}

    private static final Logger logger = Logger.getLogger(ReflectionProxy.class.getName());

    /**
     * Creates a new {@link Builder} with a default {@link ProxyPrepareManager}.
     *
     * @return a new {@link Builder} instance
     */
    public static Builder builder() {
        return new Builder(new ProxyPrepareManager());
    }

    /**
     * Creates a new {@link Builder} with the specified {@link ProxyPrepareManager}.
     *
     * @param prepareManager the {@link ProxyPrepareManager} to use
     * @return a new {@link Builder} instance
     */
    public static Builder builder(ProxyPrepareManager prepareManager) {
        return new Builder(prepareManager);
    }

    /**
     * Creates a new {@link ProxyPrepareManager}.
     *
     * @return a new {@link ProxyPrepareManager} instance
     */
    public static ProxyPrepareManager newPrepareManager() {
        return new ProxyPrepareManager();
    }

    /**
     * A builder class for creating {@link ReflectionManager} instances.
     */
    public static class Builder {

        private final ProxyPrepareManager prepareManager;
        private final Set<Class<?>> proxies = new HashSet<>();
        private ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        /**
         * Constructs a new {@link Builder} with the specified {@link ProxyPrepareManager}.
         *
         * @param prepareManager the {@link ProxyPrepareManager} to use
         */
        public Builder(ProxyPrepareManager prepareManager) {
            this.prepareManager = prepareManager;
        }

        /**
         * Sets the {@link ClassLoader} to use for loading proxy classes.
         *
         * @param classLoader the {@link ClassLoader} to use
         * @return this {@link Builder} instance
         */
        public Builder setClassLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        /**
         * Adds a class to be prepared as a proxy.
         *
         * @param clazz the class to prepare
         * @return this {@link Builder} instance
         */
        public Builder prepare(Class<?> clazz) {
            proxies.add(clazz);
            return this;
        }

        /**
         * Builds a new {@link ReflectionManager} with the configured settings.
         *
         * @return a new {@link ReflectionManager} instance
         */
        public ReflectionManager build() {
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
