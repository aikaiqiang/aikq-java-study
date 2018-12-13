package com.space.aikq.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Atomic 原子性
 * @author aikq
 * @date 2018年12月13日 10:24
 */
public class AtomicDemo {

	public static void main(String[] args) throws InterruptedException {
//		MyThreadDemo thread = new MyThreadDemo();
//		MyThreadDemo2 thread = new MyThreadDemo2();
		MyThreadDemo3 thread = new MyThreadDemo3();
		for(int i = 0; i < 100; i++){
			new Thread(thread).start();
		}
	}
}

/**
 * 存在原子性问题
 */
class MyThreadDemo implements Runnable{

	private int count = 0;

	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "---------->" + getCount());
	}

	public int getCount() {
		return count++;
	}
}

/**
 * Atomic 可以解决多线程原子性问题
 */
class MyThreadDemo2 implements Runnable{

	private AtomicInteger count = new AtomicInteger(0);

	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "---------->" + getCount());
	}

	public int getCount() {
		return count.incrementAndGet();
	}
}


/**
 * 可见性volatile不能解决
 */
class MyThreadDemo3 implements Runnable{

	private volatile int count = 0;

	@Override
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "---------->" + getCount());
	}

	public int getCount() {
		return count++;
	}
}
