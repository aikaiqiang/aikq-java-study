package com.space.aikq.design.decorator;

/**
 * @description
 * @author aikq
 * @date 2018年11月26日 13:43
 */
public abstract class DrinkDecorator implements DrinkComponent {

	protected DrinkComponent component;

	public DrinkDecorator(DrinkComponent component) {
		this.component = component;
	}
}
