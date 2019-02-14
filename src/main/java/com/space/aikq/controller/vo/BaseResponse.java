package com.space.aikq.controller.vo;

import lombok.Data;

/**
 *  E
 * @author aikq
 * @date 2019年02月14日 15:37
 */
@Data
public class BaseResponse<T> {

	T data;

	public BaseResponse(T data) {
		this.data = data;
	}

	public BaseResponse() {
	}
}
