package com.cxgm.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;    

@Data
@ToString
public class UserResponse implements Serializable{
    private static final long serialVersionUID = 6031854054753338431L;
    
    @ApiModelProperty(name = "userAccount",value = "用户账号")
    private String userAccount;
    
    @ApiModelProperty(name = "token",value = "用户token")
    private String token;
    
    @ApiModelProperty(name = "address",value = "用户收货地址")
    private String address;
    
    @ApiModelProperty(name = "phone",value = "用户手机号")
    private String phone;
    
    @ApiModelProperty(name = "headUrl",value = "用户头像")
    private String headUrl;
    
}
