package com.cxgm.service;

import com.cxgm.domain.UserAddress;


public interface UserAddressService {

    /**
     * 添加用户地址
     * @param UserAddress
     * @return
     */
	Integer addAddress(UserAddress address);
	
    /**
     * 修改用户地址
     * @param UserAddress
     * @return
     */
    Integer updateAddress(UserAddress address);
    
    /**
     * 删除用户地址
     * @param UserAddress
     * @return
     */
    Integer deleteAddress(Integer addressId,Integer userId);
}
