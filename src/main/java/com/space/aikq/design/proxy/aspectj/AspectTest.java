package com.space.aikq.design.proxy.aspectj;

import com.space.aikq.design.proxy.jdk.TargetInterface;
import com.space.aikq.design.proxy.jdk.TargetObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author aikaiqiang
 * @date 2019年05月31日 17:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AspectTest {

	@Autowired
	private TargetInterface targetInterface;

	@Test
	public void test() throws Exception {
		targetInterface.sayHello("小明");
		targetInterface.talking("小丽");
	}
}
