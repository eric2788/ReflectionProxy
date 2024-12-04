package xyz.ericlamm.toolkits.reflectionproxy;

import xyz.ericlamm.toolkits.reflectionproxy.annotations.Declared;
import xyz.ericlamm.toolkits.reflectionproxy.annotations.ForClass;

@ForClass("com.ericlam.me.reflectionproxy.RootManager")
public interface HackedRootManager {

	@Declared
	HackedSecretManager getSecretManager();

}
