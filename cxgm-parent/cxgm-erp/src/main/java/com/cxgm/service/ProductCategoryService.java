package com.cxgm.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.dao.ProductCategoryMapper;
import com.cxgm.domain.ProductCategory;
import com.cxgm.exception.TipException;

@Service
public class ProductCategoryService {

	@Autowired
	ProductCategoryMapper productCategoryDao;
	
	public List<ProductCategory> findByGrade(Integer grade, Integer shopId){
		Map<String,Object> map = new HashMap<>();
		map.put("grade", grade);
		map.put("shopId", shopId);
		List<ProductCategory> productCategoryList = productCategoryDao.findByCategory(map);
		return productCategoryList;
	}
	public ProductCategory findById(Long id) {
		return productCategoryDao.findById(id);
	}
	
	public List<ProductCategory> findByGradeAndParentId(Integer grade,Long parentId, Integer shopId){
		Map<String,Object> map = new HashMap<>();
		map.put("grade", grade);
		map.put("parentId", parentId);
		map.put("shopId", shopId);
		List<ProductCategory> productCategoryList = productCategoryDao.findByCategory(map);
		return productCategoryList;
	}
	
	
	public List<ProductCategory> getProductCategory(Integer grade,Integer shopId){
		List<ProductCategory> productCategoryTreeList = this.findByGrade(grade,shopId);
		for(ProductCategory pc : productCategoryTreeList) {
			List<ProductCategory> childOneCategory = this.findByGradeAndParentId(1, pc.getId(),shopId);
			pc.setChildCategory(childOneCategory);
			for(ProductCategory p: childOneCategory) {
				List<ProductCategory> childTwoCategory = this.findByGradeAndParentId(2, p.getId(),shopId);
				p.setChildCategory(childTwoCategory);
			}
		}
		return productCategoryTreeList;
	}
	
	@Transactional
	public void insert(Long parentId, String name, Integer shopId) {
		ProductCategory productCategory = null;
		if(parentId == 0) {
			productCategory = productCategoryDao.findByName(name);
			if(null != productCategory) {
				throw new TipException("类目名字已经存在");
			}
			productCategory = new ProductCategory();
			productCategory.setName(name);
			productCategory.setGrade(0);
			productCategory.setShopId(shopId);
			productCategoryDao.insert(productCategory);
			
		}else {
			productCategory = productCategoryDao.findById(parentId);
			ProductCategory pcy = new ProductCategory();
			pcy.setName(name);
			pcy.setParentId(parentId);
			pcy.setDeleteFlag(false);
			pcy.setShopId(shopId);
			if(productCategory.getGrade()==0) {
				pcy.setGrade(1);
			}else if(productCategory.getGrade()==1){
				pcy.setGrade(2);
			}
			productCategoryDao.insert(pcy);
		}
		
	}
	
	@Transactional
	public void update(Long id, Long parentId, String name, Integer shopId) {
		ProductCategory productCategory = productCategoryDao.findById(id);
		if(parentId == 0) {
			productCategory.setGrade(0);
			productCategory.setParentId(null);
			productCategory.setShopId(shopId);
		}else {
			ProductCategory pcy = productCategoryDao.findById(parentId);
			productCategory.setName(name);
			productCategory.setParentId(parentId);
			productCategory.setShopId(shopId);
			if(pcy.getGrade()==0) {
				productCategory.setGrade(1);
			}else if(pcy.getGrade()==1){
				productCategory.setGrade(2);
			}
		}
		productCategoryDao.update(productCategory);
		
	}
	
	@Transactional
	public int delete(Long id) {
		int resultDelete = 0;
		productCategoryDao.delete(id);
		resultDelete = 1;
		return resultDelete;
	}
}
