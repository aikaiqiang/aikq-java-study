package com.space.aikq.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author aikq
 * @date 2018年10月24日 15:58
 */
public class WorkerDemo {

	public static void main(String[] args) {

		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(5));
		ArrayBlockingQueue<Record> arrayBlockingQueue= new ArrayBlockingQueue<Record>(20);

		Producer producer = new Producer(arrayBlockingQueue);
		producer.start();

		new Consumer(arrayBlockingQueue).start();
		new Consumer(arrayBlockingQueue).start();
		new Consumer(arrayBlockingQueue).start();
		new Consumer(arrayBlockingQueue).start();
		new Consumer(arrayBlockingQueue).start();
	}

}

class Record {
	private String number;
	private  String title;

	public Record(String number, String title) {
		this.number = number;
		this.title = title;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Record{" + "number='" + number + '\'' + ", title='" + title + '\'' + '}';
	}
}

class Producer extends Thread{
	private int i = 0;
	private ArrayBlockingQueue<Record> arrayBlockingQueue;
	public Producer(ArrayBlockingQueue<Record> arrayBlockingQueue){
		this.arrayBlockingQueue = arrayBlockingQueue;
	}

	@Override
	public void run(){
		try {
			while (i < 1000) {
				arrayBlockingQueue.put(new Record("number-" + i, "test-" + i));
				if (++i % 100 == 0){
					//每生产100个，休息10s
					System.out.println(Thread.currentThread().getName() + "导入100条记录，休息10s");
					Thread.sleep(10000);
				}
			}
		}catch (InterruptedException e){
			System.out.println("produce queue InterruptedException");
		}
	}
}


class Consumer extends Thread{
	private ArrayBlockingQueue<Record> arrayBlockingQueue;
	public Consumer(ArrayBlockingQueue<Record> arrayBlockingQueue){
		this.arrayBlockingQueue = arrayBlockingQueue;
	}

	@Override
	public void run(){
		try {
			while (true) {
				Record record = arrayBlockingQueue.poll(5, TimeUnit.SECONDS);
				//如果queue为null，那么5秒之后再去队列中取数据
				if (record != null) {
					System.out.println(Thread.currentThread().getName() + "--consume --" + record.toString());
				}
			}
		}catch (InterruptedException e){
			System.out.println("produce queue InterruptedException");
		}
	}
}


