package com.space.aikq.design.decorator;

/**
 * @description
 * @author aikq
 * @date 2018年11月26日 13:41
 */
public class WaterComponent implements DrinkComponent {
	@Override
	public void operation() {
		System.out.print("water drink");
	}
}
