package com.cxgm.service;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.ShopCategory;

public interface HomePageService {

	List<ProductTransfer> findListAllWithCategory(Map<String, Object> map);
	
	List<ShopCategory> findShopOneCategory(Integer shopId);

	List<ShopCategory> findShopTwoCategory(Integer shopId,Integer productCategoryId);
  
}