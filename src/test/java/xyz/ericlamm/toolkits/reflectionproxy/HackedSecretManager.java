package xyz.ericlamm.toolkits.reflectionproxy;

import xyz.ericlamm.toolkits.reflectionproxy.annotations.Declared;
import xyz.ericlamm.toolkits.reflectionproxy.annotations.Field;
import xyz.ericlamm.toolkits.reflectionproxy.annotations.ForClass;

@ForClass("com.ericlam.me.reflectionproxy.SecretManager")
public interface HackedSecretManager {

	@Declared
	@Field
	String getSecretName();

	@Declared
	@Field
	void setSecretName(String name);

	void printSecret();

	@Declared
	void shuffSecretName();

}
