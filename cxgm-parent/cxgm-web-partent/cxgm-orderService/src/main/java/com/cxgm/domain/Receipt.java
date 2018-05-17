package com.cxgm.domain;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Receipt {
    private Integer id;

    @ApiModelProperty(name = "type",value = "发票类型（0个人，1公司）")
    private String type;

    @ApiModelProperty(name = "orderId",value = "订单ID")
    private Integer orderId;

    @ApiModelProperty(name = "phone",value = "收票人信息")
    private String phone;

    @ApiModelProperty(name = "content",value = "发票内容")
    private String content;

    @ApiModelProperty(name = "companyName",value = "发票公司抬头")
    private String companyName;

    @ApiModelProperty(name = "createTime",value = "发票税号")
    private String dutyParagraph;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "userId",value = "用户ID")
    private Integer userId;

  
}