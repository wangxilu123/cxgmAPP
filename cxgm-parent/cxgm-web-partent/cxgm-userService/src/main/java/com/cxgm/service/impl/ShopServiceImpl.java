package com.cxgm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.common.LocationUtil;
import com.cxgm.dao.ShopMapper;
import com.cxgm.domain.Shop;
import com.cxgm.domain.ShopExample;
import com.cxgm.domain.ShopResponse;
import com.cxgm.service.ShopService;

@Primary
@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	
	private ShopMapper shopMapper;

	@Override
	public Integer addShop(Shop shop) {
		
		return null;
	}

	@Override
	public Integer updateShop(Shop shop) {

		return null;
	}

	@Override
	public Integer deleteShop(Integer shopId) {

		return null;
	}

	@Override
	public List<ShopResponse> findShopByPoint(String longitude,String dimension) {
		
		ShopExample example = new ShopExample();
		
		example.setOrderByClause("id desc");
		
		List<ShopResponse> list = new ArrayList<>();
		
		List<Shop> shopList = shopMapper.selectByExample(example);
		
		for(Shop shop :shopList){
			boolean location = LocationUtil.isInPolygon(longitude, dimension, shop.getElectronicFence());
			
			if(location==true){
				ShopResponse shopResponse = new ShopResponse();
				
				shopResponse.setDescription(shop.getDescription());
				shopResponse.setId(shop.getId());
				shopResponse.setImageUrl(shop.getImageUrl());
				shopResponse.setShopAddress(shop.getShopAddress());
				shopResponse.setShopName(shop.getShopName());
				
				list.add(shopResponse);
			}
		}
		return list;
	}

	@Override
	public Shop findShopById(Integer shopId) {
		return null;
	}

	
}
