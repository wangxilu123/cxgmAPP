package com.cxgm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxgm.domain.OrderProduct;
import com.cxgm.domain.OrderProductExample;
import com.cxgm.domain.OrderProductTransfer;

public interface OrderProductMapper {
    long countByExample(OrderProductExample example);

    int deleteByExample(OrderProductExample example);

    int insert(OrderProduct record);

    int insertSelective(OrderProduct record);

    List<OrderProduct> selectByExample(OrderProductExample example);
    
    List<OrderProductTransfer> selectOrderDetail(Integer orderId);
    
    List<OrderProductTransfer> selectYouZanOrderDetail(Integer orderId);

    int updateByExampleSelective(@Param("record") OrderProduct record, @Param("example") OrderProductExample example);

    int updateByExample(@Param("record") OrderProduct record, @Param("example") OrderProductExample example);
}