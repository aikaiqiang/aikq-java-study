package com.space.aikq.juc;

/**
 *  E
 * @author aikq
 * @date 2018年12月13日 10:10
 */
public class VolatileDemo {

	public static void main(String[] args) {
		VolatileDemo volatileDemo = new VolatileDemo();
		VolatileDemo.MyThread myThread = volatileDemo.new MyThread();
//		MyThread myThread = new MyThread();
		new Thread(myThread).start();
		while (true) {
//			synchronized (myThread) {
				if (myThread.isFlag()) {
					System.out.println("status of flag is true!");
					break;
				}
//			}
		}
	}

	/**
	 * 自定义线程
	 */
	public class MyThread implements Runnable{

		private volatile boolean flag = false;

		@Override
		public void run() {
			System.out.println("current thread name is 【" + Thread.currentThread().getName() + "】");
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			flag = true;
		}

		public boolean isFlag() {
			return flag;
		}
	}
}


