package com.space.aikq.juc;

import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author aikq
 * @date 2018年12月13日 15:28
 */
public class LockAndConditionTest {

	public static void main(String[] args) {
		BufferDemo bufferDemo = new BufferDemo(5);
		ProducerDemo producerDemo = new ProducerDemo(bufferDemo);
		ConsumerDemo consumerDemo = new ConsumerDemo(bufferDemo);
		for (int i = 1; i<=5;i++){
			new Thread(producerDemo,"producer-" + i).start();
		}

		for (int i = 1; i<=3;i++){
			new Thread(consumerDemo,"consumer-" + i).start();
		}

	}
}

class BufferDemo{
	private final Lock lock;

	private final Condition notFull;

	private final Condition notEmpty;

	private int maxsize;

	private List<Date> storage;

	private AtomicInteger count = new AtomicInteger(0);

	public BufferDemo(int maxsize) {
		this.maxsize = maxsize;
		this.storage = new LinkedList<>();
		//使用锁lock，并且创建两个condition，相当于两个阻塞队列
		this.lock = new ReentrantLock();
		this.notFull = lock.newCondition();
		this.notEmpty = lock.newCondition();
	}

	public void put(){
		lock.lock();
		try {
			// 队列已满
			while (storage.size() == maxsize){
				System.out.println("#####队列已满，线程" + Thread.currentThread().getName() + "wait。。。");
				// 阻塞生产线程
				notFull.await();
			}
			Date date = new Date();
			storage.add(date);
			System.out.println("#####线程" +Thread.currentThread().getName()+"【put】，size of storage = " + storage.size());
			System.out.println("-------------------》生产：" + DateUtil.format(date, "yyyyMMdd hh:mm:ss"));
			count.incrementAndGet();
			if (count.intValue() % 10 == 0){
				System.out.println("#######################################生产线已生产产品数据量：count=" + count.intValue());
			}
			Thread.sleep(1000);
			// 唤醒消费线程
			notEmpty.signal();
		}catch (InterruptedException  e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public void take(){
		lock.lock();
		try{
			// 队列空
			while (storage.size() == 0){
				System.out.println("#####队列空，线程" + Thread.currentThread().getName() + "wait。。。");
				// 阻塞消费线程
				notEmpty.await();
			}
			Date date = ((LinkedList<Date>)storage).poll();
			System.out.println("#####线程" +Thread.currentThread().getName()+"【take】，size of storage = " + storage.size());
			System.out.println("-------------------》消费：" + DateUtil.format(date, "yyyyMMdd hh:mm:ss"));
			// 唤醒生产线程
			notFull.signal();
		}catch (InterruptedException e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}

class ProducerDemo implements Runnable{
	private BufferDemo bufferDemo;

	public ProducerDemo(BufferDemo bufferDemo) {
		this.bufferDemo = bufferDemo;
	}

	@Override
	public void run() {
		while (true){
			bufferDemo.put();
		}
	}
}

class ConsumerDemo implements Runnable{
	private BufferDemo bufferDemo;

	public ConsumerDemo(BufferDemo bufferDemo) {
		this.bufferDemo = bufferDemo;
	}

	@Override
	public void run() {
		while (true){
			bufferDemo.take();
		}
	}
}