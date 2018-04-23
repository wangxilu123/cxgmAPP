package com.cxgm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cxgm.common.OSSClientUtil;

@Service
public class UEditorService {

	@Autowired
	private OSSClientUtil ossClient;
	
	public String uploadImages(MultipartFile file) throws Exception{
	    if (file == null || file.getSize() <= 0) {
	      throw new Exception("图片不能为空");
	    }
	    String name = ossClient.uploadImg2Oss(file);
	    String imgUrl = ossClient.getImgUrl(name);
	    return imgUrl;
	  }
}
