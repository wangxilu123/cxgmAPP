package com.cxgmerp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgmerp.dao.ProductCategoryDao;
import com.cxgmerp.domain.ProductCategory;

@Service
public class ProductCategoryService {

	@Autowired
	ProductCategoryDao productCategoryDao;
	
	public List<ProductCategory> findByGrade(Integer grade){
		Map<String,Object> map = new HashMap<>();
		map.put("grade", grade);
		List<ProductCategory> productCategoryList = productCategoryDao.findByCategory(map);
		return productCategoryList;
	}
	
	public List<ProductCategory> findByGradeAndParentId(Integer grade,Long parentId){
		Map<String,Object> map = new HashMap<>();
		map.put("grade", grade);
		map.put("parentId", parentId);
		List<ProductCategory> productCategoryList = productCategoryDao.findByCategory(map);
		return productCategoryList;
	}
	
	public List<ProductCategory> getProductCategory(Integer grade){
		List<ProductCategory> productCategoryTreeList = this.findByGrade(grade);
		for(ProductCategory pc : productCategoryTreeList) {
			List<ProductCategory> pcs = this.findByGradeAndParentId(1, pc.getId());
			pc.setChildCategory(pcs);
		}
		return productCategoryTreeList;
	}
	
}
