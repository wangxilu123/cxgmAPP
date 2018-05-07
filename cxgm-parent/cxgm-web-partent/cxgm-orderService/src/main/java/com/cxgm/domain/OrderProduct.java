package com.cxgm.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderProduct {
    private Integer id;

    private Integer orderId;

    private String productName;

    private Integer productId;

    private Integer productNum;

    private Date createTime;

}