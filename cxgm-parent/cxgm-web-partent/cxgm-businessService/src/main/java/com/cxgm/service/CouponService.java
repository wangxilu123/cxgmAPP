package com.cxgm.service;

import com.cxgm.domain.CouponDetail;
import com.github.pagehelper.PageInfo;

public interface CouponService {

	PageInfo<CouponDetail> findCouponByUserId(Integer userId,Integer pageNum,Integer pageSize);
}
