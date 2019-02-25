package com.space.aikq.customfile;

import com.space.aikq.customfile.exception.GenerateKeyException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  AES 加密解密
 * @author aikq
 * @date 2019年02月25日 10:30
 */
public class AESUtils {

	private static final String keyRule = "HANCLOUD-MAGIC-123456789";

	/**
	 * 根据密钥规则获取AES密钥
	 * @param keyRule 密钥规则
	 * @return
	 */
	public static SecretKey getSecretKey(String keyRule) throws GenerateKeyException{
		try {
			// 1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen = null;
			keygen = KeyGenerator.getInstance("AES");
			// 2.根据ecnodeRules规则初始化密钥生成器
			// 生成一个128位的随机源,根据传入的字节数组
			keygen.init(128, new SecureRandom(keyRule.getBytes()));
			// 3.产生原始对称密钥
			SecretKey originalKey = keygen.generateKey();
			// 4.获得原始对称密钥的字节数组
			byte [] raw = originalKey.getEncoded();
			//5.根据字节数组生成AES密钥
			SecretKey secretKey = new SecretKeySpec(raw, "AES");
			return  secretKey;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new GenerateKeyException("获取AES密钥失败");
		}
	}

	public static File encryptFile(String keyRule, File sourceFile, String filePath){
		File encryptFile = null;
		try {
			SecretKey secretKey = getSecretKey(keyRule);
			// 1.根据指定算法AES自成密码器
			Cipher cipher = null;
			cipher = Cipher.getInstance("AES");
			// 2.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			// 3.获取当前时间戳作为加密后的文件
			LocalDateTime localDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String currentTime = localDateTime.format(formatter);
			encryptFile = new File(filePath +File.separator + currentTime + ".magic");
			if (!encryptFile.exists()){
				encryptFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(encryptFile);

			// 4.读取源文件进行AES加密
			FileInputStream fins = new FileInputStream(sourceFile);
			CipherInputStream cis = new CipherInputStream(fins, cipher);

			byte[] cache = new byte[1024];
			int nRead = 0;
			while ((nRead = cis.read(cache)) != -1) {
				fos.write(cache, 0, nRead);
				fos.flush();
			}
			fos.close();
			cis.close();
			fins.close();
		} catch (GenerateKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return encryptFile;
	}


	public static File decryptFile(String keyRule, File encryptFile, String filePath){
		File decryptFile = null;
		try {
			SecretKey secretKey = getSecretKey(keyRule);
			// 1.根据指定算法AES自成密码器
			Cipher cipher = null;
			cipher = Cipher.getInstance("AES");
			// 2.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			// 3.获取当前时间戳作为加密后的文件
			LocalDateTime localDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
			String currentTime = localDateTime.format(formatter);
			decryptFile = new File(filePath +File.separator + currentTime + ".zip");
			if (!decryptFile.exists()){
				decryptFile.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(decryptFile);

			// 4.读取加密文件进行AES解密
			FileInputStream fins = new FileInputStream(encryptFile);
			CipherInputStream cis = new CipherInputStream(fins, cipher);

			byte[] cache = new byte[1024];
			int nRead = 0;
			while ((nRead = cis.read(cache)) != -1) {
				fos.write(cache, 0, nRead);
				fos.flush();
			}
			fos.close();
			cis.close();
			fins.close();

		}  catch (GenerateKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return decryptFile;
	}

	public static void main(String[] args) {
		String filePath = "F:/zipEntry/";
		String sourceFilePath = "F:/zipEntry/aikq-demo.zip";
		File sourceFile = new File(sourceFilePath);
		System.out.println("源文件：" + sourceFile.getName() + "; 路径：" + sourceFile.getAbsolutePath());
		File encryptFile = encryptFile(keyRule,sourceFile,filePath);
		System.out.println("加密后的文件：" + encryptFile.getName() + "; 路径：" + encryptFile.getAbsolutePath());
		File decryptFile = decryptFile(keyRule, encryptFile,filePath);
		System.out.println("解密后的文件：" + decryptFile.getName() + "; 路径：" + decryptFile.getAbsolutePath());
	}

}
