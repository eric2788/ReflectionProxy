package com.ericlam.me.reflectionproxy;

import com.ericlam.me.reflectionproxy.data.PreparedProxy;

public interface ProxyPrepareService {

	PreparedProxy prepareFor(Class<?> clazz) throws Exception;

}
