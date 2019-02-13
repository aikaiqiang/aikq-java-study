package com.space.aikq.serializable;

import java.io.Serializable;

/**
 *  实现Serializable接口的对象
 * @author aikq
 * @date 2019年02月13日 10:31
 */
public class SerializableObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object writeReplace(){
		return new Integer(1);
	}

	private Object readResolve(){
		return new Double(2);
	}
}