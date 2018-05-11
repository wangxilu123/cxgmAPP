package com.cxgm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.common.LocationUtil;
import com.cxgm.dao.ShopMapper;
import com.cxgm.domain.PsfwTransfer;
import com.cxgm.domain.Shop;
import com.cxgm.domain.ShopExample;
import com.cxgm.domain.ShopResponse;
import com.cxgm.service.ShopService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class ShopServiceImpl implements ShopService {
	
	@Autowired
	
	private ShopMapper shopMapper;

	@Override
	public Integer addShop(Shop shop) {
		
		return shopMapper.insert(shop);
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
	public PageInfo<Shop> findShopByPage(Integer pageNum,Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		ShopExample example = new ShopExample();
		
		example.setOrderByClause("id desc");
		
		List<Shop> shopList = shopMapper.selectByExample(example);
		
		PageInfo<Shop> page = new PageInfo<>(shopList);
		return page;
	}
	
	@Override
	public PageInfo<ShopResponse> findShopListByPage(Integer pageNum,Integer pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		
		ShopExample example = new ShopExample();
		
		example.setOrderByClause("id desc");
		List<ShopResponse> list = new ArrayList<>();
		
		List<Shop> shopList = shopMapper.selectByExample(example);
		
		for(Shop shop :shopList){
			
				ShopResponse shopResponse = new ShopResponse();
				
				shopResponse.setDescription(shop.getDescription());
				shopResponse.setId(shop.getId());
				shopResponse.setImageUrl(shop.getImageUrl());
				shopResponse.setShopAddress(shop.getShopAddress());
				shopResponse.setShopName(shop.getShopName());
				
				list.add(shopResponse);
		}
		
		PageInfo<ShopResponse> page = new PageInfo<>(list);
		return page;
	}

	@Override
	public Shop findShopById(Integer shopId) {
		return null;
	}
	@Override
	public List<Shop> findListAll(){
		return shopMapper.findListAll();
	}
	@Override
	public List<PsfwTransfer> findPsfw(){
		
		List<Shop> list = shopMapper.findListAll();
		
		
		List<PsfwTransfer> psfwList= new ArrayList<>();
		for(Shop shop:list){
			
			PsfwTransfer psfw = new PsfwTransfer();
			
			psfw.setPsfw(shop.getElectronicFence());
			
			psfwList.add(psfw);
			
		}
		return  psfwList;

	}
}
