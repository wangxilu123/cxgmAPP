package com.cxgm.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AppUser implements Serializable{
	private static final long serialVersionUID = 6031854054753338431L;

    @ApiModelProperty(name = "id",value = "主键ID",hidden = true)
    private Integer id;

    @ApiModelProperty(name = "userName",value = "用户名称")
    private String userName;
    
    @ApiModelProperty(name = "userPwd",value = "用户密码")
    private String userPwd;

    @ApiModelProperty(name = "mobile",value = "手机号码")
    private String mobile;

    @ApiModelProperty(name = "headUrl",value = "头像")
    private String headUrl;
    
    @ApiModelProperty(name = "token",value = "用户token")
    private String token;

}
