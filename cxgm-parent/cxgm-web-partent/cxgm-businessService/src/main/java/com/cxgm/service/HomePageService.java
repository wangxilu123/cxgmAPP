package com.cxgm.service;

import java.util.List;
import java.util.Map;

import com.cxgm.domain.Advertisement;
import com.cxgm.domain.Motion;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.ShopCategory;

public interface HomePageService {

	List<ProductTransfer> findListAllWithCategory(Map<String, Object> map,Integer userId);
	
	List<ShopCategory> findShopOneCategory(Integer shopId);

	List<ShopCategory> findShopTwoCategory(Integer shopId,Integer productCategoryId);

	List<ShopCategory> findShopThreeCategory(Integer shopId, Integer productCategoryTwoId);

	List<ProductTransfer> findHotCategory(Map<String, Object> map);

	List<Advertisement> findAdvertisement(Integer shopId);

	List<Motion> findMotions(Integer shopId);
	
	List<Motion> findReport(Integer shopId);
	
	ProductTransfer findProductDetail(Integer productId,Integer shopId,Integer userId);
	
	List<ProductTransfer> pushProducts(Integer productCategoryTwoId,Integer productCategoryThirdId,Integer shopId,Integer userId);
  
}
