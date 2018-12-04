package com.space.aikq.concurrent;

import cn.hutool.core.date.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 *  多任务组合
 * @author aikq
 * @date 2018年12月03日 11:14
 */
public class CompletableFutureDemo {

	public void test() throws ExecutionException, InterruptedException {
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		new Thread(() -> {
			System.out.println("task doing...");
			try{
				Thread.sleep(3000);
				int i = 1/0;
			}catch (InterruptedException e){
				completableFuture.completeExceptionally(e);
			}

			completableFuture.complete("task is ok");

		}).start();

		// 获取线程结果
		String result = completableFuture.get();
		System.out.println(result);

	}

	public void test2(){

		long start = System.currentTimeMillis();
		//
		List<String> list = new ArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Integer> taskIdList = Arrays.asList(2,1,3,4,5,6,7,8,9,10);

		CompletableFuture[] cfs = taskIdList.stream().map(taskId ->
			CompletableFuture.supplyAsync(()->{
				try {
					if (taskId == 1) {
						//任务1耗时3秒
						Thread.sleep(3000);
					} else if (taskId == 5) {
						//任务5耗时5秒
						Thread.sleep(5000);
					} else {
						//其它任务耗时1秒
						Thread.sleep(1000);
					}
					System.out.println("task线程：" + Thread.currentThread().getName()
							+ "任务i=" + taskId + ",完成！时间=" + DateUtil.format(new Date(),"yyyyMMdd hhmmss"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return taskId;
			}, executorService).thenApply(h -> taskId + "")
					.whenComplete((s, e) -> {
						System.out.println("任务" + s + "完成！结果：result=" + s + ", 异常 e=" + e + ",时间=" + DateUtil.format(new Date(),"yyyyMMdd hhmmss"));
						list.add(s);
					})
		).toArray(CompletableFuture[]::new);

		CompletableFuture.allOf(cfs).join();
		System.out.println("结果 = " + list.toString() + ", 耗时=" + (System.currentTimeMillis() - start) / 100 + "s");
		// 关闭线程池
		System.out.println("关闭线程池----");
		executorService.shutdown();
	}


	public static void main(String[] args) {
		CompletableFutureDemo demo = new CompletableFutureDemo();
//		try {
//			demo.test();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		demo.test2();
	}
}
