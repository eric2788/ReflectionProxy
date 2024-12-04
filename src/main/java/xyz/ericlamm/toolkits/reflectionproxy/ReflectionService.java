package xyz.ericlamm.toolkits.reflectionproxy;

public interface ReflectionService {

	<T> T createProxy(Class<T> clazz) throws Exception;

	<T> T createProxyForInstance(Class<T> clazz, Object instance) throws Exception;


}
