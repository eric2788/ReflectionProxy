package com.ericlam.me.reflectionproxy;

public final class RootManager {

	private final SecretManager secretManager = new SecretManager();

	private SecretManager getSecretManager() {
		return secretManager;
	}

}
