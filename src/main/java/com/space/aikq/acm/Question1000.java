package com.space.aikq.acm;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * @description
 * @author aikq
 * @date 2018年10月11日 10:56
 */
public class Question1000 {

	public static void main(String[] args) {

		System.out.println("请输入2个数字中间空格隔开：");
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			BigInteger a = scan.nextBigInteger();
			BigInteger b = scan.nextBigInteger();
			System.out.println(a.add(b));
			System.out.println(a.add(b));
		}

	}
}
