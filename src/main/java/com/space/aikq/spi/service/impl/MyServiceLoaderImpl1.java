package com.space.aikq.spi.service.impl;

import com.google.auto.service.AutoService;
import com.space.aikq.spi.service.MyServiceLoader;

/**
 *  E
 * @author aikq
 * @date 2018年12月06日 11:22
 */
//@AutoService(MyServiceLoader.class)
public class MyServiceLoaderImpl1 implements MyServiceLoader {
	@Override
	public String sayHello() {
		return "hello1";
	}

	@Override
	public String getName() {
		return "name1";
	}
}
