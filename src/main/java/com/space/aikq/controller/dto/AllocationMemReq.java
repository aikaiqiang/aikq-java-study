package com.space.aikq.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *  E
 * @author aikq
 * @date 2019年02月14日 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationMemReq implements Serializable {

	private int mem;

	private int num;
}
