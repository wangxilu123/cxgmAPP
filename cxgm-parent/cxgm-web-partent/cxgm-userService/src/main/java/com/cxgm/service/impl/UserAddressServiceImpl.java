package com.cxgm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.UserAddressMapper;
import com.cxgm.domain.UserAddress;
import com.cxgm.domain.UserAddressExample;
import com.cxgm.service.UserAddressService;

@Primary
@Service
public class UserAddressServiceImpl implements UserAddressService {

	@Autowired

	private UserAddressMapper addressMapper;

	@Override
	public Integer addAddress(UserAddress address) {

		addressMapper.insert(address);
		return address.getId();
	}

	@Override
	public Integer updateAddress(UserAddress address) {
		
        UserAddressExample  example = new UserAddressExample();
		
		example.createCriteria().andIdEqualTo(address.getId()).andUserIdEqualTo(address.getUserId());
		
		return addressMapper.updateByExample(address, example);
	}

	@Override
	public Integer deleteAddress(Integer addressId,Integer userId) {

		UserAddressExample example = new UserAddressExample();

		example.createCriteria().andIdEqualTo(addressId).andUserIdEqualTo(userId);

		return addressMapper.deleteByExample(example);
	}

}
