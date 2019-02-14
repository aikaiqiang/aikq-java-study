package com.space.aikq.jmx;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.time.LocalTime;
import java.util.Date;

/**
 *  E
 * @author aikq
 * @date 2019年02月14日 11:44
 */
public class UserAgent implements NotificationListener {

	private MBeanServer mBeanServer;

	private MBeanServer mBeanServer1;

	public UserAgent() {
		try {
			mBeanServer = ManagementFactory.getPlatformMBeanServer();
			mBeanServer1 = MBeanServerFactory.createMBeanServer("hello");
			ObjectName name = new ObjectName("UserAgent:type=User1");

			User user = new User();
			user.setName("test");
			user.setId(1);
			user.setBirthDate(new Date());
			user.setTime(LocalTime.now());
			Test test = new Test();
			test.setName("aikq");
			test.setAge(26);
			user.setTest(test);

			mBeanServer.registerMBean(user, name);
			mBeanServer1.registerMBean(user, new ObjectName("Hello:type=user"));
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			e.printStackTrace();
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void handleNotification(Notification notification, Object handback) {

	}
}
