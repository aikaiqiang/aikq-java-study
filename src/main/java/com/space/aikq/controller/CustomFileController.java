package com.space.aikq.controller;

import com.space.aikq.controller.dto.CustomFileDTO;
import com.space.aikq.controller.vo.CustomFileVO;
import com.space.aikq.customfile.CustomFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  自定义数据文件
 * @author aikq
 * @date 2019年02月25日 15:45
 */
@Slf4j
@RestController
@RequestMapping("/customFile")
public class CustomFileController {

	@Value("${magic.file.export-path}")
	private String rootPath;

	@Value("${magic.file.path}")
	private String filePath;

	@Value("${magic.file.aes-key}")
	private String key;

	@PostMapping("/export")
	public void exportCustomFile(@RequestBody CustomFileDTO dto, HttpServletResponse response){
		CustomFileVO vo = new CustomFileVO();
		List<String> fileList = dto.getFileList();
		File customFile = CustomFileUtils.generateFile(fileList, key, rootPath);
		String customFileName = customFile.getName();
		// 重定向到excel文件地址，输出文件
		try {
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="+ new String((customFileName).getBytes(), "iso-8859-1"));

			ServletOutputStream out = response.getOutputStream();
			InputStream ins = new FileInputStream(customFile);
			DataInputStream in = new DataInputStream(ins);

			//输出文件
			int bytes = 0;
			byte[] bufferOut = new byte[1024 * 4];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			out.flush();
			out.close();
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@PostMapping("/export/info")
	public CustomFileVO exportCustomFileInfo(@RequestBody CustomFileDTO dto, HttpServletResponse response){
		CustomFileVO vo = new CustomFileVO();
		List<String> fileList = dto.getFileList();
		File customFile = CustomFileUtils.generateFile(fileList, key, rootPath);
		vo.setFileName(customFile.getName());
		vo.setFilePath(customFile.getAbsolutePath());
		return vo;
	}

	@PostMapping("/import")
	public List<CustomFileVO> importCustomFile(@RequestParam("file") MultipartFile file){
		List<CustomFileVO> resultList = new ArrayList<>();
		String customFileName = file.getOriginalFilename();
		try {
			boolean flag = CustomFileUtils.checkFileSuffix(customFileName.substring(customFileName.lastIndexOf(".")));
			if (!flag){
				System.out.println("文件后缀名不正确，请检查是否未后缀.magic");
				return null;
			}
			String tempFilePath = filePath + File.separator + customFileName;
			File tempFile = new File(tempFilePath);
			boolean existFlag = tempFile.exists();
			if (!existFlag){
				tempFile.createNewFile();
			}
			file.transferTo(new File(tempFilePath));

			List<File> fileList = CustomFileUtils.decryptCustomFile(tempFile, key, rootPath);

			// 删除中间文件
			if (existFlag){
				tempFile.delete();
			}

			fileList.forEach(file1 -> {
				CustomFileVO vo = new CustomFileVO();
				vo.setFileName(file1.getName());
				vo.setFilePath(file1.getAbsolutePath());
				resultList.add(vo);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultList;
	}
}
