package com.cxgm.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserAddress implements Serializable{
	
    private static final long serialVersionUID = 6031854054753338431L;
    
	@ApiModelProperty(name = "id",value = "主键ID",hidden = true)
    private Integer id;

	@ApiModelProperty(name = "userId",value = "用户ID",hidden = true)
    private Integer userId;

	@ApiModelProperty(name = "address",value = "详细地址")
    private String address;

	@ApiModelProperty(name = "phone",value = "联系电话")
    private String phone;

	@ApiModelProperty(name = "realName",value = "用户姓名")
    private String realName;

	@ApiModelProperty(name = "area",value = "地区信息")
    private String area;
    
	@ApiModelProperty(name = "longitude",value = "经度")
    private String longitude;
    
	@ApiModelProperty(name = "dimension",value = "维度")
    private String dimension;
	
	@ApiModelProperty(name = "isDef",value = "是否为默认值")
    private Integer isDef;
	
	@ApiModelProperty(name = "remarks",value = "备注")
    private String remarks;

}