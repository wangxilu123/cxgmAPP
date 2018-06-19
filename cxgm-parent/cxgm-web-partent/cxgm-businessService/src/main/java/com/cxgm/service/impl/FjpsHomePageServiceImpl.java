package com.cxgm.service.impl;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.StaffDistributionMapper;
import com.cxgm.dao.StaffSortingMapper;
import com.cxgm.domain.FjpsHomePage;
import com.cxgm.domain.StaffDistributionExample;
import com.cxgm.domain.StaffSortingExample;
import com.cxgm.service.FjpsHomePageService;

@Primary
@Service
public class FjpsHomePageServiceImpl implements FjpsHomePageService {

	@Autowired
	private StaffDistributionMapper distributionMapper;
	
	@Autowired
	private StaffSortingMapper staffSortingMapper;

	@Override
	public FjpsHomePage findHomePageNum(Integer adminId,Integer shopId) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("adminId", adminId);
		
		FjpsHomePage fjpsHomePage = distributionMapper.selectHomePageNum(map);
		
		//根据门店ID查询总分拣数量
		
		StaffDistributionExample example1 = new StaffDistributionExample();
		
		example1.createCriteria().andShopIdEqualTo(shopId);
		float totalPsNum = distributionMapper.countByExample(example1);
	
		//根据门店ID查询总配送数量
		StaffSortingExample example2 = new StaffSortingExample();
		
		example2.createCriteria().andShopIdEqualTo(shopId);
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		float totalFjNum = staffSortingMapper.countByExample(example2);
		
		float monthPsNum = fjpsHomePage.getMonthWpsNum();
		
		float monthFjNum = fjpsHomePage.getMonthWfjNum();
		
		fjpsHomePage.setPsPercentage(String.valueOf(df.format(monthPsNum/totalPsNum*100)));
		fjpsHomePage.setFjPercentage(String.valueOf(df.format(monthFjNum/totalFjNum*100)));
		
		return fjpsHomePage;
	}
	
	

}
