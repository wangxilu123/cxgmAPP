package com.cxgm.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShopCart {
    private Integer id;

    private Integer userId;

    private Integer shopId;

    private String goodName;

    private String goodCode;

    private Integer goodNum;

    private BigDecimal amount;

    private String imageUrl;

    private BigDecimal price;
}