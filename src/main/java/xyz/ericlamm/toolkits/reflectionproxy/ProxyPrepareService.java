package xyz.ericlamm.toolkits.reflectionproxy;

import xyz.ericlamm.toolkits.reflectionproxy.data.PreparedProxy;

/**
 * A service interface for preparing proxies for classes.
 */
public interface ProxyPrepareService {

    /**
     * Prepares a proxy for the specified class.
     *
     * @param clazz the class to prepare a proxy for
     * @return a {@link PreparedProxy} instance for the specified class
     * @throws Exception if an error occurs while preparing the proxy
     */
    PreparedProxy prepareFor(Class<?> clazz) throws Exception;

}
