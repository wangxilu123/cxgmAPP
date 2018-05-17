package com.cxgm.service;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.Advertisement;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.ShopCategory;

public interface HomePageService {

	List<ProductTransfer> findListAllWithCategory(Map<String, Object> map);
	
	List<ShopCategory> findShopOneCategory(Integer shopId);

	List<ShopCategory> findShopTwoCategory(Integer shopId,Integer productCategoryId);

	List<ShopCategory> findShopThreeCategory(Integer shopId, Integer productCategoryTwoId);

	List<ProductTransfer> findHotCategory(Map<String, Object> map);

	List<Advertisement> findAdvertisement(Integer shopId);
  
}
