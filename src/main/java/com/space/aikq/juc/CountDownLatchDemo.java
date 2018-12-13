package com.space.aikq.juc;

import java.util.concurrent.CountDownLatch;

/**
 *  CountDownLatch闭锁操作
 * @author aikq
 * @date 2018年12月13日 11:09
 */
public class CountDownLatchDemo {

	public static void main(String[] args) {

		final CountDownLatch latch = new CountDownLatch(5);
		LatchThread latchThread = new LatchThread(latch);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 5; i++){
			new Thread(latchThread).start();
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("耗费时间为："+(endTime - startTime));
	}
}

class LatchThread implements Runnable{

	private CountDownLatch countDownLatch;

	public LatchThread(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		synchronized (this) {
			try {
				for (int i = 0; i < 50000; i++) {
					if (i % 5 == 0) {
						System.out.println(i);
					}
				}
			} finally {
				countDownLatch.countDown();
			}
		}
	}
}
