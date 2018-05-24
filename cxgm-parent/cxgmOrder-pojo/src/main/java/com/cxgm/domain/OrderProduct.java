package com.cxgm.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderProduct {
    private Integer id;

    private Integer orderId;

    private String productName;

    private String goodCode;
    
    private Integer productId;

    private Integer productNum;

    private Date createTime;

}