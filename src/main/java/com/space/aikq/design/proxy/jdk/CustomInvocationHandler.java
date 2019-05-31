package com.space.aikq.design.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *  自定义横切类
 * @author aikaiqiang
 * @date 2019年05月31日 11:36
 */
public class CustomInvocationHandler implements InvocationHandler {

	private Object target;
	CustomInvocationHandler(Object target){
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// before
		System.out.println("———————————proxy start ————————————");
		System.out.println("Next one please ！");
		//
		Object obj = method.invoke(target, args);
		// after
		System.out.println("Thank you！");
		System.out.println("———————————proxy  end—————————————");
		return obj;
	}
}
