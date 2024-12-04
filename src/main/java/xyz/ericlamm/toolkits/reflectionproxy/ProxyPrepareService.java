package xyz.ericlamm.toolkits.reflectionproxy;

import xyz.ericlamm.toolkits.reflectionproxy.data.PreparedProxy;

public interface ProxyPrepareService {

	PreparedProxy prepareFor(Class<?> clazz) throws Exception;

}
