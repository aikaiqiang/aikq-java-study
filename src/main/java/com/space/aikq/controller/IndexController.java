package com.space.aikq.controller;

import com.space.aikq.controller.dto.AllocationMemReq;
import com.space.aikq.controller.vo.BaseResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aikq
 * @date 2019年02月14日 15:32
 */
@RestController
@RequestMapping("/bug")
public class IndexController {

	private static final int _1M = 1024 * 1024;

	@RequestMapping("index")
	public BaseResponse<String> index(){
		BaseResponse<String> response = new BaseResponse<>();
		response.setData("success");
		return response;


	}

	@RequestMapping(value = "/allocationMem", method = RequestMethod.POST)
	public BaseResponse<String> allocationMem(@RequestBody AllocationMemReq allocationMemReq){
		BaseResponse<String> response = new BaseResponse<>();

		for (int i = 0; i < allocationMemReq.getNum(); i++){
			byte[] mem = new byte[allocationMemReq.getMem() * _1M];
		}

		response.setData("success");
		return response;


	}

}
