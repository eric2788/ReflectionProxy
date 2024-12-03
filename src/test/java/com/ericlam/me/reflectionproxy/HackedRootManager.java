package com.ericlam.me.reflectionproxy;

import com.ericlam.me.reflectionproxy.annotations.Declared;
import com.ericlam.me.reflectionproxy.annotations.ForClass;

@ForClass("com.ericlam.me.reflectionproxy.RootManager")
public interface HackedRootManager {

	@Declared
	HackedSecretManager getSecretManager();

}
