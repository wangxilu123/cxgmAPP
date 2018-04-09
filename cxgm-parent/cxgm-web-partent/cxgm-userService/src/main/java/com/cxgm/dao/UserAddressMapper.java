package com.cxgm.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.cxgm.domain.UserAddress;
import com.cxgm.domain.UserAddressExample;

public interface UserAddressMapper {
    long countByExample(UserAddressExample example);

    int deleteByExample(UserAddressExample example);

    int insert(UserAddress record);

    int insertSelective(UserAddress record);

    List<UserAddress> selectByExample(UserAddressExample example);

    int updateByPrimaryKey(UserAddress record);
    
    int updateByExample(@Param("record") UserAddress record, @Param("example") UserAddressExample example);
}