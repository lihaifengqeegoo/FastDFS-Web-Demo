/**
 * 文件名称   : com.autozi.lihf.controller.index.IndexController.java
 * 项目名称   : 中驰车福-乘用车
 * 创建日期   : 2017-7-25
 * 更新日期   :
 * 作       者   : haifeng.li@autozi.com
 *
 * Copyright (C) 2016 中驰车福联合电子商务（北京）有限公司 版权所有.
 */
package com.autozi.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.autozi.config.FastDFSConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadFileWriter;
import com.github.tobato.fastdfs.service.DefaultGenerateStorageClient;

/**
 * <PRE>
 * 
 * 中文描述：
 * 
 * </PRE>
 * @version 1.0.0
 */
@Controller
public class IndexController {
	
	@Autowired
	private DefaultGenerateStorageClient defaultGenerateStorageClient;
	@Autowired
	private FastDFSConfig fastDFSConfig;
	
	@RequestMapping(value={"/index","/"})
	public String index(ModelMap modelMap){
		return "/index/dfs-test";
	}
	
	@RequestMapping("/dfs/file/upload")
	public String dfsFileUpload(@RequestParam(value="file",required=true) MultipartFile file,
			HttpServletRequest request,ModelMap modelMap){
		if(file.isEmpty()){
			return "文件不能为空！";
		}
		StorePath storePath = null;
		try{
			storePath = defaultGenerateStorageClient.uploadFile(fastDFSConfig.getGroupName()
					, file.getInputStream()
					, file.getSize(),fastDFSConfig.getExtName());
		}catch (IOException ioe) {
			return "文件读写错误！";
		}
		modelMap.put("group", storePath.getGroup());
		modelMap.put("path", storePath.getPath());
		modelMap.put("fullpath", storePath.getFullPath());
		return "/index/success";
	}
	
	@RequestMapping("/dfs/file/delete")
	@ResponseBody
	public String deleteFile(String path){
		String msg = "删除成功！";
		try{
			defaultGenerateStorageClient.deleteFile(fastDFSConfig.getGroupName(), path);
		}catch(Exception ex){
			msg = ex.getLocalizedMessage();
		}
		return msg;
	}
	
	@RequestMapping("/dfs/file/download")
	@ResponseBody
	public String downloadFile(String path){
		String storagepath = "d:/从fastdfs下载来的图片.jpg";
		String msg = "下载成功！,文件路径路径为："+storagepath;
		try{
			defaultGenerateStorageClient.downloadFile(fastDFSConfig.getGroupName()
					,path
					, new DownloadFileWriter(storagepath));
		}catch(Exception ex){
			msg = ex.getLocalizedMessage();
		}
		return msg;
	}
	

}
