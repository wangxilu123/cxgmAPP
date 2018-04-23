package com.cxgm.dao;

import com.cxgm.domain.ProductImage;

public interface ProductImageMapper {
   
	int insert(ProductImage productImage);
	
	int delete(Long id);
	
}