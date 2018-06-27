package com.cxgm.domain;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderProductTransfer {
	
	@ApiModelProperty(name = "productId",value = "商品ID")
    private Integer productId;
	
	@ApiModelProperty(name = "productName",value = "商品名称")
    private String productName;

	@ApiModelProperty(name = "productCode",value = "商品唯一标识")
    private String productCode;
	
	@ApiModelProperty(name = "barCode",value = "商品条码")
    private String barCode;

	@ApiModelProperty(name = "productNum",value = "商品数量")
    private Integer productNum;
    
	@ApiModelProperty(name = "productUrl",value = "商品图片")
    private String productUrl;
    
	@ApiModelProperty(name = "price",value = "商品单价")
    private BigDecimal price;
	
	@ApiModelProperty(name = "originalPrice",value = "商品原单价")
    private BigDecimal originalPrice;
    
	@ApiModelProperty(name = "amount",value = "商品总金额")
    private BigDecimal amount;
	
	@ApiModelProperty(name = "unit",value = "商品单位")
    private String unit;
	
	@ApiModelProperty(name = "weight",value = "商品重量")
    private String weight;
  

}