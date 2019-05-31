package com.space.aikq.design.proxy.cglib;

import com.space.aikq.design.proxy.jdk.TargetObject;
import com.space.aikq.design.proxy.jdk.TargetInterface;

/**
 *
 * @author aikaiqiang
 * @date 2019年05月31日 12:26
 */
public class CglibProxyTest {

	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy();
		// 通过动态生成子类的方式创建代理类
		// 代理类
		TargetObject target1 = (TargetObject) proxy.getProxy(TargetObject.class);
		// 代理接口
		TargetInterface target2 = (TargetInterface) proxy.getProxy(TargetObject.class);
		target1.sayHello("小明");
		target1.talking("小丽");
		System.out.println("==============================分割线=================================");
		target2.sayHello("小明");
		target2.talking("小丽");
	}
}
