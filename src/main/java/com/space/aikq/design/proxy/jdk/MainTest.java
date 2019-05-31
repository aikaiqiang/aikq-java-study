package com.space.aikq.design.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 *  测试
 * @author aikaiqiang
 * @date 2019年05月31日 11:39
 */
public class MainTest {

	public static void main(String[] args) {

		// 被代理的目标业务类
		TargetInterface target = new TargetObject();
		CustomInvocationHandler handler = new CustomInvocationHandler(target);
		// 创建代理实例：
		// JDK动态代理有一个很大的限制，就是它要求目标类必须实现了对应方法的接口，它只能为接口创建代理实例
		TargetInterface proxy = (TargetInterface) Proxy.newProxyInstance(
				// 目标类的类加载器
				target.getClass().getClassLoader(),
				// 目标类的接口
				target.getClass().getInterfaces(),
				// 横切类
				handler);
		proxy.sayHello("小明");
		proxy.talking("小丽");
	}
}
