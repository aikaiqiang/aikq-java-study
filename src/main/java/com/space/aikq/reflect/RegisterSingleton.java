package com.space.aikq.reflect;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 *  父类单例
 * @author aikq
 * @date 2019年02月13日 16:34
 */
public class RegisterSingleton {

	protected RegisterSingleton() {

	}

	private static final Map<String, RegisterSingleton> registry = new HashMap<>();
	private static final String name;

	private static volatile Object lock = new Object();

	static {
		RegisterSingleton registerSingleton = new RegisterSingleton();
		name = registerSingleton.getClass().getName();
		registry.put(name, registerSingleton);
	}

	public static RegisterSingleton getInstance(String name) {
		if (name == null) {
			name = RegisterSingleton.name;
		} else if (registry.get(name) == null) {
			try {
				synchronized (lock) {
					Class<?> clazz = Class.forName(name);
					Constructor<?> constructor = clazz.getDeclaredConstructor();
					constructor.setAccessible(true);
					RegisterSingleton singleton = (RegisterSingleton) constructor.newInstance();
					registry.put(name, singleton);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException();
			}
		}
		return registry.get(name);
	}

}
