package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.HaixinGoodMapper;
import com.cxgm.domain.HaixinGood;
import com.cxgm.domain.HaixinGoodExample;

@Service
public class HaixinService {

	@Autowired
	HaixinGoodMapper haixinGoodMapper;
	
	public List<HaixinGood> findAllHaixinGoods(String goodName){
		HaixinGoodExample haixinGoodExample = new HaixinGoodExample();
		
		haixinGoodExample.createCriteria().andGoodNameLike("%" + goodName + "%");
		return haixinGoodMapper.selectByExample(haixinGoodExample);
	}
}
