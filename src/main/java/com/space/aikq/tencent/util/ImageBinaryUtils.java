package com.space.aikq.tencent.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 *  E
 * @author aikq
 * @date 2019年02月18日 10:56
 */
public class ImageBinaryUtils {

	private static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	private static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

	public static String getImageBinary(String filePath) {
		File f = new File(filePath);
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes = baos.toByteArray();

			return encoder.encodeBuffer(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	static void base64StringToImage(String base64String, String filePath) {
		try {
			byte[] bytes1 = decoder.decodeBuffer(base64String);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			BufferedImage bi1 = ImageIO.read(bais);
			// 可以是jpg,png,gif格式
			UUID uuid = UUID.randomUUID();
			File w2 = new File(filePath +File.separator + uuid + ".jpg");
			// 不管输出什么格式图片，此处不需改动
			ImageIO.write(bi1, "jpg", w2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String imageBinary = ImageBinaryUtils.getImageBinary("F:\\image\\mingpian.jpg");
		System.out.println(imageBinary);
	}
}
