package com.space.aikq.design.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *  代理创建器CglibProxy
 * @author aikaiqiang
 * @date 2019年05月31日 12:19
 */
public class CglibProxy implements MethodInterceptor {

	Enhancer enhancer = new Enhancer();
	public Object getProxy(Class clazz){
		// 设置需要创建的子类
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		// 通过字节码技术动态创建子类实例
		return enhancer.create();
	}


	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println("———————————proxy start ————————————");
		// before
		System.out.println("Next one please ！");
		Object result = methodProxy.invokeSuper(o, objects);
		// after
		System.out.println("Thank you！");
		System.out.println("———————————proxy  end—————————————");
		return result;
	}
}
