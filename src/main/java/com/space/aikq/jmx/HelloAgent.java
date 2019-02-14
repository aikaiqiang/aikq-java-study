package com.space.aikq.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 *  E
 * @author aikq
 * @date 2019年02月14日 14:57
 */
public class HelloAgent {

	private MBeanServer mBeanServer;

	public HelloAgent() throws Exception {

		mBeanServer = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("HelloAgent:type=hello");

		Test test = new Test();
		test.setName("aikaiqiang");
		test.setAge(100);

		HelloImpl hello = new HelloImpl();
		hello.setName("hello");
		hello.setTest(test);

		mBeanServer.registerMBean(hello, name);

		Thread.sleep(Long.MAX_VALUE);
	}
}
