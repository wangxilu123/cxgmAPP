package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.ProductCategory;


public interface ProductCategoryMapper {

   
	public List<ProductCategory> findByCategory(Map<String,Object> map);
	
	public ProductCategory findByName(String name);
	
    public List<ProductCategory> findByPid(Long pid);
	
    public List<ProductCategory> findListAll();
	
	public ProductCategory findById(Long id);
	
	int insert(ProductCategory parameter);
	
	int update(ProductCategory parameter);
	
	int delete(Long id);
}