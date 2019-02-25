package com.space.aikq.controller;

import com.space.aikq.controller.dto.AllocationMemReq;
import com.space.aikq.controller.vo.BaseResponse;
import com.space.aikq.tencent.util.Sign;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.space.aikq.tencent.util.HttpConnection.doPostByFormNew;

/**
 *
 * @author aikq
 * @date 2019年02月14日 15:32
 */
@RestController
public class IndexController {

	private static final int _1M = 1024 * 1024;

	private final static String POST_URL = "https://recognition.image.myqcloud.com/ocr/businesscard";

	private final static long APPID = 1258152736;
	private final static String SecretId = "AKIDSAOSt9Q9vVVVogyvJVZO4WnrrFOxhSYQ";
	private final static String SecretKey = "gnywkgOuLszwYConcRBj36yMdeWNWTAY";

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

	@PostMapping("/ocr")
	public String ocr(@RequestParam("file") MultipartFile file){
		BaseResponse<String> response = new BaseResponse<>();
		String ret = "";
		try {
			Map<String, String> textMap = new HashMap<>();
			//可以设置多个参数
			textMap.put("appid", APPID + "");
			//设置file的Url路径
			Map<String, File> fileMap = new HashMap<>();
			File tempFile = new File("F:/image" + File.separator + UUID.randomUUID().toString() + ".jpg");
			file.transferTo(tempFile);
			fileMap.put("image", tempFile);
			String sign = Sign.appSign(APPID, SecretId, SecretKey, "", 259200L);
			System.out.printf("sign=%s\r\n", sign);
			ret = doPostByFormNew(POST_URL, textMap, fileMap, sign);
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
