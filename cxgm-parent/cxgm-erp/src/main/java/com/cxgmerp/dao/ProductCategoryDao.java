package com.cxgmerp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cxgmerp.domain.ProductCategory;

@Component
public class ProductCategoryDao extends BaseDaoImpl<ProductCategory,Long> {

	@Override
	public String getNameSpace() {
		return "sql.ProductCategory";
	}
   
	public List<ProductCategory> findByCategory(Map<String,Object> map){
		return selectList(this.getNameSpace()+".findByCategory",map);
	}
}