package com.ericlam.me.reflectionproxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class SecretManager {

	private String secretName;

	public SecretManager(String secretName) {
		this.secretName = secretName;
	}

	public SecretManager() {
		this("this is a default secret");
	}

	public void printSecret() {
		System.out.println("My secret name is " + secretName);
	}

	private void shuffSecretName() {
		List<Integer> arr = new ArrayList<>(secretName.chars().boxed().toList());
		Collections.shuffle(arr);
		secretName = arr.stream().map(i -> (char) i.intValue()).map(String::valueOf).collect(Collectors.joining());
	}
}
