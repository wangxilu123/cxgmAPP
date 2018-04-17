package com.cxgmerp.dao;

import org.springframework.stereotype.Component;

import com.cxgmerp.domain.Product;

@Component
public class ProductDao extends BaseDaoImpl<Product,Long>{

	@Override
	public String getNameSpace() {
		return "sql.Product";
	}
   
}