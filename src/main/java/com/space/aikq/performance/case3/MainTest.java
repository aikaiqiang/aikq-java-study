package com.space.aikq.performance.case3;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年06月20日 14:26
 */
public class MainTest {

	public static void main(String[] args) {
		RingBufferFillCount rbfc = new RingBufferFillCount(10);

		rbfc.put("java");
		System.out.println("剩余空位 = " + rbfc.remainingCapacity());
	}
}
