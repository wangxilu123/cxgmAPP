package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public int delete(String[] shopIds) {
		int resultDelete = 0;
		if (shopIds != null && shopIds.length > 0) {
			for(String shopId : shopIds) {
				
				ShopExample example = new ShopExample();
				
				example.createCriteria().andIdEqualTo(Integer.parseInt(shopId));
				shopMapper.deleteByExample(example);
			}
		}
		resultDelete = 1;
		return resultDelete;
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
		
		return shopMapper.selectByPrimaryKey(shopId);
	}
    
	public List<Shop> findListAll(){
		return shopMapper.findListAll();
	}
}
