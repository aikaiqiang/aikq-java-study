package com.space.aikq.offer;

/**
 *  E
 * @author aikq
 * @date 2018年12月06日 15:04
 */
public class ReplaceSpaceTest {

	public String replaceSpace(StringBuffer str) {
		return str.toString().replace(" ", "%20");
	}

	public static void main(String[] args) {
		ReplaceSpaceTest test = new ReplaceSpaceTest();
		StringBuffer str = new StringBuffer().append("We Are Happy.");
		System.out.println(test.replaceSpace(str));

	}
}
