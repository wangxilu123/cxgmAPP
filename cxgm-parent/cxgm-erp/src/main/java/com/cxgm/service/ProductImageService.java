package com.cxgm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cxgm.common.OSSClientUtil;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.domain.ProductImage;

@Service
public class ProductImageService {

	@Autowired
	ProductImageMapper productImageDao; 
	@Autowired
	private OSSClientUtil ossClient;
	
	public Long insertImages(MultipartFile file) throws Exception{
	    if (file == null || file.getSize() <= 0) {
	      throw new Exception("图片不能为空");
	    }
	    String name = ossClient.uploadImg2Oss(file);
	    String imgUrl = ossClient.getImgUrl(name);
	    ProductImage productImage = new ProductImage();
	    productImage.setDel(false);
	    productImage.setName(name);
	    productImage.setUrl(imgUrl);
	    productImageDao.insert(productImage);
	    return productImage.getId();
	  }
}
