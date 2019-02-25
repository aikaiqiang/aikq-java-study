package com.space.aikq.tencent;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.space.aikq.tencent.util.ImageBinaryUtils;
import com.space.aikq.tencent.util.Sign;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.space.aikq.tencent.util.HttpConnection.doPostByForm;

/**
 *  E
 * @author aikq
 * @date 2019年02月18日 10:19
 */
public class OCRDemo {

	private final static String POST_URL = "https://recognition.image.myqcloud.com/ocr/businesscard";

	private final static long APPID = 1258152736;
	private final static String SecretId = "AKIDSAOSt9Q9vVVVogyvJVZO4WnrrFOxhSYQ";
	private final static String SecretKey = "gnywkgOuLszwYConcRBj36yMdeWNWTAY";


	public static String getSign(String bucketName, long expireTime)throws Exception{
		return Sign.appSign(APPID, SecretId, SecretKey, bucketName, expireTime);
	}

	public static String buildParam(long appid, String bucket, String image){
		JSONObject jsonObject = JSONUtil.createObj();
		jsonObject.put("appid", appid+"");
		jsonObject.put("bucket", bucket);
		jsonObject.put("image", image);
		return jsonObject.toString();
	}

	public static Map buildParamMap(long appid, String bucket, String image){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("appid", appid+"");
		paramMap.put("bucket", bucket);
		paramMap.put("image", image);
		return paramMap;
	}

	public static MultiValueMap<String, String> popHeaders(long appid, String bucket, String image) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("appid", appid+"");
		map.add("bucket", bucket);
		map.add("image", image);
		//.....
		return map;
	}

	public static void main(String[] args) {
		String authorization  = null;
		try {
			authorization  = getSign("", 259200L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("sign=" + authorization );

		String fileUrl = "http://yoututest-1251966477.cossh.myqcloud.com/mingpian.jpg";
		Map<String, String> textMap = new HashMap<>();
		//可以设置多个参数
		textMap.put("appid", APPID + "");
		//设置file的Url路径
		Map<String, String> fileMap = new HashMap<>();
		fileMap.put("image", fileUrl);
		String ret = doPostByForm(POST_URL,textMap,fileMap,authorization);
		System.out.println(ret);

	}
}
