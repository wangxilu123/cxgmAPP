package com.cxgm.domain;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DistributionOrder extends UserAddress{
	
	@ApiModelProperty(name = "orderId",value = "订单ID")
	private Integer orderId;
	
	@ApiModelProperty(name = "orderTime",value = "订单时间")
	private Date orderTime;
	
}