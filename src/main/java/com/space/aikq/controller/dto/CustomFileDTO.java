package com.space.aikq.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author aikq
 * @date 2019年02月25日 15:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomFileDTO implements Serializable {

	private static final long serialVersionUID = -4662077205076418298L;
	List<String> fileList;
}
