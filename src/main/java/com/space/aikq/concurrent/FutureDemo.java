package com.space.aikq.concurrent;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * xxx
 * @author Administrator
 * @date 2018年12月03日 11:12
 */
public class FutureDemo {

	public static void main(String[] args) {
		FutureDemo futureDemo = new FutureDemo();
		try {
			futureDemo.test();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void test() throws ExecutionException, InterruptedException{
		long startTime = System.currentTimeMillis();
		// 开启线程池
		ThreadFactory nameThreadFactory = new ThreadFactoryBuilder().setNameFormat("future-demo-%d").build();

		ExecutorService pool = new ThreadPoolExecutor(10, 15, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100),
				nameThreadFactory, new ThreadPoolExecutor.AbortPolicy());
//		ExecutorService pool = Executors.newFixedThreadPool(10);
		try {
			// 线程结果集
			List<Integer> list = Lists.newArrayList();
			List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

			// 提交任务
			for (int i = 1; i <= 10; i++) {
				futureList.add(pool.submit(new CallableTask(i)));
			}

			// 结果归集，用迭代器遍历futureList,高速轮询（模拟实现了并发），任务完成就移除
			long getResultStart = System.currentTimeMillis();
			System.out.println("结果归集开始时间=" + DateUtil.format(new Date(), "yyyyMMdd hh:mm:ss"));
			while (futureList.size() > 0) {
				Iterator<Future<Integer>> iterator = futureList.iterator();
				while (iterator.hasNext()) {
					Future<Integer> future = iterator.next();
					if (future.isDone() && !future.isCancelled()) {
						// 获取线程执行结果
						Integer res = future.get();
						System.out.println("任务taskId=" + res + ",执行完成从队列删除, 时间=" + DateUtil
								.format(new Date(), "yyyyMMdd hh:mm:ss"));
						iterator.remove();
						System.out.println("任务数组：futureList = " + futureList.toString());
						list.add(res);
					} else {
						Thread.sleep(1);
					}
				}
			}

			System.out.println("最后结果集list=" + list.toString());
			System.out.println(
					"总耗时=" + (System.currentTimeMillis() - startTime) + "ms, 取结果归集耗时=" + (System.currentTimeMillis() - getResultStart) + "ms");
		}catch (Exception e){
			throw e;
		}finally {
			pool.shutdown();
		}
	}

	static class CallableTask implements Callable<Integer>{
		private Integer i;

		public CallableTask(Integer i) {
			super();
			this.i = i;
		}

		@Override
		public Integer call() throws Exception {
			if(i == 1){
				Thread.sleep(3000);
			}else if(i == 5){
				Thread.sleep(5000);
			}else{
				Thread.sleep(1000);
			}
			System.out.println("task线程[" + Thread.currentThread().getName() + "], taskId = " + i + ", 时刻=" + DateUtil.format(new Date(), "yyyyMMdd hh:mm:ss"));
			return i;
		}
	}
}
