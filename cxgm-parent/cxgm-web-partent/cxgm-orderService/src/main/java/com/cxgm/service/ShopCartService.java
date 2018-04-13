package com.cxgm.service;

import com.cxgm.domain.ShopCart;
import com.github.pagehelper.PageInfo;

public interface ShopCartService {
  
	Integer addShopCart(ShopCart shopCart);
	
	Integer updateShopCart(ShopCart shopCart);
	
    Integer deleteShopCart(Integer shopCartId,Integer userId);
    
    PageInfo<ShopCart> shopCartList(Integer pageNum, Integer pageSize,Integer userId);
}