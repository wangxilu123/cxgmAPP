package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.PostageMapper;
import com.cxgm.domain.Postage;
import com.cxgm.domain.PostageExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class PostageService {

	@Autowired
	PostageMapper postageMapper;
	
	public Integer addPostage(Postage postage) {
		
		return postageMapper.insertSelective(postage);
	}
	
    public PageInfo<Postage> findByPage(Integer pageNum, Integer pageSize) {
		
    	PageHelper.startPage(pageNum, pageSize);
    	
    	PostageExample example= new PostageExample();
    	
    	example.setOrderByClause("id desc");
    	List<Postage> list = postageMapper.selectByExample(example);
    	
    	PageInfo<Postage> page = new PageInfo<Postage>(list);
    	
		return page;
	}
    
    public PageInfo<Postage> findByList(Integer pageNum, Integer pageSize,String name) {
		
    	PageHelper.startPage(pageNum, pageSize);
    	
    	PostageExample example= new PostageExample();
    	if(!"".equals(name)){
    		example.createCriteria().andShopNameEqualTo(name);
    		example.setOrderByClause("id desc");
    	}else{
    		example.setOrderByClause("id desc");
    	}
    	List<Postage> list = postageMapper.selectByExample(example);
    	
    	PageInfo<Postage> page = new PageInfo<Postage>(list);
    	
		return page;
	}
}
