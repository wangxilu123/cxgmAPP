package com.cxgm.service;

import java.util.List;

import com.cxgm.domain.Shop;
import com.cxgm.domain.ShopResponse;
import com.github.pagehelper.PageInfo;


public interface ShopService {

	/**
     * 添加门店
     * @param Shop
     * @return
     */
	Integer addShop(Shop shop);
	
    /**
     * 修改门店信息
     * @param Shop
     * @return
     */
    Integer updateShop(Shop shop);
    
    /**
     * 删除门店
     * @param Shop
     * @return
     */
    Integer deleteShop(Integer shopId);
    
    /**
     * 根据门店ID查询门店信息
     * @param ShopId
     * @return
     */
    Shop findShopById(Integer shopId);

    List<ShopResponse> findShopByPoint(String longitude, String dimension);
    
	PageInfo<Shop> findShopByPage(Integer pageNum, Integer pageSize);
	
	List<Shop> findListAll();
}
