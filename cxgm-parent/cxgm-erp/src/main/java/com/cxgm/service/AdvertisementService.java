package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.AdvertisementMapper;
import com.cxgm.domain.Advertisement;
import com.cxgm.domain.AdvertisementExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AdvertisementService {

	@Autowired
	private AdvertisementMapper advertisementMapper;

	public Integer addAdvertisement(Advertisement advertisement) {

		return advertisementMapper.insert(advertisement);
	}

	public Integer onshelf(Advertisement advertisement) {

		AdvertisementExample example = new AdvertisementExample();

		example.createCriteria().andIdEqualTo(advertisement.getId());

		return advertisementMapper.updateByExample(advertisement, example);
	}

	public PageInfo<Advertisement> findByPage(Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);

		AdvertisementExample example = new AdvertisementExample();

		example.setOrderByClause("id desc");

		List<Advertisement> list = advertisementMapper.selectByExample(example);

		PageInfo<Advertisement> page = new PageInfo<>(list);
		return page;
	}

}
