package com.cxgm.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Advertisement {
    private Integer id;

    private String position;

    private String type;

    private String imageUrl;

    private String notifyUrl;

    private String productCode;

    private Date createTime;

    private Integer onShelf;

    private Integer shopId;
    
    private String adverName;
}