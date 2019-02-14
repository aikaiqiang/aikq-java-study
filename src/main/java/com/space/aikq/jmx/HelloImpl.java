package com.space.aikq.jmx;

/**
 *  E
 * @author aikq
 * @date 2019年02月14日 14:56
 */
public class HelloImpl implements HelloMXBean {

	private String name;
	private Test test;

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setTest(Test test) {
		this.test = test;
	}

	@Override
	public Test getTest() {
		return this.test;
	}
}
