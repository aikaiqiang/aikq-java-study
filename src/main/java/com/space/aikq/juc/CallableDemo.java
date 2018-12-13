package com.space.aikq.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *  E
 * @author aikq
 * @date 2018年12月13日 11:21
 */
public class CallableDemo {

	public static void main(String[] args) {

		MyThread myThread = new MyThread();
		FutureTask<Map<String, Object>> result = new FutureTask<>(myThread);
		new Thread(result).start();

		// 获取线程执行结果
		try {
			Map<String, Object> map = result.get();
			System.out.println("线程【" + map.get("threadName") + "】执行结果:" + map.get("sum"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}


class MyThread implements Callable<Map<String, Object>>{

	@Override
	public Map<String, Object> call() throws Exception {
		int sum = 0;
		for (int i = 0; i < 1000; i++){
			sum += i;
		}

		Map<String, Object> map = new HashMap<>();
		map.put("threadName", Thread.currentThread().getName());
		map.put("sum", sum);
		return map;
	}
}