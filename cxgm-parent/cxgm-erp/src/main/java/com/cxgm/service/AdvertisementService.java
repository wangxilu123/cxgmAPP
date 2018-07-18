package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.dao.AdvertisementMapper;
import com.cxgm.dao.ShopMapper;
import com.cxgm.domain.Advertisement;
import com.cxgm.domain.AdvertisementExample;
import com.cxgm.domain.Shop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AdvertisementService {

	@Autowired
	private AdvertisementMapper advertisementMapper;
	
	@Autowired
	private ShopMapper shopMapper;

	public Integer addAdvertisement(Advertisement advertisement) {

		return advertisementMapper.insert(advertisement);
	}

	public Integer onshelf(Advertisement advertisement) {

		AdvertisementExample example = new AdvertisementExample();

		example.createCriteria().andIdEqualTo(advertisement.getId());

		return advertisementMapper.updateByExample(advertisement, example);
	}

	public PageInfo<Advertisement> findByPage(Integer shopId, Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		AdvertisementExample example = new AdvertisementExample();
        if(shopId!=null){
        	example.createCriteria().andShopIdEqualTo(shopId);
        }
		
		example.setOrderByClause("id desc");

		List<Advertisement> list = advertisementMapper.selectByExample(example);
		
		for(Advertisement advertisement : list){
			//根据门店ID查询门店信息
			Shop shop = shopMapper.selectByPrimaryKey(advertisement.getShopId());
			
			advertisement.setShopName(shop!=null?shop.getShopName():"");
		}

		PageInfo<Advertisement> page = new PageInfo<>(list);
		return page;
	}
	
	@Transactional
	public int delete(String[] advIds) {
		int resultDelete = 0;
		if (advIds != null && advIds.length > 0) {
			for(String advId : advIds) {
				
				AdvertisementExample example = new AdvertisementExample();
				
				example.createCriteria().andIdEqualTo(Integer.parseInt(advId));
				advertisementMapper.deleteByExample(example);
			}
		}
		resultDelete = 1;
		return resultDelete;
	}

}
