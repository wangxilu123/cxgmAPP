package com.cxgm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cxgm.domain.Product;
import com.cxgm.domain.ProductTransfer;


public interface ProductMapper {

	public List<ProductTransfer> findListAllWithCategory(Map<String,Object> map);
	
	int insert(Product product);
	
	int update(Product product);
	
	int delete(Long id);
	
	List<ProductTransfer> selectByPage(HashMap<String,Object> map);
	
	ProductTransfer findById(Long id);
	Product findProductById(Long id);
}