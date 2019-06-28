package com.space.aikq.performance.case3;

/**
 *  环形缓冲区 - 翻转标记
 *  Ring Buffer Using Flip Marker（使用翻转标记的环形缓冲区）
 * @author aikaiqiang
 * @date 2019年06月20日 13:56
 */
public class RingBufferFlipMarker {
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
	 * 读取位置
	 */
	private int readPos = 0;
	/**
	 * the flip marker
	 */
	private boolean  flipped = false;

	public RingBufferFlipMarker(int capacity) {
		this.capacity = capacity;
		this.elements= new Object[capacity];
	}

	/**
	 * 重置
	 */
	public void reset(){
		this.writePos = 0;
		this.readPos = 0;
		this.flipped = false;
	}

	public int available(){
		if(!flipped){
			return writePos - readPos;
		}
		return  capacity - readPos + writePos;
	}

	public int remainingCapacity(){
		if(!flipped){
			return capacity - writePos;
		}

		return readPos - writePos;
	}

	public boolean put(Object element){
		if(!flipped){
			if(writePos >= capacity){
				writePos = 0;
				flipped = true;

				if(writePos < readPos){
					elements[writePos] = element;
					writePos++;
					return true;
				}else {
					return false;
				}
			}
		}
		return false;
	}


}
