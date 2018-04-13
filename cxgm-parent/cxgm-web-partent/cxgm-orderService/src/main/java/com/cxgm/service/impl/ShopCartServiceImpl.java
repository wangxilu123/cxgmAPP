package com.cxgm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.ShopCartMapper;
import com.cxgm.domain.ShopCart;
import com.cxgm.domain.ShopCartExample;
import com.cxgm.service.ShopCartService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
	public Integer deleteShopCart(Integer shopCartId, Integer userId) {
		
        ShopCartExample example= new ShopCartExample();
		
		example.createCriteria().andIdEqualTo(shopCartId).andUserIdEqualTo(userId);
		
		return mapper.deleteByExample(example);
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
