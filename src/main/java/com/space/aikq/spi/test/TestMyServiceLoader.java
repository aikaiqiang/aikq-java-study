package com.space.aikq.spi.test;

import com.space.aikq.spi.service.MyServiceLoader;

import java.util.ServiceLoader;

/**
 *
 * @author aikq
 * @date 2018年12月06日 11:25
 */
public class TestMyServiceLoader {

	public static void main(String[] argus){
		ServiceLoader<MyServiceLoader> serviceLoader = ServiceLoader.load(MyServiceLoader.class);
		for (MyServiceLoader myServiceLoader : serviceLoader){
			System.out.println(myServiceLoader.getName() + myServiceLoader.sayHello());
		}
	}
}
