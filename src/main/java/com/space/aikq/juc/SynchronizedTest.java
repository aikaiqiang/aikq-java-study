package com.space.aikq.juc;

import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *  synchronized/wait() 实现生产者消费者模式
 * @author aikq
 * @date 2018年12月13日 14:56
 */
public class SynchronizedTest {

	public static void main(String[] arg){
		Buffer buffer=new Buffer(10);
		Producer producer=new Producer(buffer);
		Consumer consumer=new Consumer(buffer);
		//创建线程执行生产和消费
		for(int i=0;i<3;i++){
			new Thread(producer,"producer-"+i).start();
		}
		for(int i=0;i<3;i++){
			new Thread(consumer,"consumer-"+i).start();
		}
	}

}

/**
 * 生产和消费的对象
 */
class Buffer{

	private int maxsize;
	private List<Date> storage;

	public Buffer(int maxsize) {
		this.maxsize = maxsize;
		this.storage = new LinkedList<>();
	}


	public synchronized void put(){
		try {
			// 队列已满
			while (storage.size() == maxsize){
				System.out.println("#####队列已满，线程" + Thread.currentThread().getName() + "wait。。。");
				wait();
			}
			Date date = new Date();
			storage.add(date);
			System.out.println("#####线程" +Thread.currentThread().getName()+"【put】，size of storage = " + storage.size());
			System.out.println("-------------------》生产：" + DateUtil.format(date, "yyyyMMdd hh:mm:ss"));
			Thread.sleep(1000);
			notifyAll();
		}catch (InterruptedException  e){
			e.printStackTrace();
		}
	}

	public synchronized void take(){
		try {
			// 空队列
			while (storage.size() == 0){
				System.out.println("#####队列为空，线程" + Thread.currentThread().getName() + "wait。。。");
				wait();
			}
			Date date = ((LinkedList<Date>)storage).poll();
			System.out.println("#####线程" +Thread.currentThread().getName()+"【take】，size of storage = " + storage.size());
			System.out.println("-------------------》消费：" + DateUtil.format(date, "yyyyMMdd hh:mm:ss"));
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}


class Producer implements Runnable{
	private Buffer buffer;
	public Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while(true) {
			buffer.put();
		}
	}
}

class Consumer implements Runnable{
	private Buffer buffer;

	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		while(true) {
			buffer.take();
		}
	}
}
