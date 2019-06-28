package com.space.aikq.performance.case3;

/**
 *  环形缓冲区 - 填充统计
 *  Ring Buffer Using Fill Count（使用填充计数的环形缓冲区）
 * @author aikaiqiang
 * @date 2019年06月20日 13:54
 */
public class RingBufferFillCount {
	public Object[] elements = null;

	/**
	 * 初始化数组大小
	 */
	private int capacity = 0;
	/**
	 * 下一个可写入位置索引
	 */
	private int writePos = 0;
	/**
	 * 统计可以访问个数
	 */
	private int available = 0;

	public RingBufferFillCount(int capacity) {
		this.capacity = capacity;
		this.elements = new Object[capacity];
	}

	/**
	 * 重置
	 */
	public void reset(){
		this.writePos = 0;
		this.available = 0;
	}

	public int capacity(){
		return this.capacity;
	}

	public int remainingCapacity(){
		return this.capacity - this.available;
	}

	public boolean put(Object element){
		if(available < capacity){
			if(writePos >= capacity){
				writePos = 0;
			}
			elements[writePos] = element;
			writePos++;
			available++;
			return true;
		}

		return false;
	}

	public Object take(){
		if(available == 0){
			return null;
		}

		int nextSlot = writePos - available;
		if(nextSlot < 0){
			nextSlot += capacity;
		}
		Object nextObj = elements[nextSlot];
		available--;
		return  nextObj;
	}
}
