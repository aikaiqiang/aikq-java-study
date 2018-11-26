package com.space.aikq.concurrent;

import com.google.common.base.Splitter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description
 * @author aikq
 * @date 2018年10月22日 8:55
 */
public class Test2 {

	private static AtomicInteger count = new AtomicInteger(0);

	private static final Splitter COMMA_SPLITTER = Splitter.on(',')
			.trimResults()
			.omitEmptyStrings();

	public static void main(String[] args) {

		//		String str = "test1, , test2, test3";
		//		Iterable<String> strArr = Splitter.on(',')
		//				.trimResults()
		//				.omitEmptyStrings()
		//				.split(str);
		//
		//		System.out.println("数组-1" + strArr);
		//
		//		strArr = COMMA_SPLITTER.split("foo, ,bar, quux,");
		//
		//		System.out.println("数组-2" + strArr);

		try {
			threadDemo();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	public static void threadDemo() throws InterruptedException{
		for (int i = 0; i < 1000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					count.incrementAndGet();
					System.out.println(Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + ": count=" + count.intValue());
				}
			}).start();

			System.out.println(Thread.currentThread().getName() + "-" + Thread.currentThread().getId());
			//			Thread.sleep(5);
		}

	}
}
