package com.cxgm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.StaffSortingMapper;
import com.cxgm.service.FjpsHomePageService;

@Primary
@Service
public class FjpsHomePageServiceImpl implements FjpsHomePageService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderProductMapper orderProductMapper;

	@Autowired
	private ProductImageMapper productImageMapper;

	@Autowired
	private StaffSortingMapper staffSortingMapper;

}
