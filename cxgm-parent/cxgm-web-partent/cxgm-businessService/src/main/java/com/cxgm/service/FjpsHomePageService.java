package com.cxgm.service;

import com.cxgm.domain.FjpsHomePage;

public interface FjpsHomePageService {
	
	FjpsHomePage  findHomePageNum (Integer adminId,Integer shopId);
}
