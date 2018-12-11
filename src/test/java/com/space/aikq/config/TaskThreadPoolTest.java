package com.space.aikq.config;

import com.space.aikq.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Future;

/**
 * @description
 * @author aikq
 * @date 2018年10月24日 17:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskThreadPoolTest {

	@Autowired
	private Task task;

	@Test
	public void taskTest() throws Exception {
		Future<String> task1 = task.doTaskFour();
	}

}
