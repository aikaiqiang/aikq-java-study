package com.space.aikq.jmx;

/**
 *  E
 * @author aikq
 * @date 2019年02月14日 14:25
 */
public class Main {

	public static void main(String[] args) {
//		UserAgent userAgent = new UserAgent();
		try {
			HelloAgent helloAgent = new HelloAgent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
