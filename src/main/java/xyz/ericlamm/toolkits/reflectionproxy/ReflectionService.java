package xyz.ericlamm.toolkits.reflectionproxy;

/**
 * A service interface for creating reflection proxies.
 */
public interface ReflectionService {

    /**
     * Creates a proxy instance for the specified class.
     *
     * @param <T> the type of the class
     * @param clazz the class to create a proxy for
     * @return a proxy instance of the specified class
     * @throws Exception if an error occurs while creating the proxy
     */
    <T> T createProxy(Class<T> clazz) throws Exception;

    /**
     * Creates a proxy instance for the specified class and instance.
     *
     * @param <T> the type of the class
     * @param clazz the class to create a proxy for
     * @param instance the instance to create a proxy for
     * @return a proxy instance of the specified class
     * @throws Exception if an error occurs while creating the proxy
     */
    <T> T createProxyForInstance(Class<T> clazz, Object instance) throws Exception;
}
