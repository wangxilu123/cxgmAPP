package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.ShopMapper;
import com.cxgm.domain.Shop;
import com.cxgm.domain.ShopExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ShopService {

    @Autowired
	private ShopMapper shopMapper;
    
	public Integer addShop(Shop shop) {
		
		return shopMapper.insert(shop);
	}

	public Integer updateShop(Shop shop) {

		return null;
	}

	public Integer deleteShop(Integer shopId) {

		return null;
	}
	
	public PageInfo<Shop> findShopByPage(Integer pageNum,Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		ShopExample example = new ShopExample();
		
		example.setOrderByClause("id desc");
		
		List<Shop> shopList = shopMapper.selectByExample(example);
		
		PageInfo<Shop> page = new PageInfo<>(shopList);
		return page;
	}

	public Shop findShopById(Integer shopId) {
		return null;
	}
    
	public List<Shop> findListAll(){
		return shopMapper.findListAll();
	}
}
