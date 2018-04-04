package com.cxgm.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AppUser implements Serializable{

    @ApiModelProperty(name = "主键ID",value = "id")
    private Integer id;

    @ApiModelProperty(name = "userName",value = "用户名称")
    private String userName;
    
    @ApiModelProperty(name = "userPwd",value = "用户密码")
    private String userPwd;

    @ApiModelProperty(name = "mobile",value = "手机号码")
    private String mobile;

    @ApiModelProperty(name = "email",value = "邮箱地址")
    private String email;

    @ApiModelProperty(name = "realName",value = "真实姓名")
    private String realName;

    @ApiModelProperty(name = "peopleType",value = "用户类型")
    private String peopleType;
}
