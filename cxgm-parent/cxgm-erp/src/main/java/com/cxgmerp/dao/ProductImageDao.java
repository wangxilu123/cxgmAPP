package com.cxgmerp.dao;

import org.springframework.stereotype.Component;

import com.cxgmerp.domain.ProductImage;

@Component
public class ProductImageDao extends BaseDaoImpl<ProductImage,Long>{

	@Override
	public String getNameSpace() {
		return "sql.ProductImage";
	}
   
}