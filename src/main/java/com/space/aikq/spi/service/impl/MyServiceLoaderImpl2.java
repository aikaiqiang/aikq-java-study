package com.space.aikq.spi.service.impl;

import com.google.auto.service.AutoService;
import com.space.aikq.spi.service.MyServiceLoader;

/**
 *  E
 * @author aikq
 * @date 2018年12月06日 11:22
 */
//@AutoService(MyServiceLoader.class)
public class MyServiceLoaderImpl2 implements MyServiceLoader {
	@Override
	public String sayHello() {
		return "hello2";
	}

	@Override
	public String getName() {
		return "name2";
	}
}
