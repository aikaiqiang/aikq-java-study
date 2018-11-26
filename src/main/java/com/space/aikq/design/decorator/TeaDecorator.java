package com.space.aikq.design.decorator;

/**
 * @description
 * @author aikq
 * @date 2018年11月26日 13:48
 */
public class TeaDecorator extends DrinkDecorator {

	public TeaDecorator(DrinkComponent component) {
		super(component);
	}

	@Override
	public void operation() {
		component.operation();
		System.out.print(", with tea");
	}
}
