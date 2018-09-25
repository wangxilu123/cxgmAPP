package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.dao.PostageMapper;
import com.cxgm.domain.Advertisement;
import com.cxgm.domain.AdvertisementExample;
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
    
    public Postage findPostageById(Integer postageId) {
		
    	    PostageExample example= new PostageExample();
	    	
	    	example.createCriteria().andIdEqualTo(postageId);
	    	
	    	List<Postage> postageList = postageMapper.selectByExample(example);
	    	
			return postageList.get(0);
		}
    
    public Integer updatePostage(Postage postage) {

    	PostageExample example = new PostageExample();
	    
		example.createCriteria().andIdEqualTo(postage.getId());
		return postageMapper.updateByExample(postage, example);
	}
    
    @Transactional
	public int delete(String[] postageIds) {
		int resultDelete = 0;
		if (postageIds != null && postageIds.length > 0) {
			for(String postageId : postageIds) {
				
				PostageExample example = new PostageExample();
				
				example.createCriteria().andIdEqualTo(Integer.parseInt(postageId));
				postageMapper.deleteByExample(example);
			}
		}
		resultDelete = 1;
		return resultDelete;
	}
}
