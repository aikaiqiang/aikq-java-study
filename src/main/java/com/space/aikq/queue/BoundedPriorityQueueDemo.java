package com.space.aikq.queue;

import cn.hutool.core.collection.BoundedPriorityQueue;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @description 有界优先队列
 * @author aikq
 * @date 2018年10月24日 14:03
 */
public class BoundedPriorityQueueDemo {

	public static void main(String[] args) {
		BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(5);
		queue = new BoundedPriorityQueue<>(5, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});

		int[] array = new int[]{5, 7, 8, 2, 3, 8};
		for (int i : array) {
			queue.offer(i);
		}

		ArrayList<Integer> list = queue.toList();

		System.out.println(list);
	}
}
