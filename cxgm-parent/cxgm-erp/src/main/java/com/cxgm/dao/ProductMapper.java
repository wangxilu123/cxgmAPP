package com.cxgm.dao;

import java.util.List;

import com.cxgm.domain.Product;
import com.cxgm.domain.ProductTransfer;


public interface ProductMapper {

	public List<ProductTransfer> findListAllWithCategory();
	
	int insert(Product product);
	
	int update(Product product);
	
	int delete(Long id);
}