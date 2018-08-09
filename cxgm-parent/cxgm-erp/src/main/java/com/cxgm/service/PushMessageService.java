package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.PushMessageMapper;
import com.cxgm.dao.ShopMapper;
import com.cxgm.domain.Advertisement;
import com.cxgm.domain.AdvertisementExample;
import com.cxgm.domain.PushMessage;
import com.cxgm.domain.PushMessageExample;
import com.cxgm.domain.Shop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class PushMessageService {

	@Autowired
	private PushMessageMapper pushMessageMapper;
	
	@Autowired
	private ShopMapper shopMapper;

	public Integer addPushMessage(PushMessage pushMessage) {

		return pushMessageMapper.insert(pushMessage);
	}

	public Integer onshelf(PushMessage pushMessage) {

		PushMessageExample example = new PushMessageExample();

		example.createCriteria().andIdEqualTo(pushMessage.getId());

		return pushMessageMapper.updateByExample(pushMessage, example);
	}

	public PageInfo<PushMessage> findByPage(Integer shopId, Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		PushMessageExample example = new PushMessageExample();
        if(shopId!=null){
        	example.createCriteria().andShopIdEqualTo(shopId);
        }
		
		example.setOrderByClause("id desc");

		List<PushMessage> list = pushMessageMapper.selectByExample(example);
		
		for(PushMessage pushMessage : list){
			//根据门店ID查询门店信息
			Shop shop = shopMapper.selectByPrimaryKey(pushMessage.getShopId());
			
			pushMessage.setShopName(shop!=null?shop.getShopName():"");
		}

		PageInfo<PushMessage> page = new PageInfo<>(list);
		return page;
	}
	
	
	  public PushMessage findMessageById(Integer messageId) {
			
		    PushMessageExample example = new PushMessageExample();
	    	
	    	example.createCriteria().andIdEqualTo(messageId);
	    	
	    	List<PushMessage> advertisementList = pushMessageMapper.selectByExample(example);
	    	
			return advertisementList.get(0);
		}
	  
	  public Integer updateMessage(PushMessage pushMessage) {

		   PushMessageExample example = new PushMessageExample();
		    
			example.createCriteria().andIdEqualTo(pushMessage.getId());
			return pushMessageMapper.updateByExample(pushMessage, example);
		}
	  

}
