# ReflectionProxy

> [!IMPORTANT]
> This project is proof of concept and just for fun. 
> Use it at your own risk if you want to use it in production.

ReflectionProxy is a simple library that allows you to use reflection in a more convenient way.

## Hook

- [Maven Central](https://central.sonatype.com/artifact/xyz.ericlamm.toolkits/reflection-proxy)
- [Github Packages](https://github.com/eric2788/ReflectionProxy/packages/2334237)

## Example

Assume you would like to change a private field of an object from a library:

```java
package me.example.secret;

public final class SecretManager {
	private static final SecretManager instance = new SecretManager();

	public static SecretManager getInstance() {
		return instance;
	}

	private String secret = "I am a secret"; // edit this one

	public void printSecret() {
		System.out.println(secret);
	}
}
```

Create a fake inteface for the class:

```java
@ForClass("me.example.secret.SecretManager")
public interface SecretManagerProxy {
	
	@Declared // declare that this is a declared field
    	@Field // declare that this is a field
    	String getSecret();
	
	@Declared // declare that this is a declared field
	@Field // declare that this is a field
    	void setSecret(String secret);

}
```

Register the interface and use it

```java
public static void main(String[] args) {
	var proxy = ReflectionProxy
			.builder()
			.prepare(SecretManagerProxy.class)
			.build();
	
	var ins = SecretManagerProxy.getInstance();
    	var secretManager = proxy.createProxyForInstance(SecretManagerProxy.class, ins);
	System.out.println("before");
	ins.printSecret();
	secretManager.setSecret("this is a new secret");
    	System.out.println("after");
	ins.printSecret();
}
```

or checkout the examples from [test package](/src/test/java/xyz/ericlamm/toolkits/reflectionproxy/TestReflectionProxy.java)
