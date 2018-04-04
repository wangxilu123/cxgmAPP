package com.cxgm.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;    

@Data
@ToString
public class RegisterEntity implements Serializable{
    private static final long serialVersionUID = 6031854054753338431L;
    @NotEmpty(message = "密码不能为空！")
    private String password;
    @NotEmpty(message = "手机号不能为空！")
    private String mobile;
    @NotEmpty(message = "手机验证码不能为空！")
    private String mobileValidCode;
}
