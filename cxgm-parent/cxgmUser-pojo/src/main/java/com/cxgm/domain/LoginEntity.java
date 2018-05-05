package com.cxgm.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;    

@Data
@ToString
public class LoginEntity implements Serializable{
    private static final long serialVersionUID = 6031854054753338431L;
    
    @ApiModelProperty(name = "userAccount",value = "用户账号")
    private String userAccount;
    
    @ApiModelProperty(name = "mobileValidCode",value = "验证码")
    private String mobileValidCode;

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getMobileValidCode() {
		return mobileValidCode;
	}

	public void setMobileValidCode(String mobileValidCode) {
		this.mobileValidCode = mobileValidCode;
	}
    
    
}
