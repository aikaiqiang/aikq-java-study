package com.space.aikq.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @description 有界阻塞队列
 * @author aikq
 * @date 2018年10月24日 14:18
 */
public class ArrayBlockingQueueDemo {

	public static void main(String[] args) {

		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1000);
	}
}
