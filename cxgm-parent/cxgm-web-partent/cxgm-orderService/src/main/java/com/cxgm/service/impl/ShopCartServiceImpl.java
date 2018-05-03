package com.cxgm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.ShopCartMapper;
import com.cxgm.domain.ShopCart;
import com.cxgm.domain.ShopCartExample;
import com.cxgm.service.ShopCartService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class ShopCartServiceImpl implements ShopCartService{

    @Autowired
    private ShopCartMapper mapper;

	@Override
	public Integer addShopCart(ShopCart shopCart) {
		mapper.insert(shopCart);
		return shopCart.getId();
	}

	@Override
	public Integer updateShopCart(ShopCart shopCart) {
		
		ShopCartExample example= new ShopCartExample();
		
		example.createCriteria().andIdEqualTo(shopCart.getId()).andUserIdEqualTo(shopCart.getUserId());
		
		return mapper.updateByExample(shopCart, example);
	}

	@Override
	public Integer deleteShopCart(String shopCartIds, Integer userId) {
		
		String[]  ids= shopCartIds.split(",");
		
		for (int i = 0; i < ids.length;i++) {
			
			 ShopCartExample example= new ShopCartExample();
				
			 example.createCriteria().andIdEqualTo(Integer.parseInt(ids[i])).andUserIdEqualTo(userId);
			 
			 mapper.deleteByExample(example);
		}
		return userId;
	}

	@Override
	public PageInfo<ShopCart> shopCartList(Integer pageNum, Integer pageSize, Integer userId) {
		
        PageHelper.startPage(pageNum, pageSize);
		
        ShopCartExample example = new ShopCartExample();
		
		example.createCriteria().andUserIdEqualTo(userId);
		
		List<ShopCart> result = mapper.selectByExample(example);
		
		PageInfo<ShopCart> page = new PageInfo<ShopCart>(result);
		
		return page;
	}



}
