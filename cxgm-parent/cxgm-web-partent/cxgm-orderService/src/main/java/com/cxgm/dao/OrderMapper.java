package com.cxgm.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);
}