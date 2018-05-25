package com.cxgm.dao;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.Product;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.ShopCategory;


public interface ProductMapper {

	public List<ProductTransfer> findListAllWithCategory(Map<String,Object> map);
	
	public List<ProductTransfer> findHotCategory(Map<String,Object> map);
	
	int insert(Product product);
	
	int update(Product product);
	
	int delete(Long id);
	
	ProductTransfer findById(Long id);
	Product findProductById(Long id);
	
	Product findProductByGoodCode(String goodCode);
	
	List<ShopCategory> findShopCategory(Integer shopId);
	
	List<ShopCategory> findShopCategoryTwo(Map<String,Object> map);
	
	List<ShopCategory> findShopCategoryThird(Map<String,Object> map);
	
	List<Product> findProducts(Map<String,Object> map);
	
	List<ProductTransfer> findPushProduct(Map<String,Object> map);
}