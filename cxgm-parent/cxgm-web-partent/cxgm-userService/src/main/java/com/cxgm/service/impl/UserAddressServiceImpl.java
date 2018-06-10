package com.cxgm.service.impl;

import java.util.List;

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
		
		example.createCriteria().andIdEqualTo(address.getId());
		
		if(address.getIsDef()!=null&&address.getIsDef()==1){
			//根据ID查询地址信息
			UserAddressExample example1 = new UserAddressExample();
			
			example1.createCriteria().andUserIdEqualTo(address.getUserId()).andIdNotEqualTo(address.getId());
			List<UserAddress> list = addressMapper.selectByExample(example1);
			
			for(UserAddress userAddress : list){
				UserAddressExample example2 = new UserAddressExample();
				
				example2.createCriteria().andIdEqualTo(userAddress.getId());
				
				userAddress.setIsDef(0);
				addressMapper.updateByExample(userAddress, example2);
			}
			
			return addressMapper.updateByExample(address, example);
		}else{
			//根据ID查询地址信息
			UserAddressExample example3 = new UserAddressExample();
			
			example3.createCriteria().andIdEqualTo(address.getId());
			List<UserAddress> list = addressMapper.selectByExample(example3);
			
			UserAddress dbAddress = list.get(0);
			
			dbAddress.setAddress(address.getAddress());
			dbAddress.setArea(address.getArea());
			dbAddress.setDimension(address.getDimension());
			dbAddress.setLongitude(address.getLongitude());
			dbAddress.setPhone(address.getPhone());
			dbAddress.setRealName(address.getRealName());
			dbAddress.setRemarks(address.getRemarks());
			return addressMapper.updateByExample(dbAddress, example);
		}
		
	}

	@Override
	public Integer deleteAddress(Integer addressId,Integer userId) {

		UserAddressExample example = new UserAddressExample();

		example.createCriteria().andIdEqualTo(addressId).andUserIdEqualTo(userId);

		return addressMapper.deleteByExample(example);
	}

	@Override
	public List<UserAddress> addressList(Integer userId) {
		
		UserAddressExample example = new UserAddressExample();

		example.createCriteria().andUserIdEqualTo(userId);
		
		example.setOrderByClause("is_def desc");
		
		List<UserAddress> list = addressMapper.selectByExample(example);
		return list;
	}

}
