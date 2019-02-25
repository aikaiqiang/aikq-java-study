package com.space.aikq.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author aikq
 * @date 2019年02月25日 15:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomFileVO implements Serializable {

	private static final long serialVersionUID = 1878600928401839305L;
	/**
	 * 文件名
	 */
	String fileName;
	/**
	 * 文件路径
	 */
	String filePath;
}
