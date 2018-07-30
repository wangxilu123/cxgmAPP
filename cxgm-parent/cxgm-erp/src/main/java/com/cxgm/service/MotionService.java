package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.dao.MotionMapper;
import com.cxgm.dao.ShopMapper;
import com.cxgm.domain.Motion;
import com.cxgm.domain.MotionExample;
import com.cxgm.domain.Shop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MotionService {

	@Autowired
	private MotionMapper motionMapper;
	
	@Autowired
	private ShopMapper shopMapper;

	public Integer addMotion(Motion motion) {

		return motionMapper.insert(motion);
	}

	public Integer onshelf(Motion motion) {

		MotionExample example = new MotionExample();

		example.createCriteria().andIdEqualTo(motion.getId());

		return motionMapper.updateByExample(motion, example);
	}

	public PageInfo<Motion> findByPage(Integer shopId, Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		MotionExample example = new MotionExample();
        if(shopId!=null){
        	example.createCriteria().andShopIdEqualTo(shopId);
        }
		example.setOrderByClause("id desc");

		List<Motion> list = motionMapper.selectByExample(example);
		
		for(Motion motion : list){
			//根据门店ID查询门店信息
			Shop shop = shopMapper.selectByPrimaryKey(motion.getShopId());
			
			motion.setShopName(shop!=null?shop.getShopName():"");
		}
		PageInfo<Motion> page = new PageInfo<>(list);
		return page;
	}
	
	@Transactional
	public int delete(String[] motionIds) {
		int resultDelete = 0;
		if (motionIds != null && motionIds.length > 0) {
			for(String motionId : motionIds) {
				
				MotionExample example = new MotionExample();
				
				example.createCriteria().andIdEqualTo(Integer.parseInt(motionId));
				motionMapper.deleteByExample(example);
			}
		}
		resultDelete = 1;
		return resultDelete;
	}
	
    public Motion findMotionById(Integer motionId) {
		
    	MotionExample example = new MotionExample();
    	
    	example.createCriteria().andIdEqualTo(motionId);
    	
    	List<Motion> motionList = motionMapper.selectByExample(example);
    	
		return motionList.get(0);
	}
    
    public Integer updateMotion(Motion motion) {

		MotionExample example = new MotionExample();
		example.createCriteria().andIdEqualTo(motion.getId());
		return motionMapper.updateByExample(motion, example);
	}

}
