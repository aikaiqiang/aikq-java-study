package com.space.aikq.customfile;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.*;

/**
 *  Zip文件压缩解压
 * @author aikq
 * @date 2019年02月25日 13:42
 */
public class ZipUtils {


	/**
	 * 将多个文件打包成一个zip压缩包，magic.zip
	 * @param filePathList 文件路径列表
	 * @param rootPath 项目配置根目录
	 * @return
	 */
	public static File compressorZip(List<String> filePathList, String rootPath){
		// 3.获取当前时间戳作为加密后的文件
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String currentTime = localDateTime.format(formatter);
		String root = rootPath + File.separator + currentTime;
		File zipFile = new File(root + ".zip");
		try {
			if(!zipFile.getParentFile().exists()) {
				zipFile.getParentFile().mkdirs();
			}
			if(!zipFile.exists()){
				zipFile.createNewFile();
			}
			FileOutputStream fis = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fis, new CRC32());
			ZipOutputStream finalOut = new ZipOutputStream(cos);
			filePathList.forEach(filePath ->{
				compress(filePath, finalOut, "magic");
			});
			finalOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("生成压缩zip文件：" + zipFile.getName() + "; 路径：" + zipFile.getAbsolutePath());
		return zipFile;

	}


	public static void compress(String filePath, ZipOutputStream out, String rootPath){
		File file = new File(filePath);
		if (!file.exists()){
			System.out.println("文件【" + filePath + "】不存在");
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(rootPath + File.separator + file.getName());
			out.putNextEntry(entry);
			int count;
			byte[] cache = new byte[1024 * 4];
			while ((count = bis.read(cache)) != -1) {
				out.write(cache, 0, count);
			}
			bis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压magic.zip压缩包
	 * @param zipFile
	 * @return
	 * @throws Exception
	 */
	public static List<File> unZipFile(File zipFile) throws Exception{
		String rootPath = zipFile.getAbsolutePath().substring(0, zipFile.getAbsolutePath().indexOf("."));
		List<File> fileList = new ArrayList<>();
		ZipFile zip = getZipFile(zipFile);
		for(Enumeration entries = zip.entries(); entries.hasMoreElements();){
			ZipEntry entry = (ZipEntry)entries.nextElement();
			String zipEntryName = entry.getName();
			// 文件夹
			if (entry.isDirectory()){
				File fol = new File(rootPath + File.separator + zipEntryName);
				fol.mkdirs();
			} else {
				InputStream in = zip.getInputStream(entry);
				// 解压后的文件名（绝对路径）
				String outPath = (rootPath + File.separator + zipEntryName).replaceAll("\\*", "/");
				File childFile = new File(outPath);
				fileList.add(childFile);
				if(!childFile.getParentFile().exists()) {
					childFile.getParentFile().mkdirs();
				}
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(childFile));
				byte[] buf = new byte[1024 * 4];
				int len = 0;
				while((len = in.read(buf))!= -1){
					out.write(buf,0,len);
				}
				in.close();
				out.close();
			}
		}
		// 解压完关闭zip 才可以删除压缩包
		zip.close();
		return fileList;
	}

	/**
	 * zip解压 wins 和 linux 中文名的编码问题
	 * @param zipFile
	 * @return
	 * @throws Exception
	 */
	private static ZipFile getZipFile(File zipFile) throws Exception {
		ZipFile zip = new ZipFile(zipFile, Charset.forName("UTF-8"));
		Enumeration entries = zip.entries();
		while (entries.hasMoreElements()) {
			try {
				entries.nextElement();
				zip.close();
				zip = new ZipFile(zipFile, Charset.forName("UTF-8"));
				return zip;
			} catch (Exception e) {
				zip = new ZipFile(zipFile, Charset.forName("GBK"));
				return zip;
			}
		}
		return zip;
	}

	public static void main(String[] args) {
		// 压缩
		String rootPath = "F:/zipEntry";
		List<String> filePaths = new ArrayList<>();
		filePaths.add("F:/zipEntry/20190225114728.magic");
		filePaths.add("F:/zipEntry/aikq-demo/magic-cube-platform-data.sql");
		filePaths.add("F:/zipEntry/aikq-demo/readme.txt");
		filePaths.add("F:/zipEntry/aikq-demo/测试数数据表.xlsx");
		File zipFile = compressorZip(filePaths, rootPath);


		// 解压
		File newZipFile = new File("F:/zipEntry/20190225153317.zip");
		try {
			List<File> fileList = unZipFile(newZipFile);
			// 打印信息
			fileList.forEach(file -> {
				System.out.println("文件名：" + file.getName() +"; 文件路径：" + file.getAbsolutePath());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}
