package xyz.ericlamm.toolkits.reflectionproxy;

public final class RootManager {

	private final SecretManager secretManager = new SecretManager();

	private SecretManager getSecretManager() {
		return secretManager;
	}

}
