package com.space.aikq.reflect;

/**
 *  子类单例
 * @author aikq
 * @date 2019年02月13日 16:35
 */
public final class RegisterSingletonChild extends RegisterSingleton {

	private RegisterSingletonChild() {

	}

	public static RegisterSingletonChild getInstance() {
		return (RegisterSingletonChild)RegisterSingleton.getInstance(RegisterSingletonChild.class.getName());
	}

}
