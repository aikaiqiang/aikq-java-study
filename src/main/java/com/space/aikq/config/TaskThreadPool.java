package com.space.aikq.config;

import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description
 * @author aikq
 * @date 2018年10月24日 16:58
 */
@Configuration
public class TaskThreadPool {

	/**
	 * 线程池维护线程的最少数量
	 */
	private int corePoolSize = 10;

	/**
	 * 线程池维护线程的最大数量
	 */
	private int maxPoolSize = 30;

	/**
	 * 缓存队列
	 */
	private int queueCapacity = 8;

	/**
	 * 允许的空闲时间
	 */
	private int keepAlive = 60;

	@Bean
	public Executor myExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix("MyAsync-");
		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
		// 对拒绝task的处理策略
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setKeepAliveSeconds(keepAlive);
		executor.initialize();
		return executor;
	}

	@Async("myExecutor")
	public void dataProducer(){
		System.out.println("线程名称："+Thread.currentThread().getName());
	}

	@Async("myExecutor")
	public void dataConsumer(){
		System.out.println("线程名称："+Thread.currentThread().getName());
	}

}
