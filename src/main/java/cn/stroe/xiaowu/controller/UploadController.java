package cn.stroe.xiaowu.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.stroe.xiaowu.utils.ResponseResult;

@RestController
@RequestMapping("/upload")
public class UploadController {
	public ResponseResult<Void> uploadZip(HttpServletRequest request,HttpServletResponse response){
		ResponseResult<Void> rr = new ResponseResult<Void>();
		boolean result = true;
		String message = "";
		try {
			// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			// 检查form中是否有enctype="multipart/form-data"
			if(multipartResolver.isMultipart(request)) {
				// 将request变成多部分request
				MultipartHttpServletRequest multiRequest = multipartResolver.resolveMultipart(request);
				// 获取multiRequest 中所有的文件名
				Iterator iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 一次遍历所有文件
					MultipartFile file = multiRequest.getFile(iter.next().toString());
					if (file != null) {
					//上传包名
					String packageName = file.getOriginalFilename();
					//如果不是zip压缩文件
					if(!packageName.matches(".*\\.zip")) {
						message = "请上传zip压缩文件";
					}
					//服务器存放文件的目录
					String filePath = "F:/test";
					File targetFile = new File(filePath);
					if(!targetFile.exists()) {
						targetFile.mkdirs();
					}
					FileOutputStream out = new FileOutputStream(filePath+"/"+packageName);
					out.write(file.getBytes());
					out.flush();
					out.close();
					
					}
				}
			}
			rr.setStatus(1);
			rr.setMessage("OK");
		} catch (MultipartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rr;
	}
}
