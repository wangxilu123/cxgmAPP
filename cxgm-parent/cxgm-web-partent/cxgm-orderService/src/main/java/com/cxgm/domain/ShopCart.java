package com.cxgm.domain;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShopCart {
	
	@ApiModelProperty(name = "id",value = "主键ID",hidden = true)
    private Integer id;

	@ApiModelProperty(name = "userId",value = "用户ID")
    private Integer userId;

	@ApiModelProperty(name = "shopId",value = "门店ID")
    private Integer shopId;

	@ApiModelProperty(name = "goodName",value = "商品名称")
    private String goodName;

	@ApiModelProperty(name = "goodCode",value = "商品唯一标志")
    private String goodCode;
    
	@ApiModelProperty(name = "specifications",value = "规格")
    private String specifications;

	@ApiModelProperty(name = "goodNum",value = "商品订购数量")
    private Integer goodNum;

	@ApiModelProperty(name = "amount",value = "商品订购总价")
    private BigDecimal amount;

	@ApiModelProperty(name = "imageUrl",value = "商品图片")
    private String imageUrl;

	@ApiModelProperty(name = "originalPrice",value = "商品原价")
    private BigDecimal originalPrice;
	
	@ApiModelProperty(name = "price",value = "商品单价")
    private BigDecimal price;
    
	@ApiModelProperty(name = "coupon",value = "商品促销")
    private String coupon;
    
	@ApiModelProperty(name = "couponId",value = "商品促销ID")
    private Integer couponId;
}