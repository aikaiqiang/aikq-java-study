package com.space.aikq.customfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 *  自定义文件工具
 * @author aikq
 * @date 2019年02月25日 15:36
 */
public class CustomFileUtils {

	private static final String SUFFIX = ".magic";

	public static boolean checkFileSuffix(String suffix){
		return suffix.toLowerCase().equals(SUFFIX) ? true : false;
	}

	/**
	 * 生成自定义文件
	 * @param fileList 文件列表
	 * @param key AES密钥
	 * @param rootPath 文件操作根目录
	 * @return
	 */
	public static File generateFile(List<String> fileList, String key, String rootPath){
		// 1. 生成zip压缩文件
		File zipFile = ZipUtils.compressorZip(fileList, rootPath);
		// 2. AES加密zip文件
		File customFile = AESUtils.encryptFile(key, zipFile,rootPath);
		return customFile;
	}

	/**
	 *
	 * @param customFile magic后缀文件
	 * @param key AES密钥
	 * @param rootPath 件操作根目录
	 * @return
	 */
	public static List<File> decryptCustomFile(File customFile, String key, String rootPath){
		// 1. AES解密magic文件
		File zipFile = AESUtils.decryptFile(key, customFile, rootPath);
		// 2. 解压zip文件
		List<File> fileList = new ArrayList<>();
		try {
			fileList = ZipUtils.unZipFile(zipFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}

}

