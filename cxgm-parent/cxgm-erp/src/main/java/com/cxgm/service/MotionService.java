package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.MotionMapper;
import com.cxgm.domain.Motion;
import com.cxgm.domain.MotionExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class MotionService {

	@Autowired
	private MotionMapper motionMapper;

	public Integer addMotion(Motion motion) {

		return motionMapper.insert(motion);
	}

	public Integer onshelf(Motion motion) {

		MotionExample example = new MotionExample();

		example.createCriteria().andIdEqualTo(motion.getId());

		return motionMapper.updateByExample(motion, example);
	}

	public PageInfo<Motion> findByPage(Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		MotionExample example = new MotionExample();

		example.setOrderByClause("id desc");

		List<Motion> list = motionMapper.selectByExample(example);

		PageInfo<Motion> page = new PageInfo<>(list);
		return page;
	}

}
