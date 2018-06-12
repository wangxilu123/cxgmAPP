package com.cxgm.domain;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
    
    
}