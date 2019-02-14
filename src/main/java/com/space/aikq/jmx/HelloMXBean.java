package com.space.aikq.jmx;

/**
 *  E
 * @author aikq
 * @date 2019年02月14日 14:55
 */
public interface HelloMXBean {

	public String getName();

	public void setName(String name);

	public void setTest(Test test);

	public Test getTest();
}
