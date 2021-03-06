package com.space.aikq.design.proxy.jdk;

import org.springframework.stereotype.Service;

/**
 *  实现类-代理目标类
 * @author aikaiqiang
 * @date 2019年05月31日 11:33
 */
@Service("targetInterface")
public class TargetObject implements TargetInterface {
	@Override
	public void sayHello(String name) {
		System.out.println(name + ": Hi, everyone !");
	}

	@Override
	public void talking(String name) {
		System.out.println(name + "：good good study, day day up");
	}
}
