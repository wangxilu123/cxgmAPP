package com.cxgm.domain;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StaffDistribution {
	
	@ApiModelProperty(name = "id",value = "主键ID",hidden = true)
    private Integer id;

	@ApiModelProperty(name = "adminId",value = "员工ID")
    private Integer adminId;

	@ApiModelProperty(name = "orderId",value = "订单ID")
    private Integer orderId;

	@ApiModelProperty(name = "status",value = "订单状态")
    private String status;

	@ApiModelProperty(name = "addressId",value = "地址ID")
    private Integer addressId;

	@ApiModelProperty(name = "createTime",value = "创建时间")
    private Date createTime;
    
	@ApiModelProperty(name = "cancelReason",value = "取消原因")
    private String cancelReason;
	
	@ApiModelProperty(name = "shopId",value = "门店ID")
    private Integer shopId;
	
	@ApiModelProperty(name = "psPhone",value = "配送电话")
    private String psPhone;

    
}