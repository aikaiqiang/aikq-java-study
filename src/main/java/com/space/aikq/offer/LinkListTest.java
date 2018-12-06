package com.space.aikq.offer;

import java.util.ArrayList;
import java.util.Stack;

/**
 *  E
 * @author aikq
 * @date 2018年12月06日 15:22
 */
public class LinkListTest {

	public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
		Stack<Integer> stack = new Stack<>();
		while(listNode != null){
			stack.push(listNode.val);
			listNode = listNode.next;
		}

		ArrayList<Integer> list = new ArrayList<>();
		while (!stack.isEmpty()){
			list.add(stack.pop());
		}
		return list;
	}


	 static class ListNode {
		int val;
		ListNode next = null;
		ListNode(int val) {
			this.val = val;
		}
	}
}
