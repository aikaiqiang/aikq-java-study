package com.space.aikq.reflect;

/**
 *  E
 * @author aikq
 * @date 2019年02月13日 16:36
 */
public class Main {

	public static void main(String[] args) {

		// 父类单例
		String fatherName = RegisterSingleton.class.getName();
		RegisterSingleton singleton1 = RegisterSingleton.getInstance(fatherName);
		RegisterSingleton singleton2 = RegisterSingleton.getInstance(fatherName);
		System.out.println("singleton1 = " + singleton1.hashCode());
		System.out.println("singleton2 = " + singleton2.hashCode());

		// 父类单例
		RegisterSingletonChild childSingleton3 = RegisterSingletonChild.getInstance();
		RegisterSingletonChild childSingleton4 = RegisterSingletonChild.getInstance();
		System.out.println("childSingleton3 = " + childSingleton3.hashCode());
		System.out.println("childSingleton4 = " + childSingleton4.hashCode());
	}
}
