package com.cxgm.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShopResponse {
	
	@ApiModelProperty(name = "id",value = "主键ID")
    private Integer id;

    @ApiModelProperty(name = "shopName",value = "门店名称")
    private String shopName;

	@ApiModelProperty(name = "shopAddress",value = "门店地址")
    private String shopAddress;

	@ApiModelProperty(name = "imageUrl",value = "门店图片")
    private String imageUrl;

	@ApiModelProperty(name = "description",value = "门店描述")
    private String description;
}