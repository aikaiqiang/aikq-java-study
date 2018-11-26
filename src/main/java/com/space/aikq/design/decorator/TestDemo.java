package com.space.aikq.design.decorator;

/**
 * @description
 * @author aikq
 * @date 2018年11月26日 13:49
 */
public class TestDemo {

	public static void main(String[] args) {

		WaterComponent waterComponent = new WaterComponent();
		waterComponent.operation();
		System.out.println("\n-----------------line----------------------");

		SugarDecorator sugarDecorator = new SugarDecorator(waterComponent);
		sugarDecorator.operation();
		System.out.println("\n-----------------line----------------------");

		TeaDecorator teaDecorator = new TeaDecorator(sugarDecorator);
		teaDecorator.operation();
		System.out.println("\n-----------------line----------------------");

	}
}
