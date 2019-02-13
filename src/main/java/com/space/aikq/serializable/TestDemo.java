package com.space.aikq.serializable;

import java.io.*;

/**
 *  E
 * @author aikq
 * @date 2019年02月13日 10:35
 */
public class TestDemo {

	private static final String FilePath = "F:/file/serialable.txt";

	/**
	 * 序列化
	 */
	public static void serialize(){
		SerializableObject serializableObject = new SerializableObject();
		try {
			File file = new File(FilePath);
			if (!file.exists()){
				file.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(FilePath);
			ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
			oos.writeObject(serializableObject);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 反序列化
	 */
	public static void deserialize(){
		Object object = null;
		try {
			FileInputStream fis = new FileInputStream(FilePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			object = ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(object);
	}

	public static void main(String[] args) {
		System.out.println("----------序列化开始----------");
		serialize();
		System.out.println("----------序列化结束----------");
		System.out.println("----------反序列化开始----------");
		deserialize();
		System.out.println("----------反序列化结束----------");
	}
}
