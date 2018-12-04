package com.space.aikq.design.decorator;

/**
 * @description 装饰者模式
 * @author aikq
 * @date 2018年11月26日 13:45
 */
public class SugarDecorator extends DrinkDecorator {

	public SugarDecorator(DrinkComponent component) {
		super(component);
	}

	@Override
	public void operation() {
		component.operation();
		System.out.print(", with sugar");
	}
}
