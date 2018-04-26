package com.cxgm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxgm.domain.Shop;
import com.cxgm.domain.ShopExample;

public interface ShopMapper {
    long countByExample(ShopExample example);

    int deleteByExample(ShopExample example);

    int insert(Shop record);

    int insertSelective(Shop record);
    
    Shop selectByPrimaryKey(Integer id);

    List<Shop> selectByExample(ShopExample example);

    int updateByExampleSelective(@Param("record") Shop record, @Param("example") ShopExample example);

    int updateByExample(@Param("record") Shop record, @Param("example") ShopExample example);
    
    List<Shop> findListAll();
}