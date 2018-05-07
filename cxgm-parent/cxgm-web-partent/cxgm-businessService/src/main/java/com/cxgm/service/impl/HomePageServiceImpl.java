package com.cxgm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.ProductMapper;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.ShopCategory;
import com.cxgm.service.HomePageService;

@Primary
@Service
public class HomePageServiceImpl implements HomePageService {

	@Autowired
	private ProductMapper productDao;
	
	@Override
	public List<ProductTransfer> findListAllWithCategory(Map<String,Object> map){
		return productDao.findListAllWithCategory(map);
	}
	
	@Override
	public List<ProductTransfer> findHotCategory(Map<String,Object> map){
		return productDao.findHotCategory(map);
	}

	@Override
	public List<ShopCategory> findShopOneCategory(Integer shopId) {
		
		return productDao.findShopCategory(shopId);
	}
	
	@Override
	public List<ShopCategory> findShopTwoCategory(Integer shopId,Integer productCategoryId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("shopId", shopId);
		map.put("productCategoryId", productCategoryId);
		return productDao.findShopCategoryTwo(map);
	}
	
	@Override
	public List<ShopCategory> findShopThreeCategory(Integer shopId,Integer productCategoryTwoId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("shopId", shopId);
		map.put("productCategoryTwoId", productCategoryTwoId);
		return productDao.findShopCategoryThird(map);
	}
}
