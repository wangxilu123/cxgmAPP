package com.cxgm.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Motion {
    private Integer id;

    private String imageUrl;

    private String productIds;

    private Date createTime;

    private String position;

    private Integer shopId;

    private Integer onShelf;

    private String motionName;
    
    private List<ProductTransfer> productList;
}